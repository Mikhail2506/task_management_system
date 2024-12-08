package by.toukachmikhail.taskmanagementsystem.services.impl;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.services.TaskManagementService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {

  private final TaskRepository taskRepository;

  /**
   * @return
   */
  @Override
  public Page<TaskDto> getAllTasks(int page, int size, String sortBy, String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Task> allTasks = taskRepository.findAll(pageable);

    return allTasks.map(TaskMapper::entityToDto);
  }

  /**
   * @param taskId
   * @return
   */
  @Override
  public Optional<TaskDto> getTask(Long taskId) {

    return taskRepository.findById(taskId).map(TaskMapper::entityToDto);
  }

  /**
   * @param taskDto
   * @return
   */
  @Override
  public TaskDto saveTask(TaskDto taskDto) {
    Task task = TaskMapper.dtoToEntity(taskDto);
    task = taskRepository.save(task);
    return TaskMapper.entityToDto(task);
  }

  /**
   * @param taskDto
   */
  @Override
  public Optional<TaskDto> updateTask(Long taskId, TaskDto taskDto) {
    return taskRepository.findById(taskId).map(existingTask -> {
      Task updatedTask = TaskMapper.dtoToEntity(taskDto);
      updatedTask.setTaskId(taskId);
      updatedTask = taskRepository.save(updatedTask);
      return TaskMapper.entityToDto(updatedTask);
    });
  }

  /**
   * @param taskId
   */
  @Override
  public void deleteTask(Long taskId) {
    taskRepository.deleteById(taskId);
  }

}
