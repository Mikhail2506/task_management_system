package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.TaskManagementController;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.services.TaskManagementService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskManagementControllerImpl implements TaskManagementController {

  private final TaskManagementService taskManagementService;

  @Override
  @GetMapping()
  public ResponseEntity<Page<TaskDto>> getAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "taskId") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> tasksDtoList = taskManagementService.getAllTasks(page, size, sortBy, direction);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(tasksDtoList);
  }

  @Override
  @GetMapping("/{task_id}")
  public ResponseEntity<TaskDto> getTaskById(@PathVariable("task_id") Long taskId) {

    Optional<TaskDto> taskDto = taskManagementService.getTask(taskId);
    return taskDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Override
  @PostMapping
  public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskManagementService.saveTask(taskDto));
  }

  @Override
  @PutMapping("/{taskId}")
  public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId,
      @RequestBody TaskDto taskDto) {
    Optional<TaskDto> updatedTaskDto = taskManagementService.updateTask(taskId, taskDto);
    return updatedTaskDto.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Override
  @DeleteMapping("/{taskId}")
  public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
    if (taskManagementService.getTask(taskId).isPresent()) {
      taskManagementService.deleteTask(taskId);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
