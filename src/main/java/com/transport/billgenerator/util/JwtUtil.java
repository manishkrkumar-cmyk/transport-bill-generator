package com.transport.billgenerator.util;

import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "TransportBillSecretKeyForDevMode";

    public String generateToken(String username) {
        String payload = username + ":" + (System.currentTimeMillis() + 86400000L);
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

    public boolean validateToken(String token, String username) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            String tokenUsername = parts[0];
            long expiry = Long.parseLong(parts[1]);

            return tokenUsername.equals(username) && System.currentTimeMillis() < expiry;
        } catch (Exception e) {
            return false;
        }
    }
}