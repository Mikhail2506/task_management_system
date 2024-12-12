package by.toukachmikhail.taskmanagementsystem.exception_handling;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.BadRequestExceptionMessage.INVALID_ARGUMENT_TYPE;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.BadRequestExceptionMessage.INVALID_INPUT_DATA;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.BAD_REQUEST;
import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.BaseException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

/**
 * GlobalExceptionHandler is a centralized exception handler for handling various types of
 * exceptions that may occur during request processing in the application. It provides consistent
 * error responses in a JSON format with appropriate HTTP status codes.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  public static final String REJECTED_FOR_REASON = "\033[35m{}: \033[0;97m{}\033[0m";
  public static final String THROWABLE_INFO = "\033[31m{}: \033[0;97m{}\033[0m";

  /**
   * Handles BaseException, a base exception class for custom exceptions in the application.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception BaseException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponseDTO> handleBaseException(
      HttpServletRequest request, BaseException exception) {

    var response = buildResponseEntity(request, exception.getHttpStatus(), exception.getMessage(), Collections.emptyList());
    logException(response, exception);

    return response;
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
      HttpServletRequest request, HttpMessageNotReadableException exception) {

    Throwable cause = exception.getCause();

    if (cause instanceof InvalidFormatException formatException) {
      if (formatException.getTargetType() != null && formatException.getTargetType().isEnum()) {
        String invalidValue = formatException.getValue().toString();
        String errorMessage = String.format("Invalid value '%s' for enum '%s'", invalidValue, formatException.getTargetType().getSimpleName());
        return buildResponseEntity(request, HttpStatus.BAD_REQUEST, errorMessage, Collections.emptyList());
      }
    }

    log.error("Error parsing request body", exception);

    return buildResponseEntity(request, HttpStatus.BAD_REQUEST, INVALID_INPUT_DATA.getMessage(), Collections.emptyList());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleNotFoundException(HttpServletRequest request,NotFoundException exception) {
    return buildResponseEntity(
        request, HttpStatus.NOT_FOUND, exception.getMessage());
  }

  /**
   * Handles ConstraintViolationException, an exception thrown when constraint validation fails.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception ConstraintViolationException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */

  /**
   * Handles ResponseStatusException, standard exception, extended from Exception for returning
   * status and message.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception ResponseStatusException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(
      HttpServletRequest request, ResponseStatusException exception) {

    var response = buildResponseEntity(
        request, (HttpStatus) exception.getStatusCode(), exception.getMessage());
    logException(response, exception);

    return response;
  }
//
//  @ExceptionHandler(AccessDeniedException.class)
//  public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(HttpServletRequest request,
//      AccessDeniedException exception) {
//    ForbiddenException forbiddenException = new ForbiddenException(exception.getMessage());
//    return handleBaseException(request, forbiddenException);
//  }
//
//  /**
//   * Handles AuthenticationException, an exception thrown when jwt token is not valid or has expired
//   * or there is no token in the header.
//   *
//   * @param request   HttpServletRequest object representing the HTTP request
//   * @param exception AuthenticationException object representing the thrown exception
//   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
//   */
//  @ExceptionHandler(AuthenticationException.class)
//  public ResponseEntity<ErrorResponseDTO> handleAuthException(HttpServletRequest request,
//      Exception exception) {
//    String message = "";
//    if (request.getAttribute("message") != null) {
//      message = (String) request.getAttribute("message");
//    }
//
//    String responseMessage = switch (message) {
//      case ("expired") -> TOKEN_HAS_EXPIRED.getMessage();
//      case ("wrongToken") -> TOKEN_NOT_VALID.getMessage();
//      default -> UNAUTHORIZED.getMessage();
//    };
//    var response = buildResponseEntity(request, UNAUTHORIZED, responseMessage);
//    logException(response, exception);
//
//    return response;
//  }
//
  /**
   * Handles MethodArgumentNotValidException, an exception thrown when method argument validation
   * fails.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception MethodArgumentNotValidException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */

//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
//      HttpServletRequest request, MethodArgumentNotValidException exception) {
//
//    List<String> errors = exception.getBindingResult().getFieldErrors().stream()
//        .map(FieldError::getDefaultMessage)
//        .collect(Collectors.toList());
//
//    var response = buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), "Validation failed", errors);
//    logValidationException(response, exception);
//
//    return response;
//  }

  /**
   * Handles ConstraintViolationException, an exception thrown when constraint validation fails.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception ConstraintViolationException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(
      HttpServletRequest request, Exception exception) {

    var response = buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), INVALID_INPUT_DATA.getMessage(),
        Collections.emptyList());
    logValidationException(response, exception);

    return response;
  }

  /**
   * Handles HttpMessageConversionException, an exception thrown when request body cannot be read.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception HttpMessageNotReadableException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
//  @ExceptionHandler(HttpMessageConversionException.class)
//  public ResponseEntity<ErrorResponseDTO> handleHttpMessageConversionException(
//      HttpServletRequest request, Exception exception) {
//
//    var response = buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), exception.getMessage(), Collections.emptyList());
//    logValidationException(response, exception);
//
//    return response;
//  }

  /**
   * Handles HttpMessageNotReadableException, an exception thrown when method argument type mismatch
   * occurs.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception MethodArgumentTypeMismatchException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(
      HttpServletRequest request, Exception exception) {

    var response = buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), INVALID_ARGUMENT_TYPE.getMessage(),
        Collections.emptyList());
    logValidationException(response, exception);

    return response;
  }

//  /**
//   * Handles general exceptions that are not specifically handled by other methods.
//   *
//   * @param request   HttpServletRequest object representing the HTTP request
//   * @param exception Object representing the thrown exception
//   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
//   */
//  @ExceptionHandler({Exception.class})
//  public ResponseEntity<ErrorResponseDTO> handleInternalServerError(
//      HttpServletRequest request, Throwable exception) {
//
//    var response = buildResponseEntity(request, INTERNAL_SERVER_ERROR,
//        INTERNAL_SERVER_ERROR.getMessage());
//    logException(response, exception);
//
//    return response;
//  }

//  /**
//   * Builds a ResponseEntity object with the provided error details.
//   *
//   * @param request       HttpServletRequest object representing the HTTP request
//   * @param exceptionType ExceptionType representing the type of exception
//   * @param message       String containing the error message
//   * @param errors
//   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
//   */
//  private ResponseEntity<ErrorResponseDTO> buildResponseEntity(
//      HttpServletRequest request, ExceptionType exceptionType, String message, List<String> errors) {
//
//    String uri = request.getServletPath();
//
//    var response = ErrorResponseDTO.builder()
//        .uri(uri)
//        .type(exceptionType.toString())
//        .message(message)
//        .timestamp(LocalDateTime.now())
//        .build();
//    return ResponseEntity
//        .status(exceptionType.getHttpStatus())
//        .contentType(MediaType.APPLICATION_JSON)
//        .body(response);
//  }

  /**
   * Builds a ResponseEntity object with the provided error details.
   *
   * @param request    HttpServletRequest object representing the HTTP request
   * @param statusCode HttpStatusCode representing the statusCode of exception
   * @param message    String containing the error message
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */

  private ResponseEntity<ErrorResponseDTO> buildResponseEntity(
      HttpServletRequest request, HttpStatus statusCode, String message, List<String> details) {

    String uri = request.getServletPath();

    var response = ErrorResponseDTO.builder()
        .uri(uri)
        .type(statusCode.toString())
        .message(message)
        .timestamp(LocalDateTime.now())
        .details(details)
        .build();
    return ResponseEntity
        .status(statusCode)
        .contentType(MediaType.APPLICATION_JSON)
        .body(response);
  }

  private ResponseEntity<ErrorResponseDTO> buildResponseEntity(
      HttpServletRequest request, HttpStatus statusCode, String message) {
    return buildResponseEntity(request, statusCode, message, Collections.emptyList());
  }
  /**
   * Logs information about the thrown exception.
   *
   * @param response  ResponseEntity containing ErrorResponseDTO with appropriate error details
   * @param exception Object representing the thrown exception
   */
  private void logException(ResponseEntity<ErrorResponseDTO> response, Throwable exception) {
    StackTraceElement[] stackTrace = exception.getStackTrace();
    String location = stackTrace.length > 0 ? stackTrace[0].toString() : "unknown source";
    log.error(THROWABLE_INFO, exception.getClass().getSimpleName(), location);
    log.error(REJECTED_FOR_REASON, "Response", response.getBody());
  }

  /**
   * Logs information about the thrown validation exception.
   *
   * @param response  ResponseEntity containing ErrorResponseDTO with appropriate error details
   * @param exception Object representing the thrown exception
   */
  private void logValidationException(ResponseEntity<ErrorResponseDTO> response,
      Throwable exception) {
    String location = "unknown source";
    StackTraceElement[] stackTrace = exception.getStackTrace();
    for (StackTraceElement element : stackTrace) {

      if (element.getClassName().startsWith("by.toukachmikhail.taskmanagementsystem.controllers")) {
        location = element.toString();
        break;
      }
    }
    log.error(THROWABLE_INFO, exception.getClass().getSimpleName(), location);
    log.error(REJECTED_FOR_REASON, "Response", response.getBody());
  }
}
