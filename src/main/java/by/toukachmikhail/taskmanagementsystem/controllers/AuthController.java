package by.toukachmikhail.taskmanagementsystem.controllers;

import by.toukachmikhail.taskmanagementsystem.dto.ErrorResponseDTO;
import by.toukachmikhail.taskmanagementsystem.dto.JwtRequestDto;
import by.toukachmikhail.taskmanagementsystem.dto.JwtResponseDto;
import by.toukachmikhail.taskmanagementsystem.dto.RegistrationUserDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication controller",
    description = "Controller for user authentication and registration")
public interface AuthController {

  @Operation(
      summary = "Authenticate user and generate JWT token",
      description = "Authenticates a user by email and password, and returns a JWT token if successful."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Authentication successful, JWT token returned",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JwtResponseDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "401",
              description = "Unauthorized - Invalid email or password",
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
  @PostMapping("/auth")
  ResponseEntity<JwtResponseDto> createAuthToken(
      @Parameter(description = "User credentials for authentication", required = true)
      @RequestBody JwtRequestDto authRequest
  );


  @Operation(
      summary = "Register a new user",
      description = "Registers a new user with the provided details."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201",
              description = "User registered successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class))),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request - Invalid input data",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponseDTO.class))),
          @ApiResponse(
              responseCode = "403",
              description = "Forbidden - User with this email already exists",
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
  @PostMapping("/register")
  ResponseEntity<UserDto> registerNewUser(
      @Parameter(description = "User registration details", required = true)
      @Valid @RequestBody RegistrationUserDto registrationUserDto
  );
}
