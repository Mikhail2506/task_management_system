package by.toukachmikhail.taskmanagementsystem.exception_handling;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.BadRequestExceptionMessage.INVALID_ARGUMENT_TYPE;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.BadRequestExceptionMessage.INVALID_INPUT_DATA;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.BAD_REQUEST;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.INTERNAL_SERVER_ERROR;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ExceptionType.UNAUTHORIZED;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.UnauthorizedExceptionMessage.TOKEN_HAS_EXPIRED;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.UnauthorizedExceptionMessage.TOKEN_NOT_VALID;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.ValidationExceptionMessage.VERIFY_PASSWORD_MATCHING;

import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.BaseException;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.ForbiddenException;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    return buildResponseEntity(request, exception.getHttpStatus(), exception.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(
      HttpServletRequest request, HttpMessageNotReadableException exception) {

    Throwable cause = exception.getCause();

    if (cause instanceof InvalidFormatException formatException) {
      if (formatException.getTargetType() != null && formatException.getTargetType().isEnum()) {
        String invalidValue = formatException.getValue().toString();
        String errorMessage = String.format("Invalid value '%s' for enum '%s'", invalidValue, formatException.getTargetType().getSimpleName());
        return buildResponseEntity(request, HttpStatus.BAD_REQUEST, errorMessage);
      }
    }

    return buildResponseEntity(request, HttpStatus.BAD_REQUEST, INVALID_INPUT_DATA.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleNotFoundException(HttpServletRequest request,NotFoundException exception) {
    return buildResponseEntity(
        request, HttpStatus.NOT_FOUND, exception.getMessage());
  }

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

    return buildResponseEntity(
        request, (HttpStatus) exception.getStatusCode(), exception.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponseDTO> handleNotFoundException(HttpServletRequest request,
      UnauthorizedException exception) {
    return buildResponseEntity(
        request, HttpStatus.UNAUTHORIZED, exception.getMessage());
  }

//  @ExceptionHandler(AccessDeniedException.class)
//  public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(HttpServletRequest request,
//      AccessDeniedException exception) {
//    ForbiddenException forbiddenException = new ForbiddenException(exception.getMessage());
//    return handleBaseException(request, forbiddenException);
//  }


  /**
   * Handles AuthenticationException, an exception thrown when jwt token is not valid or has expired
   * or there is no token in the header.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception AuthenticationException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponseDTO> handleAuthException(HttpServletRequest request,
      Exception exception) {
    String message = "";
    if (request.getAttribute("message") != null) {
      message = (String) request.getAttribute("message");
    }

    String responseMessage = switch (message) {
      case ("expired") -> TOKEN_HAS_EXPIRED.getMessage();
      case ("wrongToken") -> TOKEN_NOT_VALID.getMessage();
      default -> UNAUTHORIZED.getMessage();
    };

    return buildResponseEntity(request, UNAUTHORIZED.getHttpStatus(), responseMessage);
  }

  /**
   * Handles MethodArgumentNotValidException, an exception thrown when method argument validation
   * fails.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception MethodArgumentNotValidException object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
      HttpServletRequest request, MethodArgumentNotValidException exception) {

    return buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), VERIFY_PASSWORD_MATCHING.getMessage());
  }

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

    return buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), INVALID_INPUT_DATA.getMessage());
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

    return buildResponseEntity(request, BAD_REQUEST.getHttpStatus(), INVALID_ARGUMENT_TYPE.getMessage());
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponseDTO> handleNotFoundException(HttpServletRequest request,ForbiddenException exception) {
    return buildResponseEntity(
        request, HttpStatus.FORBIDDEN, exception.getMessage());
  }

  /**
   * Handles general exceptions that are not specifically handled by other methods.
   *
   * @param request   HttpServletRequest object representing the HTTP request
   * @param exception Object representing the thrown exception
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponseDTO> handleInternalServerError(
      HttpServletRequest request, Throwable exception) {

    return buildResponseEntity(request, INTERNAL_SERVER_ERROR.getHttpStatus(),
        INTERNAL_SERVER_ERROR.getMessage());
  }

  /**
   * Builds a ResponseEntity object with the provided error details.
   *
   * @param request    HttpServletRequest object representing the HTTP request
   * @param statusCode HttpStatusCode representing the statusCode of exception
   * @param message    String containing the error message
   * @return ResponseEntity containing ErrorResponseDTO with appropriate error details
   */

  private ResponseEntity<ErrorResponseDTO> buildResponseEntity(
      HttpServletRequest request, HttpStatus statusCode, String message) {

    String uri = request.getServletPath();

    var response = ErrorResponseDTO.builder()
        .uri(uri)
        .type(statusCode.toString())
        .message(message)
        .timestamp(LocalDateTime.now())
        .build();
    return ResponseEntity
        .status(statusCode)
        .contentType(MediaType.APPLICATION_JSON)
        .body(response);
  }
}
