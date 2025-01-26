package by.toukachmikhail.taskmanagementsystem.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtils {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.lifetime}")
  private Duration jwtLifeTime;

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    List<String> rolesList = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
    claims.put("roles", rolesList);
    claims.put("email", userDetails.getUsername());

    Date issuedDate = new Date();
    Date expiredDate = new Date(issuedDate.getTime() + jwtLifeTime.toMillis());
    return Jwts.builder()
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(issuedDate)
        .expiration(expiredDate)
        .signWith(getSigningKey())
        .compact();
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  public String getEmail(String token) {
    return getAllClaimsFromToken(token).getSubject(); // Subject теперь содержит email
  }

  public List<String> getRoles(String token) {
    Claims claims = getAllClaimsFromToken(token);
    List<?> roles = claims.get("roles", List.class);
    if (roles != null) {
      return roles.stream()
          .filter(String.class::isInstance)
          .map(String.class::cast)
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimResolver.apply(claims);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String email = getEmail(token);
    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}
