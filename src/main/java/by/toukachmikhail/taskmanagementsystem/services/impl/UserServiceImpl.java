package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ForbiddenExceptionMessage.USER_ALREADY_EXISTS;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.ForbiddenException;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
  }

  @Override
  public UserDto createNewUser(RegistrationUserDto registrationUserDto) {
    if (isUserExistByEmail(registrationUserDto.email())) {
      throw new ForbiddenException(USER_ALREADY_EXISTS.getMessage());
    }

    User user = User.builder()
        .username(registrationUserDto.username())
        .email(registrationUserDto.email())
        .password(passwordEncoder.encode(registrationUserDto.password()))
        .role(UserRole.USER)
        .build();
    User savedUser = userRepository.save(user);
    return userMapper.entityToDto(savedUser);
  }

  @Override
  public Page<UserDto> getAllUsers(int page, int size, String sortBy, String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    return userRepository.findAll(pageable)
        .map(userMapper::entityToDto);
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

  private boolean isUserExistByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

}
