package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final TaskRepository taskRepository;


  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
  }

  @Override
  public UserDto createNewUser(RegistrationUserDto registrationUserDto) {
    User user = User.builder()
        .username(registrationUserDto.username())
        .password(passwordEncoder.encode(registrationUserDto.password()))
        .role(UserRole.USER)
        .build();
    User savedUser = userRepository.save(user);
    return userMapper.entityToDto(savedUser);
  }

  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(userMapper::entityToDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getUserById(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    return userMapper.entityToDto(user);
  }

  @Override
  public UserDto updateUser(Long userId, UserDto userDto) {
    User updatingUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    User updatedUser = userMapper.dtoToEntity(userDto);
    updatedUser.setId(userId);
    updatedUser = userRepository.save(updatedUser);
    return userMapper.entityToDto(updatedUser);
  }

  @Override
  public void deleteUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    userRepository.delete(user);
  }

}
