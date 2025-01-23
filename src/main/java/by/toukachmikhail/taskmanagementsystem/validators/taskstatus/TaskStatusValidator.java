package by.toukachmikhail.taskmanagementsystem.validators.taskstatus;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage.TASK_STATUS_ERROR;import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage.TASK_STATUS_NULL;

import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class TaskStatusValidator implements ConstraintValidator<ValidTaskStatus, TaskStatus> {

  @Override
  public boolean isValid(TaskStatus value, ConstraintValidatorContext context) {

    if (!Arrays.asList(TaskStatus.values()).contains(value)) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(TASK_STATUS_ERROR.getMessage())
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
