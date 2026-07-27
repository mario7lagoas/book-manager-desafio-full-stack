package com.labs.vgx.books.config;

import com.labs.vgx.books.enums.PermissaoEnum;
import com.labs.vgx.books.models.filter.AutenticacaoFiltro;
import com.labs.vgx.books.models.filter.LoginFiltro;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfiguration( final AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/auth/login",
            "/auth/register",
            "/auth/login/refresh",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(PUBLIC_MATCHERS).permitAll()
                            .requestMatchers(HttpMethod.GET, "/books")
                            .hasAnyAuthority(PermissaoEnum.BUSCAR_BOOK.getRole())
                            .requestMatchers(HttpMethod.PUT, "/books")
                            .hasAnyAuthority(PermissaoEnum.ALTERAR_BOOK.getRole())
                            .requestMatchers(HttpMethod.POST, "/books/create")
                            .hasAnyAuthority(PermissaoEnum.CADASTRAR_BOOK.getRole())
                            .requestMatchers(HttpMethod.DELETE, "/books")
                            .hasAnyAuthority(PermissaoEnum.APAGAR_BOOK.getRole())
                            .anyRequest().authenticated();

                });

        http.addFilterBefore(new LoginFiltro("/auth/login", authenticationConfiguration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new AutenticacaoFiltro(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
