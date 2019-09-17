package TaskSystemProject.repositories;

import TaskSystemProject.entities.Group;
import TaskSystemProject.entities.Role;
import TaskSystemProject.entities.User;
import TaskSystemProject.entities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {
    List<UserGroup> findByUser(User user);
    List<UserGroup> findByGroup(Group group);
    void deleteByGroupAndUser(Group group,User user);
    UserGroup findByGroupAndUser(Group group,User user);
    UserGroup findByGroupAndRole(Group group, Role role);
}
