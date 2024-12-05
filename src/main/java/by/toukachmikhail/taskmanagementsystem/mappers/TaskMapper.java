package by.toukachmikhail.taskmanagementsystem.mappers;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;

import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = SPRING,
    uses = {UserMapper.class})
    //imports = {Instant.class, UUID.class, OrderStatus.class, OrderIntention.class}

public interface TaskMapper {

  Task dtoToEntity(TaskDto taskDto);

  TaskDto entityToDto(Task task);

  List<TaskDto> toDtoList(Set<Task> tasks);

  Set<Task> toEntitySet(List<TaskDto> taskDtos);
}
