package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface TaskService {


  Page<TaskDto> getAllTasks(int page, int size, String sortBy, String direction);

  TaskDto getTaskById(Long taskId);


  Page<TaskDto> getTasksByAuthor(Long authorId, int page, int size, String sortBy,
      String direction);

  Page<TaskDto> getTasksByAssignee(Long assigneeId, int page, int size, String sortBy,
      String direction);

  @Transactional
  TaskDto createTask(TaskDto taskDTO) throws NotFoundException;

  TaskDto updateTask(Long id, TaskDto taskDTO, User currentUser);

  void deleteTask(Long taskId);

  boolean isAssignee(Long taskId, String username);
}
