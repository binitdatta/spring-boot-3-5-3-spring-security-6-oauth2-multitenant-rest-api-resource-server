package com.rollingstone.security;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

public class JwtHelper {

    public static String extractIssuer(String token) {
        try {
            SignedJWT jwt = (SignedJWT) JWTParser.parse(token);
            return jwt.getJWTClaimsSet().getIssuer();
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse JWT", e);
        }
    }
}


