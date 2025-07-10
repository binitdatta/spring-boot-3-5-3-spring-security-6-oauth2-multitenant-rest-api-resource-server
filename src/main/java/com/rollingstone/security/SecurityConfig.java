package com.rollingstone.security;

import com.rollingstone.config.IssuerProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private MultiTenantDynamicJwtDecoderFactory dynamicDecoderFactory;

    @Autowired
    private JwtAuthenticationConverter authenticationConverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .authenticationManagerResolver(multiTenantAuthenticationManagerResolver())
                );
        return http.build();
    }

    public AuthenticationManagerResolver<HttpServletRequest> multiTenantAuthenticationManagerResolver() {
        return request -> {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);
            String issuer = JwtHelper.extractIssuer(token);

            JwtDecoder decoder = dynamicDecoderFactory.getDecoder(issuer);
            JwtAuthenticationProvider provider = new JwtAuthenticationProvider(decoder);
            provider.setJwtAuthenticationConverter(authenticationConverter);
            return new ProviderManager(provider);
        };
    }
}
