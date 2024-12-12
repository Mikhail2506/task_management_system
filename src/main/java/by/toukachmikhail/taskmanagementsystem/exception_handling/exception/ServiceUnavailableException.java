package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.SERVICE_UNAVAILABLE;

public class ServiceUnavailableException extends BaseException {

  public ServiceUnavailableException() {
    super(SERVICE_UNAVAILABLE);
  }

  public ServiceUnavailableException(String message) {
    super(SERVICE_UNAVAILABLE, message);
  }
}
