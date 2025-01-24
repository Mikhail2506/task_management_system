package by.toukachmikhail.taskmanagementsystem.entities;

import by.toukachmikhail.taskmanagementsystem.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
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
  private Long id;

  @Column(name = "user_name", columnDefinition = "VARCHAR(30)")
  @Size(min = 3, max = 30, message = "Username must be at least 3 characters long")
  private String username;

  @Column(name = "user_email", columnDefinition = "VARCHAR(30)")
  @NotBlank(message = "Users email required")
  @Email(message = "Invalid email format")
  private String email;

  @Column(name = "user_password", columnDefinition = "VARCHAR(256)")
  @Size(min = 5, max = 256, message = "Password must be at least 5 characters long")
  private String password;

  @OneToMany(mappedBy = "assignee", cascade = CascadeType.REMOVE)
  private List<Task> tasks;

  @Enumerated(EnumType.STRING) // Храним роль как строку в базе данных
  @Column(name = "role", nullable = false)
  private UserRole role;

}
