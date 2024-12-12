package by.toukachmikhail.taskmanagementsystem.dto;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import by.toukachmikhail.taskmanagementsystem.validators.digits.ValidIsDigit;
import by.toukachmikhail.taskmanagementsystem.validators.taskspriority.ValidTaskPriority;
import by.toukachmikhail.taskmanagementsystem.validators.taskstatus.ValidTaskStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record TaskDto(

//    @ValidIsDigit
//    Long id,

    String header,

    String description,

    @NotNull(message = "Task status cannot be null")
    @ValidTaskStatus
    TaskStatus status,

    @NotNull(message = "Task priority cannot be null")
    @ValidTaskPriority
    TaskPriority priority,

    UserDto author,
    UserDto assignee,

    List<CommentDto> comments
) {

}
