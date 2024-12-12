package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnauthorizedExceptionMessage {
  INCORRECT_PIN("Неверный код подтверждения"),
  PIN_HAS_EXPIRED("Срок действия кода подтверждения истек"),
  TOKEN_HAS_EXPIRED("Срок действия токена истек"),
  TOKEN_NOT_VALID("Токен не валиден из-за неверной подписи или некорректной структуры"),
  NOT_ACCESS("Access to the document is denied");
  private final String message;
}
