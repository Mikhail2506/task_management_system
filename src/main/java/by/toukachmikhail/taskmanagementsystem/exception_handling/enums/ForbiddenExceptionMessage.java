package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ForbiddenExceptionMessage {
  COMMENT_CREATION_DENIED("Создание и редактирование комментариев запрещено - нет доступа к задаче");
  private final String message;
}
