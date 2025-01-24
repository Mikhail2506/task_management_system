package by.toukachmikhail.taskmanagementsystem.services.impl;


import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.UnauthorizedExceptionMessage.USER_NOT_AUTHENTICATED;

import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.UnauthorizedException;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        authorities
    );
  }

  public User getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      String username = ((UserDetails) principal).getUsername();
      log.info("Current user: {}", username);
      return userRepository.findByUsername(username)
          .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    }
    throw new UnauthorizedException(USER_NOT_AUTHENTICATED.getMessage());
  }
}
