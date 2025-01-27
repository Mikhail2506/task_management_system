package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import org.springframework.data.domain.Page;

public interface UserService {

  User findByEmail(String username);

  UserDto createNewUser(RegistrationUserDto registrationUserdto);

  Page<UserDto> getAllUsers(int page, int size, String sortBy, String direction);

  UserDto getUserById(Long userId);

  UserDto updateUser(Long userId, UserDto userDto);

  void deleteUser(Long userId);

}
