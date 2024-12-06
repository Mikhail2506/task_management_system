package by.toukachmikhail.taskmanagementsystem.services.impl;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import java.util.List;
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
    return List.of();
  }

  /**
   * @param taskId
   * @return
   */
  @Override
  public UserDto getUser(Long taskId) {
    return null;
  }

  /**
   * @param userDto
   */
  @Override
  public void saveUser(UserDto userDto) {

  }

  /**
   * @param taskDto
   */
  @Override
  public void correctUsersInfo(TaskDto taskDto) {

  }

  /**
   *
   */
  @Override
  public void deleteAllUsers() {

  }

  /**
   * @param userId
   */
  @Override
  public void deleteUser(Long userId) {

  }
}
