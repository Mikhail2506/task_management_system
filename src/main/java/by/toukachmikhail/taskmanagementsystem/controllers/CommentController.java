package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Comment controller",
    description = "Controller to create, show, update and delete task comments")
public interface CommentController {

  @Operation(
      summary = "Created (/open) broker account")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "An application to open a broker account has successfully created",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TaskDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not Found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "408",
              description = "Request Timeout",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "429",
              description = "Too Many Requests",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "503",
              description = "Service Unavailable",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @PostMapping
 // @PreAuthorize("hasRole('ADMIN') or @taskService.getTaskById(#taskId).assignee.id == authentication.principal.id")
  ResponseEntity<CommentDto> addComment(@RequestParam Long taskId, @RequestParam Long userId,
      @RequestBody String text)
      throws NotFoundException;

  @Operation(
      summary = "Created (/open) broker account")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "An application to open a broker account has successfully created",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TaskDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not Found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "408",
              description = "Request Timeout",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "429",
              description = "Too Many Requests",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "503",
              description = "Service Unavailable",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @PutMapping("/{commentId}")
  //@PreAuthorize("hasRole('ADMIN') or @commentService.getCommentById(#commentId).user.id == authentication.principal.id")
  ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody String text)
      throws NotFoundException;

  @Operation(
      summary = "Created (/open) broker account")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "An application to open a broker account has successfully created",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TaskDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not Found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "408",
              description = "Request Timeout",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "429",
              description = "Too Many Requests",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "503",
              description = "Service Unavailable",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @DeleteMapping("/{commentId}")
  //@PreAuthorize("hasRole('ADMIN') or @commentService.getCommentById(#commentId).user.id == authentication.principal.id")
  ResponseEntity<Void> deleteComment(@PathVariable Long commentId);

  @Operation(
      summary = "Created (/open) broker account")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "An application to open a broker account has successfully created",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TaskDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not Found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "408",
              description = "Request Timeout",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "429",
              description = "Too Many Requests",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "503",
              description = "Service Unavailable",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @GetMapping("/task/{taskId}")
 // @PreAuthorize("hasRole('ADMIN') or @taskService.getTaskById(#taskId).assignee.id == authentication.principal.id")
  ResponseEntity<Page<CommentDto>> getCommentsByTask(@PathVariable Long taskId,@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction);

  @Operation(
      summary = "Created (/open) broker account")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "An application to open a broker account has successfully created",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = TaskDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "404",
              description = "Not Found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "408",
              description = "Request Timeout",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "429",
              description = "Too Many Requests",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "503",
              description = "Service Unavailable",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @GetMapping("/{commentId}")
  //@PreAuthorize("hasRole('ADMIN') or @commentService.getCommentById(#commentId).user.id == authentication.principal.id")
  ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId)
      throws NotFoundException;
}
