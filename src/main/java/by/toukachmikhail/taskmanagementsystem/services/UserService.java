package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import java.util.List;

public interface UserService {

  List<UserDto> getAllUsers();

  UserDto getUser(Long taskId);

  void saveUser(UserDto userDto);

  void correctUsersInfo(TaskDto taskDto);

  void deleteAllUsers();

  void deleteUser(Long userId);
}
