package by.toukachmikhail.taskmanagementsystem.exception_handling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {
  BAD_REQUEST(HttpStatus.BAD_REQUEST,
      "Некорректный запрос. Убедитесь, что адрес указан верно и попробуйте еще раз."),
  FORBIDDEN(HttpStatus.FORBIDDEN,
      "Запрещено. Отсутствуют права доступа к содержимому."),
  NOT_FOUND(HttpStatus.NOT_FOUND,
      "Страница не найдена"),
  REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT,
      "Время ожидания запроса истекло."),
  UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
      "Формат запрашиваемых данных не поддерживается сервером, поэтому запрос отклонён."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
      "Внутренняя ошибка сервера. Подождите несколько минут и попробуйте снова."),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,
      "Сервер временно недоступен по техническим причинам. Попробуйте позже."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED,
      "Ошибка авторизации. Для доступа к запрашиваемому ресурсу требуется аутентификация.");
  private final HttpStatus httpStatus;
  private final String message;

  @Override
  public String toString() {
    return httpStatus.value() + " " + name().replace("_", " ");
  }
}
