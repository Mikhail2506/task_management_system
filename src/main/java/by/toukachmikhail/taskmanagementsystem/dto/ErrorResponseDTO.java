package by.toukachmikhail.taskmanagementsystem.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ErrorResponseDTO(
    String uri,
    String type,
    String message,
    LocalDateTime timestamp
) {

  @Override
  public String toString() {
    return "type = '" + type + "', message = '" + message + "'";
  }
}
