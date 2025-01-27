package by.toukachmikhail.taskmanagementsystem.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.toukachmikhail.taskmanagementsystem.controllers.impl.TaskControllerImpl;
import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.services.TaskService;
import by.toukachmikhail.taskmanagementsystem.services.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

  @Mock
  private TaskService taskService;

  @Mock
  private UserDetailsServiceImpl userDetailsService;

  @InjectMocks
  private TaskControllerImpl taskController;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private UserDto userDto;
  private CommentDto commentDto;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    objectMapper = new ObjectMapper();
    userDto = new UserDto("Mikl", "mikl@mail.ru", UserRole.USER);
    commentDto = new CommentDto("Text");
  }

  @Test
  @Disabled
  void getAllTasks_ShouldReturnPageOfTasks() throws Exception {

    TaskDto taskDto = TaskDto.builder()
        .header("Header")
        .description("Description")
        .status(TaskStatus.FINISHED)
        .priority(TaskPriority.HIGH)
        .assignee(new UserDto("Mikl", "mikl@mail.ru", UserRole.USER))
        .comments(List.of(commentDto))
        .build();

    Page<TaskDto> taskPage = new PageImpl<>(Collections.singletonList(taskDto));

    System.out.println("Mocking taskService to return: " + taskPage);

    when(taskService.getAllTasks(any(Integer.class), any(Integer.class), any(String.class), any(String.class)))
        .thenReturn(taskPage);

    System.out.println("Performing GET request to /api/v1/tasks...");

    mockMvc.perform(get("/api/v1/tasks")
            .param("page", "0")
            .param("size", "10")
            .param("sortBy", "id")
            .param("direction", "asc")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content[0].header").value("Header"))
        .andExpect(jsonPath("$.content[0].description").value("Description"))
        .andExpect(jsonPath("$.content[0].status").value("FINISHED"))
        .andExpect(jsonPath("$.content[0].priority").value("HIGH"))
        .andExpect(jsonPath("$.content[0].assignee.username").value("Mikl"))
        .andDo(result -> {
          System.out.println("Request result: " + result.getResponse().getContentAsString());
        });
    System.out.println("GET request to /api/v1/tasks completed.");

    verify(taskService).getAllTasks(0, 10, "id", "asc");
  }

  @Test
  void getTaskByIdShouldReturnTask() throws Exception {

    TaskDto taskDto = new TaskDto(
        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, userDto, null
    );
    when(taskService.getTaskById(1L)).thenReturn(taskDto);

    mockMvc.perform(get("/api/v1/tasks/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.header").value("Header"))
        .andExpect(jsonPath("$.description").value("Description"))
        .andExpect(jsonPath("$.status").value("FINISHED"))
        .andExpect(jsonPath("$.priority").value("HIGH"))
        .andExpect(jsonPath("$.assignee.username").value("Mikl"))
        .andExpect(jsonPath("$.assignee.email").value("mikl@mail.ru"))
        .andExpect(jsonPath("$.assignee.role").value("USER"));

    verify(taskService).getTaskById(1L);
  }

  @Test
  void createTaskShouldReturnCreatedTask() throws Exception {

    TaskDto taskDto = new TaskDto(
        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, userDto, List.of(commentDto)
    );
    when(taskService.createTask(taskDto)).thenReturn(taskDto);

    mockMvc.perform(post("/api/v1/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(taskDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.header").value("Header"))
        .andExpect(jsonPath("$.description").value("Description"))
        .andExpect(jsonPath("$.status").value("FINISHED"))
        .andExpect(jsonPath("$.priority").value("HIGH"))
        .andExpect(jsonPath("$.assignee.username").value("Mikl"))
        .andExpect(jsonPath("$.assignee.email").value("mikl@mail.ru"))
        .andExpect(jsonPath("$.assignee.role").value("USER"));


    verify(taskService).createTask(taskDto);
  }

  @Test
  void updateTaskShouldReturnUpdatedTask() throws Exception {

    User currentUser = new User();
    currentUser.setUsername("Kirill");
    currentUser.setEmail("kirill@mail.ru");
    currentUser.setRole(UserRole.USER);

    UserDto currentUserDto = new UserDto("Kirill", "kirill@mail.ru", UserRole.USER);

    when(userDetailsService.getCurrentUser()).thenReturn(currentUser);

    TaskDto taskDto = new TaskDto(
        "Updated Header", "Updated Description", TaskStatus.FINISHED, TaskPriority.HIGH,
        currentUserDto,List.of(commentDto));

    when(taskService.updateTask(
        eq(1L),
        any(TaskDto.class),
        eq(currentUser)
    )).thenReturn(taskDto);

    mockMvc.perform(put("/api/v1/tasks/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(taskDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.header").value("Updated Header"))
        .andExpect(jsonPath("$.description").value("Updated Description"))
        .andExpect(jsonPath("$.status").value("FINISHED"))
        .andExpect(jsonPath("$.priority").value("HIGH"))
        .andExpect(jsonPath("$.assignee.username").value("Kirill"))
        .andExpect(jsonPath("$.assignee.email").value("kirill@mail.ru"))
        .andExpect(jsonPath("$.assignee.role").value("USER"));

    verify(taskService).updateTask(
        eq(1L),
        any(TaskDto.class),
        eq(currentUser)
    );
  }

  @Test
  void deleteTaskShouldReturnOk() throws Exception {

    doNothing().when(taskService).deleteTask(1L);

    mockMvc.perform(delete("/api/v1/tasks/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(taskService).deleteTask(1L);
  }
}


