package com.rollingstone.security;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
//public class DynamicIssuerRegistry {
//
//    private final Map<String, String> issuerMap = new ConcurrentHashMap<>();
//
//    public void registerIssuer(String tenantId, String issuerUrl) {
//        issuerMap.put(tenantId, issuerUrl);
//    }
//
//    public String resolveIssuer(String issuerClaim) {
//        return issuerMap.values().stream()
//                .filter(issuerClaim::equals)
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Unknown issuer: " + issuerClaim));
//    }
//
//    public Map<String, String> getAllIssuers() {
//        return Map.copyOf(issuerMap);
//    }
//
//    public boolean containsIssuer(String issuerClaim) {
//        return issuerMap.containsValue(issuerClaim);
//    }
//}

@Component
public class DynamicIssuerRegistry {

    private final Map<String, String> tenantToIssuer = new ConcurrentHashMap<>();

    // 🟢 Load static issuers on startup
    public void initializeWithStaticIssuers(Map<String, String> staticIssuers) {
        tenantToIssuer.putAll(staticIssuers);
    }

    public void register(String tenantId, String issuerUrl) {
        tenantToIssuer.put(tenantId, issuerUrl);
    }

    public String resolveIssuer(String issuerClaim) {
        return tenantToIssuer.values().stream()
                .filter(issuerClaim::equals)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Issuer not registered: " + issuerClaim));
    }

    public Map<String, String> getAllIssuers() {
        return Map.copyOf(tenantToIssuer);
    }
}