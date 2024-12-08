package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.TasksPriorities;
import by.toukachmikhail.taskmanagementsystem.entities.TasksStatuses;
import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import java.util.stream.Collectors;


public class TaskMapper {

  public static TaskDto entityToDto(Task task) {
    return TaskDto.builder()
        .header(task.getHeader())
        .description(task.getDescription())
        .status(task.getStatus().getStatus().name())
        .priority(task.getTaskPriority().getPriority().name())
        .usersDto(task.getUsers().stream().map(UserMapper::entityToDto).collect(Collectors.toList()))
        .comment(task.getComment())
        .taskId(task.getTaskId())
        .build();
  }

  public static Task dtoToEntity(TaskDto taskDto) {
    return Task.builder()
        .header(taskDto.header())
        .description(taskDto.description())
        .status(TasksStatuses.builder().status(TaskStatus.valueOf(taskDto.status())).build())
        .taskPriority(TasksPriorities.builder().priority(TaskPriority.valueOf(taskDto.priority())).build())
        .users(taskDto.usersDto().stream().map(UserMapper::dtoToEntity).collect(Collectors.toList()))
        .comment(taskDto.comment())
        .taskId(taskDto.taskId())
        .build();
  }
}
