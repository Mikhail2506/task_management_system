package by.toukachmikhail.taskmanagementsystem.mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.Role;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Lazy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = SPRING,
    //uses = {TaskMapper.class},
    imports = {Role.class})
@RequiredArgsConstructor
public abstract class UserMapper {

  @Lazy
  protected TaskMapper taskMapper;

  @Mapping(source = "role.usersRole", target = "role")
  @Mapping(source = "tasks", target = "tasksDto")
  public  abstract UserDto entityToDto(User user);

  @Mapping(source = "role", target = "role.usersRole")
  @Mapping(source = "tasksDto", target = "tasks")
  public  abstract User dtoToEntity(UserDto userDto);

  public  abstract List<UserDto> toDtoList(Set<User> users);

  public  abstract Set<User> toEntitySet(List<UserDto> userDtos);

}
