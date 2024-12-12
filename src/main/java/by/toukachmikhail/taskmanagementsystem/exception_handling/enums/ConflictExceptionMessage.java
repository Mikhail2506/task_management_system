package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConflictExceptionMessage {
  NOT_ENOUGH_MONEY(
      "Недостаточно средств");
  private final String message;
}
