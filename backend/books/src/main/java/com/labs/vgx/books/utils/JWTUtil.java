package com.labs.vgx.books.utils;

import com.labs.vgx.books.enums.PermissaoEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Getter
@ToString
public class JWTUtil {
    @Value("${jwt.secret}")
    private String JWT_KEY ;

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {

            String username = claims.getSubject(); // Pega o usuário
            Date expirationDate = claims.getExpiration(); // Data de Expiração
            Date now = new Date(System.currentTimeMillis()); // Data Atual
            if (username != null && expirationDate != null && now.before(expirationDate)) { // Validando o token
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) { // Claims = Reivindicações do token

        try {
            return Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(token)
                    .getBody(); // Recupera os claims de agordo com o token

        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String token) { // Retorna o usuario
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public List<String> setRoles() {

        List<String> novasRoles = new ArrayList();
        novasRoles.clear();

        novasRoles.add(PermissaoEnum.BUSCAR_BOOK.getRole());
        novasRoles.add(PermissaoEnum.ALTERAR_BOOK.getRole());
        novasRoles.add(PermissaoEnum.CADASTRAR_BOOK.getRole());
        novasRoles.add(PermissaoEnum.APAGAR_BOOK.getRole());

        return novasRoles;
    }
}
