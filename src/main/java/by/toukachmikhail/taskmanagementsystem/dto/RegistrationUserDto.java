package by.toukachmikhail.taskmanagementsystem.dto;

import jakarta.validation.constraints.Size;

public record RegistrationUserDto(
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    String username,
    @Size(min = 3, max = 256, message = "Password must be between 3 and 256 characters")
    String password,
    @Size(min = 3, max = 256, message = "Password must be between 3 and 256 characters")
    String confirmPassword
) {

}
