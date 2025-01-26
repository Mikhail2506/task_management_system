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
import org.junit.jupiter.api.BeforeEach;
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

//@ExtendWith(MockitoExtension.class)
//public class TaskControllerTest {
//
//  @Mock
//  private TaskService taskService;
//
//  @Mock
//  private UserDetailsServiceImpl userDetailsService;
//
//  @InjectMocks
//  private TaskControllerImpl taskController;
//
//  private MockMvc mockMvc;
//  private ObjectMapper objectMapper;
//  private UserDto userDto;
//  private CommentDto commentDto;
//
//  @BeforeEach
//  void setUp() {
//    mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
//    objectMapper = new ObjectMapper();
//    userDto = new UserDto("Mikl", "mikl@mail.ru", UserRole.USER);
//    commentDto = new CommentDto("Text");
//  }
//
//  @Test
//  void getAllTasksShouldReturnAllTasks() throws Exception {
//
//    TaskDto taskDto = new TaskDto(
//        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, userDto, null, null
//    );
//    when(taskService.createTask(taskDto)).thenReturn(taskDto);
//
//    mockMvc.perform(post("/api/v1/tasks")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(taskDto)))
//        .andExpect(status().isCreated())
//        .andExpect(jsonPath("$.header").value("Header"))
//        .andExpect(jsonPath("$.description").value("Description"));
//
//    verify(taskService).createTask(taskDto);
//  }
//
//  @Test
//  void getTaskByIdShouldReturnTask() throws Exception {
//
//    TaskDto taskDto = new TaskDto(
//        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, null, null, null
//    );
//    when(taskService.getTaskById(1L)).thenReturn(taskDto);
//
//    mockMvc.perform(get("/api/v1/tasks/1")
//            .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.header").value("Header"))
//        .andExpect(jsonPath("$.description").value("Description"));
//
//    verify(taskService).getTaskById(1L);
//  }
//
//  @Test
//  void createTaskShouldReturnCreatedTask() throws Exception {
//
//    TaskDto taskDto = new TaskDto(
//        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, null, null, null
//    );
//    when(taskService.createTask(taskDto)).thenReturn(taskDto);
//
//    mockMvc.perform(post("/api/v1/tasks")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(taskDto)))
//        .andExpect(status().isCreated())
//        .andExpect(jsonPath("$.header").value("Header"))
//        .andExpect(jsonPath("$.description").value("Description"));
//
//    verify(taskService).createTask(taskDto);
//  }
//
//  @Test
//  void updateTaskShouldReturnUpdatedTask() throws Exception {
//
//    User currentUser = new User();
//    currentUser.setUsername("Kirill");
//    currentUser.setEmail("kirill@mail.ru");
//
//    currentUser.setRole(UserRole.USER);
//    UserDto currentUserDto = new UserDto("Kirill","kirill@mail.ru",UserRole.USER);
//
//    when(userDetailsService.getCurrentUser()).thenReturn(currentUser);
//    TaskDto taskDto = new TaskDto(
//        "Updated Header", "Updated Description", TaskStatus.FINISHED, TaskPriority.HIGH,
//        currentUserDto, null, null
//    );
//
//    when(taskService.updateTask(
//        eq(1L),
//        eq(taskDto),
//        eq(currentUser)
//    )).thenReturn(taskDto);
//
//    mockMvc.perform(put("/api/v1/tasks/1")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(taskDto)))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.header").value("Updated Header"))
//        .andExpect(jsonPath("$.description").value("Updated Description"));
//
//    verify(taskService).updateTask(
//        eq(1L),
//        eq(taskDto),
//        eq(currentUser)
//    );
//  }
//
//  @Test
//  void deleteTaskShouldReturnOk() throws Exception {
//
//    doNothing().when(taskService).deleteTask(1L);
//
//    mockMvc.perform(delete("/api/v1/tasks/1")
//            .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk());
//
//    verify(taskService).deleteTask(1L);
//  }

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
  void getAllTasksShouldReturnAllTasks() throws Exception {
    // Arrange
    UserDto assigneeDto = new UserDto("Mikl", "mikl@mail.ru", UserRole.USER);
    CommentDto commentDto = new CommentDto("Комментарий 1");

    TaskDto taskDto = new TaskDto(
        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH,
        assigneeDto, null, Collections.singletonList(commentDto) // Добавляем комментарий
    );

    Page<TaskDto> taskPage = new PageImpl<>(Collections.singletonList(taskDto));

    when(taskService.getAllTasks(0, 10, "id", "asc")).thenReturn(taskPage);

    // Act & Assert
    mockMvc.perform(get("/api/v1/tasks")
            .param("page", "0")
            .param("size", "10")
            .param("sortBy", "id")
            .param("direction", "asc")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].header").value("Header"))
        .andExpect(jsonPath("$.content[0].description").value("Description"))
        .andExpect(jsonPath("$.content[0].status").value("FINISHED"))
        .andExpect(jsonPath("$.content[0].priority").value("HIGH"))
        .andExpect(jsonPath("$.content[0].assignee.username").value("Mikl"))
        .andExpect(jsonPath("$.content[0].assignee.email").value("mikl@mail.ru"))
        .andExpect(jsonPath("$.content[0].assignee.role").value("USER"))
        .andExpect(jsonPath("$.content[0].comments[0].text").value("Комментарий 1")); // Проверяем комментарий

    // Verify
    verify(taskService).getAllTasks(0, 10, "id", "asc");
  }

  @Test
  void getTaskByIdShouldReturnTask() throws Exception {

    TaskDto taskDto = new TaskDto(
        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, userDto, null, null
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
        "Header", "Description", TaskStatus.FINISHED, TaskPriority.HIGH, userDto, commentDto, null
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
        .andExpect(jsonPath("$.assignee.role").value("USER"))
        .andExpect(jsonPath("$.comment.text").value("Text"));


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
        currentUserDto, commentDto, null
    );

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
        .andExpect(jsonPath("$.assignee.role").value("USER"))
        .andExpect(jsonPath("$.comment.text").value("Text"));

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


