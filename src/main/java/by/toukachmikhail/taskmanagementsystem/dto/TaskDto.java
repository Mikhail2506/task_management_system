package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import by.toukachmikhail.taskmanagementsystem.validators.taskspriority.ValidTaskPriority;
import by.toukachmikhail.taskmanagementsystem.validators.taskstatus.ValidTaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import lombok.Builder;

@Builder
public record TaskDto(

    @NotBlank
    String header,

    @NotBlank
    String description,

    @NotNull(message = "Task status cannot be null")
    @ValidTaskStatus
    TaskStatus status,

    @NotNull(message = "Task priority cannot be null")
    @ValidTaskPriority
    TaskPriority priority,

    UserDto assignee,
//
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    CommentDto comment,

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<CommentDto> comments

) {

  public TaskDto {
    if (comments == null) {
      comments = Collections.emptyList(); // или new ArrayList<>()
    }
  }

}
