package by.toukachmikhail.taskmanagementsystem.controllers.impl;


import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.UnauthorizedExceptionMessage.TOKEN_NOT_VALID;

import by.toukachmikhail.taskmanagementsystem.controllers.AuthController;
import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.dto.JwtRequestDto;
import by.toukachmikhail.taskmanagementsystem.dto.JwtResponseDto;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import by.toukachmikhail.taskmanagementsystem.utils.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

  private final UserDetailsService userDetailsService;
  private final JwtTokenUtils jwtTokenUtils;
  private final AuthenticationManager authenticationManager;

  @Override
  @PostMapping("/auth")
  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
    } catch (BadCredentialsException e) {
      throw new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
    String token = jwtTokenUtils.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponseDto(token));
  }
}
