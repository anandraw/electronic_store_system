package com.anand.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	// VALIDITY IN milisec
	public static final long TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

	// secret key -> we put value in the application properties
	public static final String SECRET_KEY = "vvbdfuhgukdfhgukdfhgukdfhgkudvbdfuhgukvbdfuhgukdfhgukdfhgukdfhgkudfhngukdfngkgrhidfhgukdfhgukdfhgkudfhngukdfngkgrhifhngukdfngkgrhibdfuhgukdfhgukdfhgukdfhgkudfhngukdfngkgrhi";

	// Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve a specific claim from the token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims from the token
    private Claims getAllClaimsFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            return Jwts.parser()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("Token has expired", ex);
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("Invalid token", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error parsing token", ex);
        }
    }

    // Check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Generate token with claims and subject
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                   .setClaims(claims)
                   .setSubject(subject)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
	}
}
