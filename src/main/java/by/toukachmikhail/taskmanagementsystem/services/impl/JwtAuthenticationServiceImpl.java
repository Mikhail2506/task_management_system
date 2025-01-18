package by.toukachmikhail.taskmanagementsystem.services.impl;

import by.toukachmikhail.taskmanagementsystem.services.JwtAuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationServiceImpl implements JwtAuthenticationService{

  @Override
  public String generateToken(){

    return "token";
  }
}
