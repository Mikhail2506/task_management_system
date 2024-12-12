package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.BAD_REQUEST;

public class ValidationException extends BaseException {

  public ValidationException() {
    super(BAD_REQUEST);
  }
  public ValidationException(String message) {
    super(BAD_REQUEST, message);
  }
}
