package by.toukachmikhail.taskmanagementsystem.configuration;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.UnauthorizedExceptionMessage.TOKEN_HAS_EXPIRED;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.UnauthorizedExceptionMessage.TOKEN_NOT_VALID;

import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.UnauthorizedException;
import by.toukachmikhail.taskmanagementsystem.utils.jwt.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j

public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtils jwtTokenUtils;

  /**
   * @param request
   * @param response
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
      try {
        username = jwtTokenUtils.getUsername(jwt);
      } catch (ExpiredJwtException e) {
        throw new UnauthorizedException(TOKEN_HAS_EXPIRED.getMessage());
      } catch (SignatureException e) {
        throw new UnauthorizedException(TOKEN_NOT_VALID.getMessage());
      }
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
          username, null, jwtTokenUtils.getRoles(jwt).stream()
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList())
      );
      SecurityContextHolder.getContext().setAuthentication(token);
    }
    filterChain.doFilter(request, response);
  }
}
