package com.rollingstone.security;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MultiTenantDynamicJwtDecoderFactory {

    private final Map<String, JwtDecoder> decoderCache = new ConcurrentHashMap<>();
    private final DynamicIssuerRegistry issuerRegistry;


    public MultiTenantDynamicJwtDecoderFactory(DynamicIssuerRegistry issuerRegistry) {
        this.issuerRegistry = issuerRegistry;
    }

    public JwtDecoder getDecoder(String issuerClaim) {
        String issuer = issuerRegistry.resolveIssuer(issuerClaim);
        return decoderCache.computeIfAbsent(issuer, iss -> {
            NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(iss);
            decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(iss));
            return decoder;
        });
    }
}
