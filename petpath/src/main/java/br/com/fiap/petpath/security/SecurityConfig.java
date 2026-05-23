package br.com.fiap.petpath.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JWTAuthFilter authFilter;

    @Bean
    public SecurityFilterChain filtrar(HttpSecurity request) throws Exception {
        request
                .csrf(csrf -> csrf.disable())
                .headers(header ->
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                "/autenticacao/**",
                                "/tutores/cadastrar",
                                "/usuarios/novo",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/**",
                                "/h2-console/**",
                                "/error"
                        ).permitAll()
                         .anyRequest().authenticated())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return request.build();
    }
}
