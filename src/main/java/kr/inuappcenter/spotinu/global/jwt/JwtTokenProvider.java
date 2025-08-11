package kr.inuappcenter.spotinu.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.inuappcenter.spotinu.domain.member.entity.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long expirationTime;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(CustomUserDetails customUserDetails) {
    return Jwts.builder()
      .setSubject(customUserDetails.getUsername())
      .claim("role", customUserDetails.getRole().name())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
      .signWith(getSigningKey(), SignatureAlgorithm.HS512)
      .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  // 토큰 유효성 검증
  public boolean validateToken(String token) {
    try {
      Key key = getSigningKey();

      Jwts.parserBuilder()
        .setSigningKey(key)  // 서명 키 설정
        .build()
        .parseClaimsJws(token);  // 토큰 파싱
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String extractStudentNumber(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getClaims(token);
    return claimsResolver.apply(claims);
  }
}