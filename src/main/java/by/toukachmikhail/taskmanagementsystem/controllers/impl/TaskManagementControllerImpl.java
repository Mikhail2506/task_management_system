package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.TaskManagementController;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.services.TaskManagementService;
import by.toukachmikhail.taskmanagementsystem.services.impl.TaskManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task-management")
@RequiredArgsConstructor
public class TaskManagementControllerImpl implements TaskManagementController {

  private final TaskManagementService taskManagementService;

  @Override
  @GetMapping("/tasks")
  public ResponseEntity<Page<TaskDto>> showAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "taskId") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {
    Page<TaskDto> tasksDtoList = taskManagementService.getAllTasks(page, size, sortBy, direction);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(tasksDtoList);
  }

  @Override
  @GetMapping("/tasks/{task_id}")
  public ResponseEntity<TaskDto> showSingleTask(@PathVariable("task_id") Long taskId) {

    TaskDto taskDto = taskManagementService.getTask(taskId);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(taskDto);
  }

  @Override
  @PostMapping("/tasks")
  public ResponseEntity<HttpStatus> addNewTask(@RequestBody TaskDto taskDto){

    taskManagementService.saveTask(taskDto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Override
  @PatchMapping("/tasks")
  public ResponseEntity<HttpStatus> updateTask(@RequestBody TaskDto taskDto) {

    taskManagementService.correctTask(taskDto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/tasks")
  public ResponseEntity<HttpStatus> removeAllTasks(){

    taskManagementService.deleteAllTasks();
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/tasks/{task_id}")
  public ResponseEntity<HttpStatus> updateTask(@PathVariable("task_id") Long taskId){

    taskManagementService.deleteSingleTask(taskId);
    return ResponseEntity.ok(HttpStatus.OK);
  }

}
