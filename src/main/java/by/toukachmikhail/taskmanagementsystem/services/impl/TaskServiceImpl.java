package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.AccessDeniedExceptionMessage.ACCESS_DENIED_EXCEPTION_MESSAGE;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.USER_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.TASK_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.AccessDeniedException;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.CommentMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
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

  @Override
  public Page<TaskDto> getAllTasks(int page, int size, String sortBy, String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Task> allTasks = taskRepository.findAll(pageable);

    return allTasks.map(taskMapper::entityToDto);
  }

  @Override
  public TaskDto getTaskById(Long taskId) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND.getMessage()));
    return taskMapper.entityToDto(task);
  }

  @Override
  public Page<TaskDto> getTasksByAuthor(Long authorId, int page, int size, String sortBy,
      String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Task> tasks = taskRepository.findByAuthorId(authorId, pageable);
    return tasks.map(taskMapper::entityToDto);
  }

  @Override
  public Page<TaskDto> getTasksByAssignee(Long assigneeId, int page, int size, String sortBy,
      String direction) {
    Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Task> tasks = taskRepository.findByAuthorId(assigneeId, pageable);
    return tasks.map(taskMapper::entityToDto);
  }

  @Override
  @Transactional
  public TaskDto createTask(TaskDto taskDTO) {
    Task task = new Task();
    task.setHeader(taskDTO.header());
    task.setDescription(taskDTO.description());
    task.setStatus(taskDTO.status());
    task.setTaskPriority(taskDTO.priority());

    User author = userRepository.findByUsername(taskDTO.author().username())
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));
    User assignee = userRepository.findByUsername(taskDTO.assignee().username())
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));

    task.setAuthor(author);
    task.setAssignee(assignee);

    Task createdTask = taskRepository.save(task);

    if (taskDTO.comment() != null) {
      Comment comment = new Comment();
      comment.setText(taskDTO.comment().text());
      comment.setTask(createdTask);
      comment.setUser(assignee);

      commentRepository.save(comment);
    }

    return taskMapper.entityToDto(createdTask);
  }

  @Override
  public TaskDto updateTask(Long id, TaskDto taskDTO, User currentUser) {
    log.info("Method updateTask called for task ID: {}", id);
    log.info("Current user: {}", currentUser.getUsername());
    log.info("Current user role: {}", currentUser.getRole());

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND.getMessage()));
    log.info("User {}", currentUser.getRole());

    if (currentUser.getRole() == UserRole.USER) {
      if (!task.getAssignee().equals(currentUser)) {
        throw new AccessDeniedException(ACCESS_DENIED_EXCEPTION_MESSAGE.getMessage());
      }

      if (taskDTO.status() != null) {
        task.setStatus(taskDTO.status());
      }
    } else {
      if (taskDTO.header() != null) {
        task.setHeader(taskDTO.header());
      }
      if (taskDTO.description() != null) {
        task.setDescription(taskDTO.description());
      }
      if (taskDTO.status() != null) {
        task.setStatus(taskDTO.status());
      }
      if (taskDTO.priority() != null) {
        task.setTaskPriority(taskDTO.priority());
      }
      if (taskDTO.assignee() != null) {
        User assignee = userRepository.findByUsername(taskDTO.assignee().username())
            .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
        task.setAssignee(assignee);
      }
    }

    if (taskDTO.comment() != null) {
      Comment newComment = commentMapper.dtoToEntity(taskDTO.comment());
      newComment.setTask(task);
      newComment.setUser(currentUser);
      task.getComments().add(newComment);
    }

    Task updatedTask = taskRepository.save(task);
    return taskMapper.entityToDto(updatedTask);
  }

  @Override
  public void deleteTask(Long taskId) {
    taskRepository.deleteById(taskId);
  }

  @Override
  public boolean isAssignee(Long taskId, String username) {
    Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NotFoundException("Task not found"));
    return task.getAssignee().getUsername().equals(username);
  }

}
