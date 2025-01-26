package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Task management controller",
    description = "Controller to create, get, update and delete tasks")
public interface TaskController {

  @Operation(
      summary = "Get all tasks",
      description = "Returns a paginated list of all tasks."
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Tasks retrieved successfully",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Page.class))
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have ADMIN role",
          content = @Content(schema = @Schema(hidden = true))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<Page<TaskDto>> getAllTasks(
      @Parameter(description = "Page number (starting from 0)", example = "0")
      @RequestParam(defaultValue = "0") int page,

      @Parameter(description = "Number of items per page", example = "10")
      @RequestParam(defaultValue = "10") int size,

      @Parameter(description = "Sorting field", example = "id")
      @RequestParam(defaultValue = "id") String sortBy,

      @Parameter(description = "Sorting direction (asc or desc)", example = "asc")
      @RequestParam(defaultValue = "asc") String direction
  );

  @Operation(
      summary = "Get task by ID",
      description = "Returns a task by its unique identifier."
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Task retrieved successfully",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = TaskDto.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Task not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @GetMapping("/{task_id}")
  ResponseEntity<TaskDto> getTaskById(
      @Parameter(description = "Unique identifier of the task", example = "1")
      @PathVariable("task_id") Long taskId
  );

  @Operation(
      summary = "Create a new task",
      description = "Creates a new task with the provided details."
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "Task created successfully",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = TaskDto.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad Request - Invalid input data",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have ADMIN role",
          content = @Content(schema = @Schema(hidden = true))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Assignee not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<TaskDto> createTask(
      @Parameter(description = "Task details to create", required = true)
      @Valid @RequestBody TaskDto taskDto
  ) throws NotFoundException;

  @Operation(
      summary = "Update an existing task",
      description = "Updates an existing task by its unique identifier."
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Task updated successfully",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = TaskDto.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad Request - Invalid input data",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have required permissions",
          content = @Content(schema = @Schema(hidden = true))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Task or assignee not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @PutMapping("/{taskId}")
  @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
  ResponseEntity<TaskDto> updateTask(
      @Parameter(description = "Unique identifier of the task", example = "1")
      @PathVariable Long taskId,

      @Parameter(description = "Updated task details", required = true)
      @RequestBody TaskDto taskDto
  );

  @Operation(
      summary = "Delete a task",
      description = "Deletes a task by its unique identifier."
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "Task deleted successfully",
          content = @Content(schema = @Schema(hidden = true))
      ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden - User does not have ADMIN role",
          content = @Content(schema = @Schema(hidden = true))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Task not found",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @DeleteMapping("/{taskId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<Void> deleteTask(
      @Parameter(description = "Unique identifier of the task", example = "1")
      @PathVariable Long taskId
  );
}
