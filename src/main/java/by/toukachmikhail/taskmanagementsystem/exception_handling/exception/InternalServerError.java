package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.INTERNAL_SERVER_ERROR;

public class InternalServerError extends BaseException {

  public InternalServerError() {
    super(INTERNAL_SERVER_ERROR);
  }

  public InternalServerError(String message) {
    super(INTERNAL_SERVER_ERROR, message);
  }

}
