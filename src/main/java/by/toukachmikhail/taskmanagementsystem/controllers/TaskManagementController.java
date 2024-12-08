package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Task management controller",
    description = "Controller to create, show, update and delete tasks")
public interface TaskManagementController {

  @Operation(
      summary = "Created (/open) broker account",
      description = "<a href=\"https://wiki.astondevs.ru/pages/viewpage.action?pageId=52025111\">"
          + "INV-3.15 Создать номера брокерского и депозитарного счетов</a>")
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
  @GetMapping()
  ResponseEntity<Page<TaskDto>> getAllTasks(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "taskId") String sortBy,
      @RequestParam(defaultValue = "asc") String direction);


  @Operation(
      summary = "Created (/open) broker account",
      description =
          "<a href=\"https://wiki.astondevs.ru/pages/viewpage.action?pageId=52025111\">"
              + "INV-3.15 Создать номера брокерского и депозитарного счетов</a>")
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
  @GetMapping("/{task_id}")
  ResponseEntity<TaskDto> getTaskById(@PathVariable("task_id") Long taskId);


  @Operation(
      summary = "Created (/open) broker account",
      description =
          "<a href=\"https://wiki.astondevs.ru/pages/viewpage.action?pageId=52025111\">"
              + "INV-3.15 Создать номера брокерского и депозитарного счетов</a>")
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
  ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto);


  @Operation(
      summary = "Created (/open) broker account",
      description =
          "<a href=\"https://wiki.astondevs.ru/pages/viewpage.action?pageId=52025111\">"
              + "INV-3.15 Создать номера брокерского и депозитарного счетов</a>")
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
  @PutMapping("/{taskId}")
  ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId,
      @RequestBody TaskDto taskDto);


  @Operation(
      summary = "Created (/open) broker account",
      description =
          "<a href=\"https://wiki.astondevs.ru/pages/viewpage.action?pageId=52025111\">"
              + "INV-3.15 Создать номера брокерского и депозитарного счетов</a>")
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
  @DeleteMapping("/{taskId}")
  ResponseEntity<Void> deleteTask(@PathVariable Long taskId);
}
