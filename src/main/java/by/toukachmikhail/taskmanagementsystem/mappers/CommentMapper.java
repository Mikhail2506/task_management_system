package by.toukachmikhail.taskmanagementsystem.mappers;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

  private final UserMapper userMapper;

  public CommentDto entityToDTO(Comment comment) {
    return CommentDto.builder()
        //.id(comment.getId())
        .text(comment.getText())
        .build();
  }

  public Comment dtoToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
   // comment.setId(commentDto.id());
    comment.setText(commentDto.text());
    return comment;
  }
}
