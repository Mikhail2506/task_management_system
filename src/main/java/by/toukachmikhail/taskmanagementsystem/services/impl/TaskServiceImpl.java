package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ASSIGNEE_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.AUTHOR_NOT_FOUND;
import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.TASK_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.dto.CommentDto;
import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import by.toukachmikhail.taskmanagementsystem.entities.Comment;
import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.mappers.CommentMapper;
import by.toukachmikhail.taskmanagementsystem.mappers.TaskMapper;
import by.toukachmikhail.taskmanagementsystem.repositories.CommentRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.TaskRepository;
import by.toukachmikhail.taskmanagementsystem.repositories.UserRepository;
import by.toukachmikhail.taskmanagementsystem.services.CommentService;
import by.toukachmikhail.taskmanagementsystem.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
        .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND.getMessage()));
    User assignee = userRepository.findByUsername(taskDTO.assignee().username())
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));

    task.setAuthor(author);
    task.setAssignee(assignee);

    Task createdTask = taskRepository.save(task);

    Comment comment = new Comment();
    comment.setText(String.valueOf(taskDTO.comment()));
    comment.setTask(createdTask);

    User commentUser = userRepository.findByUsername(task.getAssignee().getUsername())
        .orElseThrow(() -> new NotFoundException(ASSIGNEE_NOT_FOUND.getMessage()));
    comment.setUser(commentUser);

    commentRepository.save(comment);

    return taskMapper.entityToDto(createdTask);
  }

  @Override
  public TaskDto updateTask(Long id, TaskDto taskDTO) {

    Task taskDetails = taskMapper.dtoToEntity(taskDTO);

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND.getMessage()));

    if (taskDetails.getAssignee() != null) {
      User assignee = userRepository.findByUsername(taskDetails.getAssignee().getUsername())
          .orElseThrow(() -> new NotFoundException(
              ASSIGNEE_NOT_FOUND.getMessage()));

      taskDetails.setAssignee(assignee);
    }

    if (taskDTO.comment() != null) {
      Comment newComment = commentMapper.dtoToEntity(taskDTO.comment());
      newComment.setTask(task);
      task.getComments().add(newComment);
    }

    task.setHeader(taskDetails.getHeader());
    task.setDescription(taskDetails.getDescription());
    task.setStatus(taskDetails.getStatus());
    task.setTaskPriority(taskDetails.getTaskPriority());
    Task updatedTask = taskRepository.save(task);
    return taskMapper.entityToDto(updatedTask);
  }

  @Override
  public void deleteTask(Long taskId) {
    taskRepository.deleteById(taskId);
  }

}
