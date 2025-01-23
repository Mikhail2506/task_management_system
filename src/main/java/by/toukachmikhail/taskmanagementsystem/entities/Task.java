package by.toukachmikhail.taskmanagementsystem.entities;

import by.toukachmikhail.taskmanagementsystem.enums.TaskPriority;
import by.toukachmikhail.taskmanagementsystem.enums.TaskStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long id;

  @NotBlank
  @Size(min = 5, max = 50, message = "Header must be between 5 and 50 characters")
  @Column(columnDefinition = "VARCHAR(50)")
  private String header;

  @NotBlank
  @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
  @Column(columnDefinition = "VARCHAR(255)")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_id")
  private TaskStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "priority_id")
  private TaskPriority taskPriority;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "assignee_id", nullable = false)
  private User assignee;

  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
  @Builder.Default
  private List<Comment> comments = new ArrayList<>();

  public Task() {
    this.comments = new ArrayList<>();
  }
}
