package by.toukachmikhail.taskmanagementsystem.repositories;

import by.toukachmikhail.taskmanagementsystem.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
