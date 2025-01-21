package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.TaskController;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

  private final TaskService taskService;

  @Override
  @GetMapping()// просмотр всех задач только админ
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<TaskDto>> getAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> tasksDtoList = taskService.getAllTasks(page, size, sortBy, direction);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(tasksDtoList);
  }

  @Override
  @GetMapping("/{task_id}")// просмотр задачи все аутентифицированные пользователи
  public ResponseEntity<TaskDto> getTaskById(@PathVariable("task_id") Long taskId)
      throws NotFoundException {
    TaskDto taskDto = taskService.getTaskById(taskId);
    return new ResponseEntity<>(taskDto, HttpStatus.OK);
  }

  @Override
  @GetMapping("/author/{authorId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<TaskDto>> getTasksByAuthor(@PathVariable Long authorId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> taskDtos = taskService.getTasksByAuthor(authorId, page, size, sortBy, direction);
    return new ResponseEntity<>(taskDtos, HttpStatus.OK);
  }

  @Override
  @GetMapping("/assignee/{assigneeId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<TaskDto>> getTasksByAssignee(@PathVariable Long assigneeId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> taskDtos = taskService.getTasksByAssignee(assigneeId, page, size, sortBy,
        direction);
    return new ResponseEntity<>(taskDtos, HttpStatus.OK);
  }

  @Override
  @PostMapping// создание задач только админ
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto)
      throws NotFoundException {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDto));
  }

  @Override
  @PutMapping("/{taskId}")
  @PreAuthorize("hasRole('ADMIN')")// обновление задач только админ
  public ResponseEntity<TaskDto> updateTask(@Valid @PathVariable Long taskId,
      @RequestBody TaskDto taskDto) throws NotFoundException {
    TaskDto updatedTaskDTO = taskService.updateTask(taskId, taskDto);
    return new ResponseEntity<>(updatedTaskDTO, HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/{taskId}")
  @PreAuthorize("hasRole('ADMIN')")//удаление задач только админ
  public ResponseEntity<Void> deleteTask(@Valid @PathVariable Long taskId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
