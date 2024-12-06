package by.toukachmikhail.taskmanagementsystem.repositories;

import by.toukachmikhail.taskmanagementsystem.entities.TasksStatuses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksStatusesRepository extends JpaRepository<TasksStatuses, Long> {

}
