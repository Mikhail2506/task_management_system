package by.toukachmikhail.taskmanagementsystem.mappers;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final CommentMapper commentMapper;

  public TaskDto entityToDto(Task task) {
    return TaskDto.builder()
        .header(task.getHeader())
        .description(task.getDescription())
        .status(task.getStatus())
        .priority(task.getTaskPriority())
        .assignee(userMapper.entityToDto(task.getAssignee()))
        .comments(task.getComments().stream()
         .map(commentMapper::entityToDTO)
          .collect(Collectors.toList()))
        .build();
  }

  public Task dtoToEntity(TaskDto taskDto) {
    Task task = new Task();
    task.setHeader(taskDto.header());
    task.setDescription(taskDto.description());
    task.setStatus(taskDto.status());
    task.setTaskPriority(taskDto.priority());

    User assignee = userRepository.findByEmail(taskDto.assignee().email())
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    task.setAssignee(assignee);

    if (taskDto.comments() != null && !taskDto.comments().isEmpty()) {
      for (CommentDto commentDto : taskDto.comments()) {
        Comment comment = commentMapper.dtoToEntity(commentDto);
        comment.setTask(task);
        comment.setUser(task.getAssignee());
        task.getComments().add(comment);
      }
    }
    return task;
  }
}
