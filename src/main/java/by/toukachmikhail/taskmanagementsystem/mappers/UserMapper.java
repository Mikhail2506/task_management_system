package by.toukachmikhail.taskmanagementsystem.mappers;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = SPRING,
    uses = {TaskMapper.class})
public interface UserMapper {

  User dtoToEntity(UserDto userDto);

  UserDto entityToDto(User user);

  List<UserDto> toDtoList(Set<User> users);

  Set<User> toEntitySet(List<UserDto> userDtos);

}
