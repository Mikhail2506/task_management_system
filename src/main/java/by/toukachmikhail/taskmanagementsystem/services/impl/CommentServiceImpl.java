package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.COMMENT_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.CommentMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.CommentRepository;
import by.toukachmikhail.taskmanagementsystem.services.CommentService;
import by.toukachmikhail.taskmanagementsystem.services.TaskService;
import by.toukachmikhail.taskmanagementsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserService userService;
  private final TaskService taskService;
  private final TaskMapper taskMapper;
  private final UserMapper userMapper;
  private final CommentMapper commentMapper;

  @Override
  public CommentDto createComment(Long taskId, Long userId, String text) {
    TaskDto taskDto = taskService.getTaskById(taskId);
    UserDto userDto = userService.getUserById(userId);

    try {
      Comment comment = new Comment();
      comment.setText(text);
      comment.setTask(taskMapper.dtoToEntity(taskDto));
      comment.setUser(userMapper.dtoToEntity(userDto));

      return commentMapper.entityToDTO(commentRepository.save(comment));
    } catch (RuntimeException e) {
      throw new NotFoundException(e.getMessage());
    }
  }

  @Override
  public CommentDto updateComment(Long commentId, String text) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND.getMessage()));
    try {
      comment.setText(text);
      return commentMapper.entityToDTO(commentRepository.save(comment));
    } catch (RuntimeException e) {
      throw new NotFoundException(COMMENT_NOT_FOUND.getMessage());
    }
  }

  @Override
  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }

  @Override
  public Page<CommentDto> getCommentsByTask(Long taskId, int page, int size, String sortBy,
      String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Comment> comments = commentRepository.findByTaskId(taskId, pageable);
    return comments.map(commentMapper::entityToDTO);
  }

  @Override
  public CommentDto getCommentById(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND.getMessage()));

    try {
      return commentMapper.entityToDTO(comment);
    } catch (RuntimeException e) {
      throw new NotFoundException(COMMENT_NOT_FOUND.getMessage());
    }
  }
}
