package TaskSystemProject.controllers;

import TaskSystemProject.entities.*;
import TaskSystemProject.exceptions.UnauthorizedException;
import TaskSystemProject.services.GroupService;
import TaskSystemProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @PostMapping("/createGroup")
    public ResponseEntity<Object> createGroup(Authentication authentication, @RequestBody Group group){
        String email = authentication.getName();
        User user = userService.findUser(email);
        groupService.createGroup(group,user);
        return new ResponseEntity<>("Group "+group.getName()+" created successfully",HttpStatus.OK);
    }

    @PostMapping("/addMember/{groupId}")
    public ResponseEntity<Object> addMember(Authentication authentication,@PathVariable Long groupId,@RequestParam String email){
        Group group = groupService.findById(groupId);
        User user = userService.findUser(email);
        if (!groupService.getGroupAdmin(group).getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException("No authorization");
        }
        groupService.addMember(group,user);
        return new ResponseEntity<>(user.getEmail()+" added at group "+group.getName(),HttpStatus.OK);
    }

    @GetMapping("/findUsersForGroup/{groupId}")
    public ResponseEntity<List<User>> findUsersForGroup(@PathVariable Long groupId){
        Group group = groupService.findById(groupId);
        List<User> users = groupService.findUsersForGroup(group);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }


    @DeleteMapping("/deleteUserAtGroup/{groupId}")
    public ResponseEntity<Object> deleteUserAtGroup(Authentication authentication,@PathVariable Long groupId,@RequestParam String email){
        Group group = groupService.findById(groupId);
        User user = userService.findUser(email);
        if (!groupService.getGroupAdmin(group).getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException("No authorization");
        }
        groupService.deleteUserAtGroup(group,user);
        return new ResponseEntity<>("User "+ email +" deleted successfully from group " +
                group.getName(),HttpStatus.OK);
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<Object> deleteGroup(Authentication authentication,@PathVariable Long groupId){
        Group group = groupService.findById(groupId);
        if (!groupService.getGroupAdmin(group).getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException("No authorization");
        }
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>("Group deleted successfully",HttpStatus.OK);
    }

}
