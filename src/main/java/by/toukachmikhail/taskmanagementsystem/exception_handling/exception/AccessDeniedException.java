package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType;

public class AccessDeniedException extends BaseException {

  public AccessDeniedException() {super(ExceptionType.UNAUTHORIZED);
  }

  public AccessDeniedException(String message) {
    super(ExceptionType.UNAUTHORIZED, message);
  }
}

