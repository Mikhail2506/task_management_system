package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationExceptionMessage {
  TASK_STATUS_NULL("Статус задачи не может быть null"),
  TASK_STATUS_ERROR("Некоректный статус задачи. Только WAITING, PROCESSING, FINISHED разрешены"),
  TASK_PRIORITY_NULL("Приоритете задачи не может быть null"),
  TASK_PRIORITY_ERROR("Некорректныф приоритет задачи. Только HIGH, MIDDLE, LOW разрешены"),
  VERIFY_PASSWORD_MATCHING("Password and confirmPassword не совпадают");
  private final String message;
}
