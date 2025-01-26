package by.toukachmikhail.taskmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.dto.UserDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.CommentMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.CommentRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.impl.TaskServiceImpl;
import by.toukachmikhail.taskmanagementsystem.services.impl.UserDetailsServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private TaskMapper taskMapper;

  @Mock
  private CommentMapper commentMapper;

  @Mock
  private UserDetailsServiceImpl userDetailsService;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private TaskServiceImpl taskService;

  private User user;
  private User admin;
  private Task task;
  private TaskDto taskDto;
  private Comment comment;
  private CommentDto commentDto;
  private UserDto assigneeDto;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1L);
    user.setUsername("user");
    user.setRole(UserRole.USER);

    admin = new User();
    admin.setId(2L);
    admin.setUsername("admin");
    admin.setRole(UserRole.ADMIN);

    task = new Task();
    task.setId(1L);
    task.setHeader("Task Header");
    task.setDescription("Task Description");
    task.setStatus(TaskStatus.FINISHED);
    task.setTaskPriority(TaskPriority.HIGH);
    task.setAuthor(admin);
    task.setAssignee(user);

    comment = new Comment();
    comment.setId(1L);
    comment.setText("Test Comment");
    comment.setUser(user);
    comment.setTask(task);

    commentDto = new CommentDto("Test Comment");

    taskDto = TaskDto.builder()
        .header("Task Header")
        .description("Task Description")
        .status(TaskStatus.FINISHED)
        .priority(TaskPriority.HIGH)
        .assignee(userMapper.entityToDto(user))
        .comments(List.of(commentDto))
        .build();

     assigneeDto = UserDto.builder()
        .email("assignee@example.com")
        .username("assignee")
        .build();
  }

  @Test
  void getAllTasksTestForUserRole() {

    when(userDetailsService.getCurrentUser()).thenReturn(user);
    when(taskRepository.findByAssignee(user, PageRequest.of(0, 10, Sort.by("id"))))
        .thenReturn(new PageImpl<>(List.of(task)));
    when(taskMapper.entityToDto(task)).thenReturn(taskDto);

    Page<TaskDto> result = taskService.getAllTasks(0, 10, "id", "asc");

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    verify(taskRepository, times(1)).findByAssignee(user, PageRequest.of(0, 10, Sort.by("id")));
  }

  @Test
  void getAllTasksTestForAdminRole() {

    when(userDetailsService.getCurrentUser()).thenReturn(admin);
    when(taskRepository.findByAuthor(admin, PageRequest.of(0, 10, Sort.by("id"))))
        .thenReturn(new PageImpl<>(List.of(task)));
    when(taskMapper.entityToDto(task)).thenReturn(taskDto);

    Page<TaskDto> result = taskService.getAllTasks(0, 10, "id", "asc");

    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    verify(taskRepository, times(1)).findByAuthor(admin, PageRequest.of(0, 10, Sort.by("id")));
  }

  @Test
  void getTaskByIdWhenWhenTaskExists() {

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(taskMapper.entityToDto(task)).thenReturn(taskDto);

    TaskDto result = taskService.getTaskById(1L);

    assertNotNull(result);
    assertEquals(taskDto.header(), result.header());
    verify(taskRepository, times(1)).findById(1L);
  }

  @Test
  void getTaskByIdTestWhenTaskNotFoundThrowsNotFoundException() {

    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> taskService.getTaskById(1L));
    verify(taskRepository, times(1)).findById(1L);
  }

  @Test
  void createTaskTestWhenValidTaskDto() {

    TaskDto taskDto = TaskDto.builder()
        .header("Task Header")
        .description("Task Description")
        .status(TaskStatus.PROCESSING)
        .priority(TaskPriority.HIGH)
        .assignee(assigneeDto)
        .comments(Collections.emptyList())
        .build();

    when(userDetailsService.getCurrentUser()).thenReturn(admin);
    when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
    when(taskRepository.save(any())).thenReturn(task);
    when(taskMapper.entityToDto(task)).thenReturn(taskDto);

    TaskDto result = taskService.createTask(taskDto);

    assertNotNull(result);
    assertEquals(taskDto.header(), result.header());
    verify(taskRepository, times(1)).save(any());
  }

  @Test
  void updateTaskTestWhenUserRoleUpdatesStatusOnly() {

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(taskRepository.save(task)).thenReturn(task);

    TaskDto updateDto = TaskDto.builder()
        .status(TaskStatus.PROCESSING)
        .build();

    TaskDto result = taskService.updateTask(1L, updateDto, user);

    assertNotNull(result);
    assertEquals(TaskStatus.PROCESSING, result.status());
    verify(taskRepository, times(1)).save(task);
  }

  @Test
  void updateTaskWhenAdminRoleUpdatesAllFields() {

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(taskRepository.save(task)).thenReturn(task);

    CommentDto commentDto = CommentDto.builder()
        .text("Test comment")
        .build();

    Comment comment = new Comment();
    comment.setText(commentDto.text());
    comment.setTask(task);

    when(commentMapper.dtoToEntity(commentDto)).thenReturn(comment);

    TaskDto updateDto = TaskDto.builder()
        .header("Updated Header")
        .description("Updated Description")
        .status(TaskStatus.FINISHED)
        .priority(TaskPriority.HIGH)
        .assignee(userMapper.entityToDto(user))
        .comments(List.of(commentDto)) // Use the initialized commentDto
        .build();

    TaskDto result = taskService.updateTask(1L, updateDto, admin);

    assertNotNull(result);
    assertEquals("Updated Header", result.header());
    verify(taskRepository, times(1)).save(task);
  }

  @Test
  void deleteTaskTestWhenTaskExists() {

    when(taskRepository.existsById(1L)).thenReturn(true);

    taskService.deleteTask(1L);

    verify(taskRepository, times(1)).deleteById(1L);
  }

  @Test
  void deleteTaskTestWhenTaskNotFoundThrowsNotFoundException() {

    when(taskRepository.existsById(1L)).thenReturn(false);

    assertThrows(NotFoundException.class, () -> taskService.deleteTask(1L));
    verify(taskRepository, times(1)).existsById(1L);
  }

  @Test
  void isAssigneeTestWhenTaskAndUserMatchReturnsTrue() {

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    boolean result = taskService.isAssignee(1L, "user");

    assertTrue(result);
    verify(taskRepository, times(1)).findById(1L);
  }

  @Test
  void isAssigneeTestWhenTaskAndUserDoNotMatchReturnsFalse() {

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    boolean result = taskService.isAssignee(1L, "other_user");

    assertFalse(result);
    verify(taskRepository, times(1)).findById(1L);
  }
}
