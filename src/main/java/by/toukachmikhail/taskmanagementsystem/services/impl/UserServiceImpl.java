package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.AUTHOR_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.RoleService;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final UserMapper userMapper;


  @Override
  public Optional<User> findByUsername(String username){
    return userRepository.findByUsername(username);
  }

//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    User user = findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
//    return new org.springframework.security.core.userdetails.User(
//        user.getUsername(),
//        user.getPassword(),
//        user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).toList()
//    );
//  }


  @Override
  public void createNewUser(User user){
   /*
   #toDo роли извлекатть через RoleService
    */
    user.setRoles(roleService.findRoleByName("USER"));
    userRepository.save(user);
  }

  /**
   * @return
   */
  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(userMapper::entityToDto)
        .collect(Collectors.toList());
  }

  /**
   * @param userId
   * @return
   */
  @Override
  public UserDto getUserById(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND.getMessage()));

    try {
      return userMapper.entityToDto(user);
    } catch (RuntimeException e) {
      throw new NotFoundException(AUTHOR_NOT_FOUND.getMessage());
    }
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
   * @param userDto
   */
  @Override
  public Optional<UserDto> updateUser(Long userId, UserDto userDto) {
    return userRepository.findById(userId).map(existingUser -> {
      User updatedUser = userMapper.dtoToEntity(userDto);
      updatedUser.setId(userId);
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

  /**
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
}
