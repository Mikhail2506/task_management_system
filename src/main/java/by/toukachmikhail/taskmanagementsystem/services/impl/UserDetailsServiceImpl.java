package by.toukachmikhail.taskmanagementsystem.services.impl;


import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        Collections.singletonList(authority));
  }
}
