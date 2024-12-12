package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.NOT_FOUND;

public class NotFoundException extends BaseException {

  public NotFoundException() {
    super(NOT_FOUND);
  }

  public NotFoundException(String message) {
    super(NOT_FOUND, message);
  }
}
