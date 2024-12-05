package by.toukachmikhail.taskmanagementsystem.entities;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long taskId;

  @NonNull
  @Column(columnDefinition = "VARCHAR(30)")
  private String header;

  @NonNull
  @Column(columnDefinition = "VARCHAR(30)", nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(30)")
  private TaskStatus status;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(30)")
  private TaskPriority taskPriority;

  @ManyToMany(mappedBy = "tasks")
  private List<User> users = new ArrayList<>();;

  @Column(columnDefinition = "VARCHAR(255)", nullable = false)
  private String comment;
}
