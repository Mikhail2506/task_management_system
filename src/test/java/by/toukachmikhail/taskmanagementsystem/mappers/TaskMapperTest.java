package by.toukachmikhail.taskmanagementsystem.mappers;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

  @Mock
  private UserMapper userMapper;

  @Mock
  private CommentMapper commentMapper;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TaskMapper taskMapper;

  private Task task;
  private TaskDto taskDto;
  private User user;
  private UserDto userDto;
  private Comment comment;
  private CommentDto commentDto;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setUsername("Mikl");
    user.setEmail("mikl@mail.ru");

    userDto = new UserDto("Mikl", "mikl@mail.ru", UserRole.USER);

    comment = new Comment();
    comment.setId(1L);
    comment.setText("Text");

    task = Task.builder()
        .header("Header")
        .description("Description")
        .status(TaskStatus.FINISHED)
        .taskPriority(TaskPriority.HIGH)
        .assignee(user)
        .comments(Collections.singletonList(comment))
        .build();

    commentDto = new CommentDto("Text");

    taskDto = TaskDto.builder()
        .header("Header")
        .description("Description")
        .status(TaskStatus.FINISHED)
        .priority(TaskPriority.HIGH)
        .assignee(userDto)
        .comments(List.of(commentDto))
        .build();
  }

  @Test
  void entityToDtoForCreationWhenMappedCorrectly() {
    when(userMapper.entityToDto(user)).thenReturn(userDto);
    when(commentMapper.entityToDTO(comment)).thenReturn(commentDto);

    TaskDto result = taskMapper.entityToDto(task);

    assertNotNull(result);
    assertEquals(task.getHeader(), result.header());
    assertEquals(task.getDescription(), result.description());
    assertEquals(task.getStatus(), result.status());
    assertEquals(task.getTaskPriority(), result.priority());
    assertEquals(userDto, result.assignee());
    assertEquals(List.of(commentDto), result.comments());
    verify(userMapper).entityToDto(user);
    verify(commentMapper).entityToDTO(comment);
  }

  @Test
  void entityToDtoForListWhenMappedCorrectly() {
    when(userMapper.entityToDto(user)).thenReturn(userDto);
    when(commentMapper.entityToDTO(comment)).thenReturn(commentDto);

    TaskDto result = taskMapper.entityToDto(task);

    assertNotNull(result);
    assertEquals(task.getHeader(), result.header());
    assertEquals(task.getDescription(), result.description());
    assertEquals(task.getStatus(), result.status());
    assertEquals(task.getTaskPriority(), result.priority());
    assertEquals(userDto, result.assignee());
    assertEquals(1, result.comments().size());
    assertEquals(commentDto, result.comments().get(0));

    verify(userMapper).entityToDto(user);
    verify(commentMapper).entityToDTO(comment);
  }

  @Test
  void dtoToEntityWhenMappedCorrectly() {
    when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.of(user));
    when(commentMapper.dtoToEntity(commentDto)).thenReturn(comment);

    Task result = taskMapper.dtoToEntity(taskDto);

    assertNotNull(result);
    assertEquals(taskDto.header(), result.getHeader());
    assertEquals(taskDto.description(), result.getDescription());
    assertEquals(taskDto.status(), result.getStatus());
    assertEquals(taskDto.priority(), result.getTaskPriority());
    assertEquals(user, result.getAssignee());
    assertEquals(1, result.getComments().size());
    assertEquals(comment, result.getComments().get(0));

    verify(userRepository).findByEmail(userDto.email());
    verify(commentMapper).dtoToEntity(commentDto);
  }

  @Test
  void dtoToEntity_ShouldThrowExceptionWhenAssigneeNotFound() {
    when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.empty());

    NotFoundException exception = assertThrows(NotFoundException.class, () -> {
      taskMapper.dtoToEntity(taskDto);
    });

    assertEquals(ASSIGNEE_NOT_FOUND.getMessage(), exception.getMessage());

    verify(userRepository).findByEmail(userDto.email());
  }
}
