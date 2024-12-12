package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.UNAUTHORIZED;

public class UnauthorizedException extends BaseException {

  public UnauthorizedException() {
    super(UNAUTHORIZED);
  }

  public UnauthorizedException(String message) {
    super(UNAUTHORIZED, message);
  }
}
