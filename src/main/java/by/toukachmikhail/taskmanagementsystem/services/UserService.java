package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import java.util.List;

public interface UserService  {

  User findByUsername(String username);

  UserDto createNewUser(RegistrationUserDto registrationUserdto);

  List<UserDto> getAllUsers();

  UserDto getUserById(Long userId);

  UserDto updateUser(Long userId, UserDto userDto);

  void deleteUser(Long userId);

}
