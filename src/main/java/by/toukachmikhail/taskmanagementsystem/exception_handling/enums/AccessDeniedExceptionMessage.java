package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessDeniedExceptionMessage {
 ACCESS_DENIED_EXCEPTION_MESSAGE("Пользователь не авторизован");
  private final String message;
}
