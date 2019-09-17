package TaskSystemProject.services;

import TaskSystemProject.entities.Group;
import TaskSystemProject.entities.Task;
import TaskSystemProject.entities.User;
import TaskSystemProject.exceptions.StatusErrorException;
import TaskSystemProject.exceptions.TaskNotFoundException;
import TaskSystemProject.exceptions.UserNotFoundException;
import TaskSystemProject.repositories.GroupRepository;
import TaskSystemProject.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    public void addTaskToGroup(Task task, Group group){
        task.setGroup(group);
        task.setStatus("UNDONE");
        taskRepository.save(task);
    }

    public void addTaskToUser(Task task, Group group, User user){
        if(!groupService.findUsersForGroup(group).contains(user)){
            throw new UserNotFoundException("User with email "+user.getEmail()+" is not at this group");
        }
        task.setGroup(group);
        task.setUser(user);
        task.setStatus("UNDONE");
        taskRepository.save(task);
    }

    public void changeStatus(Task task){
        if(task.getStatus().equals("DONE")){
            task.setStatus("UNDONE");
        }
        else {
            task.setStatus("DONE");
        }
        taskRepository.save(task);
    }

    public Task findById(Long id){
        if(!existsTaks(id)){
            throw new TaskNotFoundException("Task with id = "+id+" doesn't exist");
        }
        return taskRepository.findById(id).get();
    }

    public boolean existsTaks(Long id){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            return true;
        }
        return false;
    }

    public List<Task> findByGroup(Group group){
        return taskRepository.findByGroup(group);
    }

    public List<Task> findByGroupAndUser(Group group,User user){
        if(!groupService.findUsersForGroup(group).contains(user)){
            throw new UserNotFoundException("User with email "+user.getEmail()+" is not at this group");
        }
        return taskRepository.findByGroupAndUser(group,user);
    }

}
