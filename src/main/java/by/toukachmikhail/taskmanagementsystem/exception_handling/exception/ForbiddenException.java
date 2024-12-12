package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.FORBIDDEN;

public class ForbiddenException extends BaseException {

  public ForbiddenException() {
    super(FORBIDDEN);
  }

  public ForbiddenException(String message) {
    super(FORBIDDEN, message);
  }
}
