package by.toukachmikhail.taskmanagementsystem.repositories;

import by.toukachmikhail.taskmanagementsystem.entities.Task;
import by.toukachmikhail.taskmanagementsystem.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  Page<Task> findByAssignee(User assignee, Pageable pageable);

  Page<Task> findByAuthor(User author, Pageable pageable);
}
