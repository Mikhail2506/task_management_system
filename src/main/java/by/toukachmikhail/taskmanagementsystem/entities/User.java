package by.toukachmikhail.taskmanagementsystem.entities;

import by.toukachmikhail.taskmanagementsystem.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_name", columnDefinition = "VARCHAR(30)", nullable = false)
  private String username;

  @Column(name = "user_role", columnDefinition = "VARCHAR(10)", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;


  @Column(name = "user_phone_number", columnDefinition = "VARCHAR(30)", nullable = false)
  private String usersPhoneNumber;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "user_task",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn (name = "task_id"))
  private Set<Task> tasks = new HashSet<>();
}
