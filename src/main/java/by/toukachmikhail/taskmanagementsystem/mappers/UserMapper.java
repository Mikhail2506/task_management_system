package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDto entityToDto(User user) {
    return UserDto.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  public User dtoToEntity(UserDto userDto) {
    User user = new User();
    user.setUsername(userDto.username());
    user.setRole(userDto.role());
    user.setEmail(userDto.email());
    return user;
  }
}

