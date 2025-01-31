package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadRequestExceptionMessage {
  INVALID_INPUT_DATA("Не удалось прочитать тело запроса. Укажите корректные JSON данные"),
  INVALID_ARGUMENT_TYPE("Неверный тип аргумента. Укажите правильный тип параметра"),
  VALIDATION_FAILED("Валидация не удалась. Пожалуйста, проверьте входные данные и повторите попытку");

  private final String message;

}
