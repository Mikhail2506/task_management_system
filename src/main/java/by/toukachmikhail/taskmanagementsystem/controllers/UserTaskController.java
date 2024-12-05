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
import org.springframework.web.bind.annotation.RequestBody;

public interface UserTaskController {

  @GetMapping("/")
  ResponseEntity<List<UserDto>> showAllUsers();

  @GetMapping("/{user_id}")
  ResponseEntity<UserDto> showSingleUser(@PathVariable("user_id") Long taskId);

  @PostMapping()
  ResponseEntity<HttpStatus> addNewTask(@RequestBody UserDto userDto);

  @PatchMapping("/")
  ResponseEntity<HttpStatus> updateTask(@RequestBody TaskDto taskDto);

  @DeleteMapping()
  ResponseEntity<HttpStatus> removeAllTasks();

  @DeleteMapping("/{user_id}")
  ResponseEntity<HttpStatus> updateTask(@PathVariable("user_id") Long userId);
}
