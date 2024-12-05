package by.toukachmikhail.taskmanagementsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "task_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TasksStatuses {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "status_id")
  private Long statusId;

  @NonNull
  @Column(columnDefinition = "VARCHAR(20)")
  private String status;
}