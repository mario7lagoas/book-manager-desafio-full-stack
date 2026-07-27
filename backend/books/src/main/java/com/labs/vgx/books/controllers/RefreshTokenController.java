package com.labs.vgx.books.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labs.vgx.books.models.entityes.UsuarioEntity;
import com.labs.vgx.books.repositories.IUsuarioRepository;
import com.labs.vgx.books.utils.JWTUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth/login")
public class RefreshTokenController {

    private final JWTUtil jwtUtil;

    private final IUsuarioRepository iUsuarioRepository;

    public RefreshTokenController(final JWTUtil jwtUtil, final IUsuarioRepository iUsuarioRepository) {
        this.jwtUtil = jwtUtil;
        this.iUsuarioRepository = iUsuarioRepository;
    }

    private static final String REFRESH_TOKEN = "RefreshToken";
    private static final String AUTHORITIES = "authorities";
    private static final String HEAD_AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    private static String JWT_KEY;
    private static int EXPIRATION_TOKEN;
    private static int EXPIRATION_REFRESH_TOKEN;

    @Value("${jwt.secret}")
    private void setKey(String key) {
        JWT_KEY = key;
    }

    @Value("${jwt.expiration}")
    private void setExpiration(int expiration) {
        EXPIRATION_TOKEN = expiration;
    }

    @Value("${jwt.refreshToken}")
    private void setRefresh(int refresh) {
        EXPIRATION_REFRESH_TOKEN = refresh;
    }

    @PostMapping("/refresh")
    public void refreshtoken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = null;

        if ("/book-api/v1/auth/login/refresh".equalsIgnoreCase(request.getRequestURI())
                && "refresh_token".equals(request.getParameter("grant_type"))
                && request.getCookies() != null) {

            boolean checkCookieAdd = false;

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("RefreshToken") && checkCookieAdd == false) {
                    refreshToken = cookie.getValue();
                    checkCookieAdd = true;
                }
            }

            if (refreshToken != null) {
                try {

                    if (jwtUtil.tokenValido(refreshToken)) {

                        String email = jwtUtil.getUsername(refreshToken);

                        UsuarioEntity user = iUsuarioRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " não Encontrado!"));

                        Map<String, Object> claims = new HashMap<>();

                        claims.put(AUTHORITIES, jwtUtil.setRoles());
                        claims.put("nome", user.getUserName());

                        String jwtToken = Jwts.builder()
                                .setSubject(user.getEmail())
                                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                                .setIssuer(request.getRequestURL().toString())
                                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                                .addClaims(claims)
                                .compact();

                        String jwtReshToken = Jwts.builder()
                                .setSubject(user.getEmail())
                                .setExpiration(new Date(System.currentTimeMillis() + 10L * EXPIRATION_REFRESH_TOKEN))
                                .setIssuer(request.getRequestURL().toString())
                                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                                .compact();

                        Cookie cookie = new Cookie(REFRESH_TOKEN, jwtReshToken);
                        cookie.setMaxAge(EXPIRATION_REFRESH_TOKEN);
                        cookie.setHttpOnly(true);
                        cookie.setSecure(false);
                        cookie.setPath(request.getContextPath() + "/auth/login/refresh");

                        response.addCookie(cookie);

                        response.addHeader(HEAD_AUTHORIZATION, BEARER + jwtToken);
                        response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, HEAD_AUTHORIZATION);

                    } else {
                        throw new RuntimeException("Token inválido");
                    }

                } catch (Exception ex) {
                    try {
                        response.setHeader("error", ex.getMessage());
                        response.setStatus(FORBIDDEN.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("codigo", "JWT_ERRO");
                        error.put("mensagem", ex.getMessage());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

    }

}
