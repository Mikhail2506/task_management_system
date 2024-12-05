package by.toukachmikhail.taskmanagementsystem.dto;

import java.util.Set;

public record UserDto(
String username,
String role,
String usersPhoneNumber,
Set<TaskDto> tasksDto
) {

}
