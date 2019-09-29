package TaskSystemProject.controllers;

import TaskSystemProject.entities.Group;
import TaskSystemProject.entities.User;
import TaskSystemProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<Object>("User with email "+ user.getEmail() +
                " created successfully",HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Object> changePassword(Authentication authentication,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword){
        String email = authentication.getName();
        User user = userService.findUser(email);
        userService.changePassword(user,oldPassword,newPassword);
        return new ResponseEntity<Object>("Password changed successfully",HttpStatus.OK);
    }

    @GetMapping("/findGroupsForUserWhereRoleAdmin")
    public ResponseEntity<List<Group>> findGroupsForUserWhereAdmin(Authentication authentication){
        String email = authentication.getName();
        User user = userService.findUser(email);
        List<Group> groups = userService.findGroupsForUserWhereAdmin(user);
        return new ResponseEntity<List<Group>>(groups,HttpStatus.OK);
    }

    @GetMapping("/findGroupsForUserWhereRoleUser")
    public ResponseEntity<List<Group>> findGroupsForUserWhereUser(Authentication authentication){
        String email = authentication.getName();
        User user = userService.findUser(email);
        List<Group> groups = userService.findGroupsForUserWhereUser(user);
        return new ResponseEntity<List<Group>>(groups,HttpStatus.OK);
    }

    //    @GetMapping("/findGroupsForUser")
//    public ResponseEntity<List<Group>> findByUser(Authentication authentication){
//        String email = authentication.getName();
//        User user = userService.findUser(email);
//        List<Group> groups = userService.findGroupsForUser(user);
//        return new ResponseEntity<List<Group>>(groups,HttpStatus.OK);
//    }

}
