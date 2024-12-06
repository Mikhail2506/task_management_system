package by.toukachmikhail.taskmanagementsystem.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record TaskDto(
    String header,
    String description,
    String status,
    String priority,
    List<UserDto> usersDto,
    String comment,
    Long taskId) {

}
