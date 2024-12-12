package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationExceptionMessage {
  TASK_STATUS_NULL("Task Status cannot be null"),
  TASK_STATUS_ERROR("Invalid TaskStatus. Only WAITING, PROCESSING, FINISHED are allowed"),
  TASK_PRIORITY_NULL("Task Priority cannot be null"),
  TASK_PRIORITY_ERROR("Invalid Task Priority. Only HIGH, MIDDLE, LOW are allowed");
  private final String message;
}
