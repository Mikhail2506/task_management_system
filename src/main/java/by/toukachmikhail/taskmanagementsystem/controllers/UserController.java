package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {

  @GetMapping()
  ResponseEntity<List<UserDto>> getAllUsers();

  @GetMapping("/{user_id}")
  ResponseEntity<UserDto> getUserById(@PathVariable("user_id") Long userId);

  @PostMapping
  ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

  @PutMapping("/{userId}")
  ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto);

  @DeleteMapping("/{userId}")
  ResponseEntity<Void> deleteUser(@PathVariable Long userId);
}
