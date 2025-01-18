package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotFoundExceptionMessage {
  TASK_NOT_FOUND("Задание с таким id не найдено"),
  AUTHOR_NOT_FOUND("Пользователь с таким id не найден"),
  ASSIGNEE_NOT_FOUND("Пользователь с таким id не найден"),
  ROLE_NOT_FOUND("Такой роли не существует в БД"),
  COMMENT_NOT_FOUND("Комментарий с таким id не найден");

  private final String message;
}
