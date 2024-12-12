package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {

  private final UserMapper userMapper;

  private final  CommentMapper commentMapper;

  public  TaskDto entityToDto(Task task) {
    return TaskDto.builder()
        //.id(task.getId())
        .header(task.getHeader())
        .description(task.getDescription())
        .status(task.getStatus())
        .priority(task.getTaskPriority())
        .author(userMapper.entityToDto(task.getAuthor()))
        .assignee(userMapper.entityToDto(task.getAssignee()))
        .comments(task.getComments().stream()
            .map(commentMapper::entityToDTO)
            .collect(Collectors.toList()))
        .build();
  }

  public Task dtoToEntity(TaskDto taskDto) {
    Task task = new Task();
   // task.setId(taskDto.id());
    task.setHeader(taskDto.header());
    task.setDescription(taskDto.description());
    task.setStatus(taskDto.status());
    task.setTaskPriority(taskDto.priority());
    task.setAuthor(userMapper.dtoToEntity(taskDto.author()));
    task.setAssignee(userMapper.dtoToEntity(taskDto.assignee()));
    task.setComments(taskDto.comments().stream()
        .map(commentMapper::dtoToEntity)
        .collect(Collectors.toList()));
    return task;
  }
}
