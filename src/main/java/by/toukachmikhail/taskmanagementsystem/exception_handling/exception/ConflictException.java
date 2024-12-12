package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.CONFLICT;

public class ConflictException extends BaseException {

  public ConflictException() {
    super(CONFLICT);
  }

  public ConflictException(String message) {
    super(CONFLICT, message);
  }
}
