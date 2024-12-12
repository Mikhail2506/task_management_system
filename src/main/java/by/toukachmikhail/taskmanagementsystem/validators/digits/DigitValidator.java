package by.toukachmikhail.taskmanagementsystem.validators.digits;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DigitValidator implements ConstraintValidator<ValidIsDigit, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    if (value == null) {
      return true;
    }
    return value.matches("\\d+");
  }
}
