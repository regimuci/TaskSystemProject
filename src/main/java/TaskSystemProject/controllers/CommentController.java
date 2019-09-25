package TaskSystemProject.controllers;

import TaskSystemProject.entities.Comment;
import TaskSystemProject.entities.Task;
import TaskSystemProject.entities.User;
import TaskSystemProject.services.CommentService;
import TaskSystemProject.services.TaskService;
import TaskSystemProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @PostMapping("/createComment/{taskId}")
    public ResponseEntity<Object> createComment(@PathVariable Long taskId, Authentication authentication,
                                                @RequestBody Comment comment){
        String userEmail = authentication.getName();
        Task task = taskService.findById(taskId);
        User user = userService.findUser(userEmail);
        commentService.addComment(task,user,comment);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long commentId,Authentication authentication){
        String email = authentication.getName();
        User user = userService.findUser(email);
        commentService.deleteComment(commentId,user);
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/getCommentsForTask/{taskId}")
    public ResponseEntity<List<Comment>> getCommentsForTask(Authentication authentication,@PathVariable Long taskId){
        if(!authentication.isAuthenticated()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Task task = taskService.findById(taskId);
        List<Comment> comments = commentService.findCommentsForTask(task);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

}
