package by.toukachmikhail.taskmanagementsystem.validators.taskspriority;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage.TASK_PRIORITY_ERROR;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage.TASK_STATUS_ERROR;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage.TASK_STATUS_NULL;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import java.util.Arrays;

public class TaskPriorityValidator implements ConstraintValidator<ValidTaskPriority, TaskPriority> {

  /**
   * @param value   object to validate
   * @param context context in which the constraint is evaluated
   */
  @Override
  public boolean isValid(TaskPriority value, ConstraintValidatorContext context) {

    if (!Arrays.asList(TaskStatus.values()).contains(value)) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(TASK_PRIORITY_ERROR.getMessage())
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
