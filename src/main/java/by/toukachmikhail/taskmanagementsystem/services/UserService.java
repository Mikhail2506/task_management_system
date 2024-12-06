package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import java.util.List;
import java.util.Optional;

public interface UserService {

  List<UserDto> getAllUsers();

  Optional<UserDto> getUserById(Long userId);

  UserDto saveUser(UserDto userDto);

  Optional<UserDto> updateUser(Long userId, UserDto userDto);

  void deleteUser(Long userId);
}
