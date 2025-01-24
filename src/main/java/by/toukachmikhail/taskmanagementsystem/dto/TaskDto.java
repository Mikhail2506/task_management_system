package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import by.toukachmikhail.taskmanagementsystem.validators.taskspriority.ValidTaskPriority;
import by.toukachmikhail.taskmanagementsystem.validators.taskstatus.ValidTaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    UserDto author,

    UserDto assignee,

    CommentDto comment,

    @JsonIgnore
    List<CommentDto> comments
) {

}
