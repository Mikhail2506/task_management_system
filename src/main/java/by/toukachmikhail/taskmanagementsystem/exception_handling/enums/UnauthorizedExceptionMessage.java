package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnauthorizedExceptionMessage {
  TOKEN_HAS_EXPIRED("Срок действия токена истек"),
  TOKEN_NOT_VALID("Токен не валиден из-за неверной подписи или некорректной структуры"),
  USER_NOT_AUTHENTICATED("Пользователь не авторизован");
  private final String message;
}
