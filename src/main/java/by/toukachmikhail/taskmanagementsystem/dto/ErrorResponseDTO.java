package by.toukachmikhail.taskmanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ErrorResponseDTO(
    String uri,
    String type,
    String message,
    List<String> details,
    LocalDateTime timestamp

) {

  @Override
  public String toString() {
    return "type = '" + type + "', message = '" + message + "'";
  }
}
