//
//package com.manh.security;
//
//import org.springframework.security.oauth2.jwt.*;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class MultiTenantJwtDecoderFactory {
//
//    private final Map<String, JwtDecoder> decoderCache = new ConcurrentHashMap<>();
//
//    public JwtDecoder getDecoder(String issuer) {
//        return decoderCache.computeIfAbsent(issuer, iss -> {
//            NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(iss);
//            jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(iss));
//            return jwtDecoder;
//        });
//    }
//}
//

package com.rollingstone.security;

import com.rollingstone.config.IssuerProperties;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MultiTenantJwtDecoderFactory {

    private final Map<String, JwtDecoder> decoderCache = new ConcurrentHashMap<>();
    private final Map<String, String> issuerMap;

    public MultiTenantJwtDecoderFactory(IssuerProperties issuerProperties) {
        this.issuerMap = issuerProperties.getIssuers();
    }

    public JwtDecoder getDecoder(String issuerClaim) {
        String issuer = issuerMap.values().stream()
                .filter(issuerClaim::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown issuer: " + issuerClaim));

        return decoderCache.computeIfAbsent(issuer, iss -> {
            NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(iss);
            decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(iss));
            return decoder;
        });
    }
}
