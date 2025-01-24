package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import lombok.Builder;

@Builder
public record UserDto(

    String username,
    UserRole role
) {

}
