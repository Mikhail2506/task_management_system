package by.toukachmikhail.taskmanagementsystem.dto;

import java.util.Set;
import lombok.Builder;

@Builder
public record UserDto(
String username,
String role,
String usersPhoneNumber,
Set<TaskDto> tasksDto
) {

}
