package TaskSystemProject.repositories;

import TaskSystemProject.entities.Group;
import TaskSystemProject.entities.Task;
import TaskSystemProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByGroup(Group group);
    List<Task> findByGroupAndUser(Group group,User user);
}
