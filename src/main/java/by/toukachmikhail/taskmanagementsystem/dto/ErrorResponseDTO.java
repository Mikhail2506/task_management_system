package by.toukachmikhail.taskmanagementsystem.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDTO(
    String uri,
    String type,
    String message,
    Long timestamp

) {

  @Override
  public String toString() {
    return "type = '" + type + "', message = '" + message + "'";
  }
}
