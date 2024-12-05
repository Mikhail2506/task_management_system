package by.toukachmikhail.taskmanagementsystem.repositories;

import by.toukachmikhail.taskmanagementsystem.dto.TaskDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskDto, Long> {

  Page<TaskDto> findAll(Pageable pageable);


  Optional<TaskDto> findById(Long taskId);
}
