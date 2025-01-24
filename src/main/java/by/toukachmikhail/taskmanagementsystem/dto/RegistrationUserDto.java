package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.validators.password.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatch
public record RegistrationUserDto(
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    String username,

    @NotBlank(message = "Users email required")
    @Email(message = "Invalid email format")
    String email,

    @Size(min = 3, max = 256, message = "Password must be between 3 and 256 characters")
    String password,

    @Size(min = 3, max = 256, message = "Password must be between 3 and 256 characters")
    String confirmPassword
) {

}
