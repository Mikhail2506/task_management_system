package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "User controller",
    description = "Controller to create, get, update and delete users")
public interface UserController {

  @Operation(
      summary = "Get all users",
      description = "Returns a paginated list of all users."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "An application to show all users has succeeded",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Page.class))),
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
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @GetMapping("")
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<Page<UserDto>> getAllUsers(
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
      summary = "Get user by id",
      description = "Returns a user by their unique identifier."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User found and returned successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class))),
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
              description = "User not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @GetMapping("/{user_id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<UserDto> getUserById(
      @Parameter(description = "Unique identifier of the user", example = "1")
      @PathVariable("user_id") Long userId
  );

  @Operation(
      summary = "Update user",
      description = "Updates an existing user by their unique identifier."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User updated successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class))), // Исправлено на UserDto
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
              description = "User not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @PutMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<UserDto> updateUser(
      @Parameter(description = "Unique identifier of the user", example = "1")
      @PathVariable Long userId,

      @Parameter(description = "User data to update", required = true)
      @RequestBody UserDto userDto
  );

  @Operation(
      summary = "Delete user",
      description = "Deletes a user by their unique identifier."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "204",
              description = "User deleted successfully",
              content = @Content(schema = @Schema(hidden = true))), // Пустой ответ
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
              description = "User not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class)))
      }
  )
  @DeleteMapping("/{userId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  ResponseEntity<Void> deleteUser(
      @Parameter(description = "Unique identifier of the user", example = "1")
      @PathVariable Long userId
  );
}
