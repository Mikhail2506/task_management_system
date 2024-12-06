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
  private final UserMapper userMapper;

  /**
   * @return
   */
  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return userMapper.toDtoList(Set.copyOf(users));
  }

  /**
   * @param userId
   * @return
   */
  @Override
  public Optional<UserDto> getUserById(Long userId) {
    return userRepository.findById(userId).map(userMapper::entityToDto);
  }

  /**
   * @param userDto
   * @return
   */
  @Override
  public UserDto saveUser(UserDto userDto) {
    User user = userMapper.dtoToEntity(userDto);
    user = userRepository.save(user);
    return userMapper.entityToDto(user);
  }

  /**
   * @param taskDto
   */
  @Override
  public Optional<UserDto> updateUser(Long userId, UserDto userDto) {
    return userRepository.findById(userId).map(existingUser -> {
      User updatedUser = userMapper.dtoToEntity(userDto);
      updatedUser.setUserId(userId);
      updatedUser = userRepository.save(updatedUser);
      return userMapper.entityToDto(updatedUser);
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
