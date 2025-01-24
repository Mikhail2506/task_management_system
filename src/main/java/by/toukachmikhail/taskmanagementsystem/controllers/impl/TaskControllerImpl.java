package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.TaskController;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.services.TaskService;
import by.toukachmikhail.taskmanagementsystem.services.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
  private final UserDetailsServiceImpl userDetailsService;
  private final PagedResourcesAssembler<TaskDto> pagedResourcesAssembler;

  @Override
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<PagedModel<EntityModel<TaskDto>>> getAllTasks(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> tasksDtoList = taskService.getAllTasks(page, size, sortBy, direction);
    PagedModel<EntityModel<TaskDto>> pagedModel = pagedResourcesAssembler.toModel(tasksDtoList);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(pagedModel);
  }

  @Override
  @GetMapping("/{task_id}")
  public ResponseEntity<TaskDto> getTaskById(@PathVariable("task_id") Long taskId) {
    TaskDto taskDto = taskService.getTaskById(taskId);
    return new ResponseEntity<>(taskDto, HttpStatus.OK);
  }

  @Override
  @GetMapping("/assignee/{assigneeId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<PagedModel<EntityModel<TaskDto>>> getTasksByAssignee(
      @PathVariable Long assigneeId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> taskDtos = taskService.getTasksByAssignee(assigneeId, page, size, sortBy,
        direction);
    PagedModel<EntityModel<TaskDto>> pagedModel = pagedResourcesAssembler.toModel(taskDtos);
    return new ResponseEntity<>(pagedModel, HttpStatus.OK);
  }

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto)
      throws NotFoundException {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDto));
  }

  @Override
  @PutMapping("/{taskId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  public ResponseEntity<TaskDto> updateTask(@Valid @PathVariable Long taskId,
      @RequestBody TaskDto taskDto) {
    User currentUser = userDetailsService.getCurrentUser();
    TaskDto updatedTaskDTO = taskService.updateTask(taskId, taskDto, currentUser);
    return new ResponseEntity<>(updatedTaskDTO, HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/{taskId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> deleteTask(@Valid @PathVariable Long taskId) {
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
