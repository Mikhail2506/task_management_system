package by.toukachmikhail.taskmanagementsystem.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
