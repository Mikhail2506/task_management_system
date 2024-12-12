package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.NOT_ACCEPTABLE;

public class NotAcceptableException extends BaseException {

  public NotAcceptableException() {super(NOT_ACCEPTABLE);
  }

  public NotAcceptableException(String message) {
    super(NOT_ACCEPTABLE, message);
  }
}
