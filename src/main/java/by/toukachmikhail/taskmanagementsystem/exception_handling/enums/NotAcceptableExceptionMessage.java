package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotAcceptableExceptionMessage {
  INCORRECT_STOCK_ORDER_DATA("Проверьте корректность параметров заявки");

  private final String message;
}
