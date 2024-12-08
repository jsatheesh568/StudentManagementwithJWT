package com.studentmangement.StudentManagement.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class JwtUtil {
  @Value ("${jwt.secret-key}") // Key from application.properties
  private String secretKey;

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean isTokenExpired(String token) {
    return extractExpirationDate(token).before(new Date());
  }

  public Date extractExpirationDate(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
  }
}
