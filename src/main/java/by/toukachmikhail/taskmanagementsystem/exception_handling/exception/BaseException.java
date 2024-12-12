package by.toukachmikhail.taskmanagementsystem.exception_handling.exception;

import by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class BaseException extends RuntimeException {

  private final ExceptionType type;
  private final HttpStatus httpStatus;

  public BaseException(ExceptionType type) {
    super(type.getMessage());
    this.type = type;
    this.httpStatus = type.getHttpStatus();
  }

  public BaseException(ExceptionType type, String message) {
    super(message);
    this.type = type;
    this.httpStatus = type.getHttpStatus();
  }
}

