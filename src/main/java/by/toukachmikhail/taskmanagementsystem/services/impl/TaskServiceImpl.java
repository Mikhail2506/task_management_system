package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.AccessDeniedExceptionMessage.ACCESS_DENIED_EXCEPTION_MESSAGE;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.TASK_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.AccessDeniedException;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.CommentMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.UserMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.CommentRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final TaskMapper taskMapper;
  private final CommentMapper commentMapper;
  private final UserDetailsServiceImpl userDetailsService;
  private final UserMapper userMapper;

  @Override
  public Page<TaskDto> getAllTasks(int page, int size, String sortBy, String direction) {
    User currentUser = userDetailsService.getCurrentUser();
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    if (currentUser.getRole() == UserRole.USER) {
      Page<Task> currentUserTasks = taskRepository.findByAssignee(
          currentUser, pageable);
      return currentUserTasks.map(taskMapper::entityToDtoForList);
    } else if (currentUser.getRole() == UserRole.ADMIN) {
      Page<Task> currentAdminTasks = taskRepository.findByAuthor(currentUser, pageable);
      return currentAdminTasks.map(taskMapper::entityToDtoForList);
    }
    return Page.empty();
  }

  @Override
  public TaskDto getTaskById(Long taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND.getMessage()));
    return taskMapper.entityToDtoForList(task);
  }

  @Override
  @Transactional
  public TaskDto createTask(TaskDto taskDTO) {
    Task task = new Task();
    task.setHeader(taskDTO.header());
    task.setDescription(taskDTO.description());
    task.setStatus(taskDTO.status());
    task.setTaskPriority(taskDTO.priority());

    User assignee = userRepository.findByEmail(taskDTO.assignee().email())
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));

    User author = userDetailsService.getCurrentUser();

    task.setAuthor(author);
    task.setAssignee(assignee);

    Task createdTask = taskRepository.save(task);

    if (taskDTO.comment() != null) {
      Comment comment = new Comment();
      comment.setText(taskDTO.comment().text());
      comment.setTask(createdTask);
      comment.setUser(assignee);

      commentRepository.save(comment);
      createdTask.getComments().add(comment);
    }

    return taskMapper.entityToDtoForCreation(createdTask);
  }

  @Override
  public TaskDto updateTask(Long id, TaskDto taskDto, User currentUser) {
    log.info("Method updateTask called for task ID: {}", id);
    log.info("Current user: {}", currentUser.getUsername());
    log.info("Current user role: {}", currentUser.getRole());

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND.getMessage()));

    if (currentUser.getRole() == UserRole.USER) {
      if (!task.getAssignee().equals(currentUser)) {
        throw new AccessDeniedException(ACCESS_DENIED_EXCEPTION_MESSAGE.getMessage());
      }

      if (taskDto.status() != null) {
        task.setStatus(taskDto.status());
      }
    } else {
      if (taskDto.header() != null) {
        task.setHeader(taskDto.header());
      }
      if (taskDto.description() != null) {
        task.setDescription(taskDto.description());
      }
      if (taskDto.status() != null) {
        task.setStatus(taskDto.status());
      }
      if (taskDto.priority() != null) {
        task.setTaskPriority(taskDto.priority());
      }
      if (taskDto.assignee() != null) {
        User assignee = userRepository.findByUsername(taskDto.assignee().username())
            .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
        task.setAssignee(assignee);
      }
    }

    CommentDto newCommentDto = null;
    if (taskDto.comment() != null) {
      Comment comment = commentMapper.dtoToEntity(taskDto.comment());
      comment.setTask(task);
      comment.setUser(currentUser);
      task.getComments().add(comment);
      commentRepository.save(comment);
      newCommentDto = commentMapper.entityToDTO(comment);
    }

    Task updatedTask = taskRepository.save(task);
    return TaskDto.builder()
        .header(updatedTask.getHeader())
        .description(updatedTask.getDescription())
        .status(updatedTask.getStatus())
        .priority(updatedTask.getTaskPriority())
        .assignee(userMapper.entityToDto(updatedTask.getAssignee()))
        .comment(newCommentDto)
        .build();
  }

  @Override
  public void deleteTask(Long taskId) {
    if (!isTaskExistById(taskId)) {
      throw new NotFoundException(TASK_NOT_FOUND.getMessage());
    }
    taskRepository.deleteById(taskId);
  }

  private boolean isTaskExistById(Long taskId) {
    return taskRepository.existsById(taskId);
  }

  @Override
  public boolean isAssignee(Long taskId, String username) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException("Task not found"));
    return task.getAssignee().getUsername().equals(username);
  }
}
