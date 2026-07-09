package com.medigen.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Generate a Base64-encoded 256-bit key and paste it here.
    // We'll move this to application.properties later.
    private static final String SECRET_KEY =
            "H1Xz8gT8Lw9vK4M7zQmN2jVb5YxC1pRa8DqW0sEfGhIjKlMnOpQrStUvWxYz12345=";
    // 24 hours
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24;

    /**
     * Generates a JWT token for the given email.
     */
    public String generateToken(String email) {
        return generateToken(new HashMap<>(), email);
    }

    /**
     * Generates a JWT token with custom claims.
     */
    public String generateToken(Map<String, Object> extraClaims, String email) {

        return Jwts.builder()
                .claims(extraClaims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract email (subject) from token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic method to extract any claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Check if token is valid.
     */
    public boolean isTokenValid(String token, String email) {

        String username = extractUsername(token);

        return username.equals(email) && !isTokenExpired(token);
    }

    /**
     * Check token expiry.
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    /**
     * Parse all claims.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Convert Base64 secret into SecretKey.
     */
    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}