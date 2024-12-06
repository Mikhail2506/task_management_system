package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface TaskManagementController {


  @GetMapping()
  ResponseEntity<Page<TaskDto>> getAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "taskId") String sortBy,
      @RequestParam(defaultValue = "asc") String direction);

  @GetMapping("/{task_id}")
  ResponseEntity<TaskDto> getTaskById(@PathVariable("task_id") Long taskId);

  @PostMapping
  ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto);

  @PutMapping("/{taskId}")
  ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId,
      @RequestBody TaskDto taskDto);

  @DeleteMapping("/{taskId}")
  ResponseEntity<Void> deleteTask(@PathVariable Long taskId);
}
