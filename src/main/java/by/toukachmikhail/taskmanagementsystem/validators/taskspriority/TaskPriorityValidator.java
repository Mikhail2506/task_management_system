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

  @Override
  public boolean isValid(TaskPriority priority, ConstraintValidatorContext context) {
    if (priority == null) {
      return false; // или true, если null допустим
    }
    return priority == TaskPriority.HIGH || priority == TaskPriority.MIDDLE || priority == TaskPriority.LOW;
  }
}
