package by.toukachmikhail.taskmanagementsystem.services.impl;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  /**
   * @return
   */
  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(UserMapper::entityToDto).toList();
  }

  /**
   * @param userId
   * @return
   */
  @Override
  public Optional<UserDto> getUserById(Long userId) {
    return userRepository.findById(userId).map(UserMapper::entityToDto);
  }

  /**
   * @param userDto
   * @return
   */
  @Override
  public UserDto saveUser(UserDto userDto) {
    User user = UserMapper.dtoToEntity(userDto);
    user = userRepository.save(user);
    return UserMapper.entityToDto(user);
  }

  /**
   * @param userDto
   */
  @Override
  public Optional<UserDto> updateUser(Long userId, UserDto userDto) {
    return userRepository.findById(userId).map(existingUser -> {
      User updatedUser = UserMapper.dtoToEntity(userDto);
      updatedUser.setUserId(userId);
      updatedUser = userRepository.save(updatedUser);
      return UserMapper.entityToDto(updatedUser);
    });
  }

   /**
   * @param userId
   */
  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }
}
