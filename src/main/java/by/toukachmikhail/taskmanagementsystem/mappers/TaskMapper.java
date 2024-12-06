package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Lazy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    //uses = {UserMapper.class},
    imports = {TaskPriority.class, TaskStatus.class})
@RequiredArgsConstructor
public abstract class TaskMapper {

  @Lazy
  protected UserMapper userMapper;

  @Mapping(source = "status.status", target = "status")
  @Mapping(source = "taskPriority.priority", target = "priority")
  @Mapping(source = "users", target = "usersDto")
  public  abstract TaskDto entityToDto(Task task);

  @Mapping(source = "status", target = "status.status")
  @Mapping(source = "priority", target = "taskPriority.priority")
  @Mapping(source = "usersDto", target = "users")
  public  abstract Task dtoToEntity(TaskDto taskDto);

  public  abstract List<TaskDto> toDtoList(Set<Task> tasks);

  public  abstract Set<Task> toEntitySet(List<TaskDto> taskDtos);
}
