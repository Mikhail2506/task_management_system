package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.validators.digits.ValidIsDigit;
import java.util.Set;
import lombok.Builder;

@Builder
public record UserDto(

//    @ValidIsDigit
//    Long id,
    String username,
    Set<String> roles
) {

}
