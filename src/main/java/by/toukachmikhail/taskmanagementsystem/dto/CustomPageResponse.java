package by.toukachmikhail.taskmanagementsystem.dto;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record CustomPageResponse<T>(
    List<T> content,
    int currentPage,
    int totalPages,
    long totalElements
) {
  public CustomPageResponse(Page<T> page) {
    this(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
  }
}
