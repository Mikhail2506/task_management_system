package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.UserTaskController;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task-management/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserTaskController{

  private final UserService userService;

  @Override
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> showAllUsers() {
    List<UserDto> usersDtoList = userService.getAllUsers();
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(usersDtoList);
  }

  @Override
  @GetMapping("/{user_id}")
  public ResponseEntity<UserDto> showSingleUser(@PathVariable("user_id") Long taskId) {

    UserDto userDto = userService.getUser(taskId);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userDto);
  }

  @Override
  @PostMapping()
  public ResponseEntity<HttpStatus> addNewTask(@RequestBody UserDto userDto){

    userService.saveUser(userDto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Override
  @PatchMapping("/")
  public ResponseEntity<HttpStatus> updateTask(@RequestBody TaskDto taskDto) {

    userService.correctUsersInfo(taskDto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Override
  @DeleteMapping()
  public ResponseEntity<HttpStatus> removeAllTasks(){

    userService.deleteAllUsers();
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/{user_id}")
  public ResponseEntity<HttpStatus> updateTask(@PathVariable("user_id") Long userId){

    userService.deleteUser(userId);
    return ResponseEntity.ok(HttpStatus.OK);
  }
}
