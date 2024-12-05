package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface TaskManagementController {

  @GetMapping("/tasks")
  ResponseEntity<Page<TaskDto>> showAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction);

  @GetMapping("/tasks/{task_id}")
  ResponseEntity<Optional<TaskDto>> showSingleTask(@PathVariable("task_id") Long taskId);

  @PostMapping("/tasks")
  ResponseEntity<HttpStatus> addNewTask(@RequestBody TaskDto taskDto);

  @PatchMapping("/tasks/")
  ResponseEntity<HttpStatus> updateTask(@RequestBody TaskDto taskDto);

  @DeleteMapping("/tasks")
  ResponseEntity<HttpStatus> removeAllTasks();

  @DeleteMapping("/tasks/{task_id}")
  ResponseEntity<HttpStatus> updateTask(@PathVariable("task_id") Long taskId);
}
