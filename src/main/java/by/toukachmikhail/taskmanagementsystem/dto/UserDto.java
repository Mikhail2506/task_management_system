package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import java.util.Set;
import lombok.Builder;

@Builder
public record UserDto(

//    @ValidIsDigit
//    Long id,
    String username,
    UserRole role
) {

}
