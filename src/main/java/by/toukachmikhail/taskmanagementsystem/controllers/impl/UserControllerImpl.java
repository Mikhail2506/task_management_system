package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.UserController;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  @GetMapping("")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    List<UserDto> usersDtoList = userService.getAllUsers();
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(usersDtoList);
  }

  @Override
  @GetMapping("/{user_id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("user_id") Long userId) {

    UserDto userDto = userService.getUserById(userId);
    return ResponseEntity.ok(userDto);
  }

  @Override
  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto));
  }

  @Override
  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
      @RequestBody UserDto userDto) {
    Optional<UserDto> updatedUserDto = userService.updateUser(userId, userDto);
    return updatedUserDto.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Override
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
      userService.deleteUser(userId);
      return ResponseEntity.noContent().build();
  }
}
