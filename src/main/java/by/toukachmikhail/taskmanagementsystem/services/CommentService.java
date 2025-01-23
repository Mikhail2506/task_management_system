package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

  CommentDto createComment(Long taskId, Long userId, String text);

  void deleteComment(Long commentId);

  Page<CommentDto> getCommentsByTask(Long taskId, int page, int size, String sortBy,
      String direction);

  CommentDto getCommentById(Long commentId);
}
