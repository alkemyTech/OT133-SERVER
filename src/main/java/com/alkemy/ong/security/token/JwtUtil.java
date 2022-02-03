package com.alkemy.ong.security.token;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class JwtUtil {
  @Value("${security.token.validator-secret}")
  private String SECRET_KEY;
  private static final String TOKEN_PREFIX = "Bearer ";
  private static final String EMPTY = "";

  public String extractUsername(String authorizationHeader) {
    return extractClaim(getToken(authorizationHeader), Claims::getSubject);
  }

  private String getToken(String authorizationHeader) {
	    return authorizationHeader.replace(TOKEN_PREFIX, EMPTY);
	  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    
    return claimsResolver.apply(claims);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody();
  }



}