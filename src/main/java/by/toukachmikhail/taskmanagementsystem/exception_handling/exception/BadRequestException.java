package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.BAD_REQUEST;

public class BadRequestException extends BaseException {

  public BadRequestException() {
    super(BAD_REQUEST);
  }

  public BadRequestException(String message) {
    super(BAD_REQUEST, message);
  }
}
