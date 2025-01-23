package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService  {

  Optional<User> findByUsername(String username);

  UserDto createNewUser(RegistrationUserDto registrationUserdto);

  List<UserDto> getAllUsers();

  UserDto getUserById(Long userId);

  Optional<UserDto> updateUser(Long userId, UserDto userDto);

  void deleteUser(Long userId);

}
