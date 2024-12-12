package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.REQUEST_TIMEOUT;

public class RequestTimeoutException extends BaseException{

  public RequestTimeoutException(String message) {
    super(REQUEST_TIMEOUT);
  }

  public RequestTimeoutException(String message, Throwable cause) {
    super(REQUEST_TIMEOUT, message);
  }

}
