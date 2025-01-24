package by.toukachmikhail.taskmanagementsystem.validators.taskspriority;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskPriorityValidator implements ConstraintValidator<ValidTaskPriority, TaskPriority> {

  @Override
  public boolean isValid(TaskPriority priority, ConstraintValidatorContext context) {
    if (priority == null) {
      return false; // или true, если null допустим
    }
    return priority == TaskPriority.HIGH || priority == TaskPriority.MIDDLE || priority == TaskPriority.LOW;
  }
}
