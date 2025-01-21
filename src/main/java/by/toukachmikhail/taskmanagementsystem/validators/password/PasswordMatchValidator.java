package by.toukachmikhail.taskmanagementsystem.validators.password;

import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements
    ConstraintValidator<PasswordMatch, RegistrationUserDto> {

  @Override
  public void initialize(PasswordMatch constraintAnnotation) {
  }

  @Override
  public boolean isValid(RegistrationUserDto registrationUserDto,
      ConstraintValidatorContext constraintValidatorContext) {
    if (registrationUserDto.password() == null || registrationUserDto.confirmPassword() == null) {
      return false;
    }
    return registrationUserDto.password().equals(registrationUserDto.confirmPassword());


  }
}
