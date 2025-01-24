package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.NOT_ACCEPTABLE;

public class AccessDeniedException extends BaseException {

  public AccessDeniedException() {super(NOT_ACCEPTABLE);
  }

  public AccessDeniedException(String message) {
    super(NOT_ACCEPTABLE, message);
  }
}

