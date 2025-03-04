package com.thishotel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "123456789qwertyuioplkjhgfdsazxcvb";

    private static final long EXPIRATION_TIME = 86400000;


    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }
}



/**
 *
 private static final String SECRET_KEY = "12";

 private static final long EXPIRATION_TIME = 86400000;


 public String generateToken(String email){
 return Jwts.builder()
 .setSubject(email)
 .setIssuedAt(new Date())
 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
 .signWith(SignatureAlgorithm.ES256, SECRET_KEY)
 .compact();
 }
 */
