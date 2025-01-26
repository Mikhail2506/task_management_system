package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.UserController;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  @GetMapping("")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Page<UserDto>> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<UserDto> usersDtoPage = userService.getAllUsers(page, size, sortBy, direction);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(usersDtoPage);
  }

  @Override
  @GetMapping("/{user_id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<UserDto> getUserById(@PathVariable("user_id") Long userId) {
    UserDto userDto = userService.getUserById(userId);
    return ResponseEntity.ok(userDto);
  }

  @Override
  @PutMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
      @RequestBody UserDto userDto) {
    UserDto updatedUserDto = userService.updateUser(userId, userDto);
    return ResponseEntity.ok(updatedUserDto);
  }

  @Override
  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
