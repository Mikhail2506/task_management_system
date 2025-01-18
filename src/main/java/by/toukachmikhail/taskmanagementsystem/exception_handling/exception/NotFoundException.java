package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.NOT_FOUND;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

  public NotFoundException() {
    super(NOT_FOUND);
  }

  public NotFoundException(String message) {
    super(NOT_FOUND, message);
  }

  public NotFoundException(String message, HttpStatus httpStatus) {
    super(NOT_FOUND, message);
  }
}
