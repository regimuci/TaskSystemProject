package TaskSystemProject.controllers;

import TaskSystemProject.entities.Group;
import TaskSystemProject.entities.Task;
import TaskSystemProject.entities.User;
import TaskSystemProject.exceptions.UnauthorizedException;
import TaskSystemProject.services.GroupService;
import TaskSystemProject.services.TaskService;
import TaskSystemProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @PostMapping("/addTaskToGroup/{groupId}")
    public ResponseEntity<Object> addTaskToGroup(Authentication authentication, @RequestBody Task task, @PathVariable Long groupId){
        Group group = groupService.findById(groupId);
        if (!groupService.getGroupAdmin(group).getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException("No authorization");
        }
        taskService.addTaskToGroup(task,group);
        return new ResponseEntity<>("Task addedd successfully to group "+group.getName(),HttpStatus.OK);
    }

    @PostMapping("/addTaskToUser/{groupId}/{userEmail}")
    public ResponseEntity<Object> addTaskToUser(Authentication authentication,
                                                @RequestBody Task task,@PathVariable Long groupId,
                                              @PathVariable String userEmail){
        Group group = groupService.findById(groupId);
        User user = userService.findUser(userEmail);
        if (!groupService.getGroupAdmin(group).getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException("No authorization");
        }
        taskService.addTaskToUser(task,group,user);
        return new ResponseEntity<>("Task addedd succesfully to group "+group.getName()+
                " for user "+user.getEmail(),HttpStatus.OK);
    }

    @PostMapping("/changeStatus/{taskId}")
    public ResponseEntity<Object> changeStatus(Authentication authentication,@PathVariable Long taskId){
        if(!authentication.isAuthenticated()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Task task = taskService.findById(taskId);
        taskService.changeStatus(task);
        return new ResponseEntity<>("Task status changed to "+task.getStatus(),HttpStatus.OK);
    }

    @GetMapping("getAllTasksAtGroup/{groupId}")
    public ResponseEntity<List<Task>> getAllTasks(Authentication authentication,@PathVariable Long groupId){
        if(!authentication.isAuthenticated()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Group group = groupService.findById(groupId);
        List<Task> tasks = taskService.findByGroup(group);
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

    @GetMapping("getAllTasksAtGroupForAUser/{groupId}")
    public ResponseEntity<List<Task>> getAllTasksForAUser(@PathVariable Long groupId,
                                                  Authentication authentication){
        String userEmail = authentication.getName();
        Group group = groupService.findById(groupId);
        User user = userService.findUser(userEmail);
        List<Task> tasks = taskService.findByGroupAndUser(group,user);
        return new ResponseEntity<>(tasks,HttpStatus.OK);
    }

}
