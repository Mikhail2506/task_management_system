package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService  {

  Optional<User> findByUsername(String username);

  void createNewUser(User user);

  List<UserDto> getAllUsers();

  UserDto getUserById(Long userId);

  UserDto saveUser(UserDto userDto);

  Optional<UserDto> updateUser(Long userId, UserDto userDto);

  void deleteUser(Long userId);

}
