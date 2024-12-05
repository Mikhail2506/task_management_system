package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TaskManagementService {

  Page<TaskDto> getAllTasks(int page, int size, String sortBy, String direction);

  Optional<TaskDto> getTask(Long taskId);

  void saveTask(TaskDto taskDto);

  void correctTask(TaskDto taskDto);

  void deleteAllTasks();

  void deleteSingleTask(Long taskId);

}
