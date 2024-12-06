package by.toukachmikhail.taskmanagementsystem.services.impl;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.services.TaskManagementService;
import java.util.NoSuchElementException;
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
  private final TaskMapper taskMapper;

  /**
   * @return
   */
  @Override
  public Page<TaskDto> getAllTasks(int page, int size, String sortBy, String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Task> allTasks = taskRepository.findAll(pageable);

    return allTasks.map(taskMapper::entityToDto);
  }

  /**
   * @param taskId
   * @return
   */
  @Override
  public TaskDto getTask(Long taskId) {

    Task task = taskRepository.findById(taskId).orElseThrow(
        () -> new NoSuchElementException(String.format("Задание с taskId '%s' не найдено ", taskId)));

    return taskMapper.entityToDto(task);
  }

  /**
   * @param taskDto
   */
  @Override
  public void saveTask(TaskDto taskDto) {

  }

  /**
   * @param taskDto
   */
  @Override
  public void correctTask(TaskDto taskDto) {

  }

  /**
   *
   */
  @Override
  public void deleteAllTasks() {

  }

  /**
   * @param taskId
   */
  @Override
  public void deleteSingleTask(Long taskId) {

  }

}
