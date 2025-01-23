package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Task management controller",
    description = "Controller to create, show, update and delete tasks")
public interface TaskController {


  @GetMapping // просмотр всех задач только админ
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<PagedModel<EntityModel<TaskDto>>> getAllTasks(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction);

  @GetMapping("/{task_id}")// просмотр задачи все аутентифицированные пользователи
  ResponseEntity<TaskDto> getTaskById(@PathVariable("task_id") Long taskId);


  @GetMapping("/assignee/{assigneeId}") // задачи по исполнителю (только админ)
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<PagedModel<EntityModel<TaskDto>>> getTasksByAssignee(
      @PathVariable Long assigneeId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction);

  @PostMapping// создание задач только админ
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto)
      throws NotFoundException;

  @PutMapping("/{taskId}")
  @PreAuthorize("hasAuthority('ADMIN')")// обновление задач только админ
  ResponseEntity<TaskDto> updateTask(@Valid @PathVariable Long taskId,
      @RequestBody TaskDto taskDto) throws NotFoundException;

  @DeleteMapping("/{taskId}")
  @PreAuthorize("hasAuthority('ADMIN')")//удаление задач только админ
  ResponseEntity<Void> deleteTask(@Valid @PathVariable Long taskId);
}
