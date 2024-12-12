package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.Role;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDto entityToDto(User user) {
    return UserDto.builder()
        //.id(user.getId())
        .username(user.getUsername())
        .roles(user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet()))
        .build();
  }

  public User dtoToEntity(UserDto userDto) {
    User user = new User();
    //user.setId(userDto.id());
    user.setUsername(userDto.username());
    // Здесь можно добавить логику для маппинга ролей, если это необходимо
    return user;
  }
}

