package com.labs.vgx.books.models.filter;

import com.labs.vgx.books.services.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class AutenticacaoFiltro extends BasicAuthenticationFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    public AutenticacaoFiltro(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!request.getContextPath().concat("/auth/login/refresh").equals(request.getRequestURI())
                && !"refresh_token".equals(request.getParameter("grant_type"))) {

            Authentication authentication = AutenticacaoService.obterAutenticacao(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
    }
}
