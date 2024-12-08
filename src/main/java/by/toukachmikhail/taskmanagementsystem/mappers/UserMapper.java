package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.entities.UsersRoles;
import by.toukachmikhail.taskmanagementsystem.enums.Role;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapper {


  public static UserDto entityToDto(User user) {
    return UserDto.builder()
        .username(user.getUsername())
        .role(user.getRole().getUsersRole().name())
        .usersPhoneNumber(user.getUsersPhoneNumber())
        .tasksDto(user.getTasks().stream().map(TaskMapper::entityToDto).collect(Collectors.toSet()))
        .build();
  }

  public static User dtoToEntity(UserDto userDto) {
    return User.builder()
        .username(userDto.username())
        .role(UsersRoles.builder().usersRole(Role.valueOf(userDto.role())).build())
        .usersPhoneNumber(userDto.usersPhoneNumber())
        .tasks(userDto.tasksDto().stream().map(TaskMapper::dtoToEntity).collect(Collectors.toSet()))
        .build();
  }
}

