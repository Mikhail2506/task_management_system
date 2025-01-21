package by.toukachmikhail.taskmanagementsystem.controllers.impl;

import by.toukachmikhail.taskmanagementsystem.controllers.CommentController;
import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentControllerImpl implements CommentController {

  private final CommentService commentService;

  @Override
  @PostMapping
  public ResponseEntity<CommentDto> addComment(@RequestParam Long taskId, @RequestParam Long userId,
      @RequestBody String text){
    CommentDto commentDTO = commentService.createComment(taskId, userId, text);
    return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
  }

  @Override
  @PutMapping("/{commentId}")
  public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId,
      @RequestBody String text)
      throws NotFoundException {
    CommentDto updatedCommentDTO = commentService.updateComment(commentId, text);
    return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
  }

//  @Override
//  @DeleteMapping("/{commentId}")
//  public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
//    commentService.deleteComment(commentId);
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }

//  @Override
//  @GetMapping("/task/{taskId}")
//  public ResponseEntity<Page<CommentDto>> getCommentsByTask(@PathVariable Long taskId,
//      @RequestParam(defaultValue = "0") int page,
//      @RequestParam(defaultValue = "10") int size,
//      @RequestParam(defaultValue = "id") String sortBy,
//      @RequestParam(defaultValue = "asc") String direction) {
//    Page<CommentDto> commentDTOs = commentService.getCommentsByTask(taskId, page, size, sortBy, direction);
//    return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
//  }

//  @Override
//  @GetMapping("/{commentId}")
//  public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId){
//    CommentDto commentDTO = commentService.getCommentById(commentId);
//    return new ResponseEntity<>(commentDTO, HttpStatus.OK);
//  }
}
