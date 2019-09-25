package TaskSystemProject.services;

import TaskSystemProject.entities.Group;
import TaskSystemProject.entities.User;
import TaskSystemProject.entities.UserGroup;
import TaskSystemProject.exceptions.PasswordNotMatchException;
import TaskSystemProject.exceptions.UserExistException;
import TaskSystemProject.exceptions.UserNotFoundException;
import TaskSystemProject.repositories.UserGroupRepository;
import TaskSystemProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    public void createUser(User user){
        if(userRepository.findById(user.getEmail()).isPresent()){
            throw new UserExistException("User with email: "+user.getEmail()+" exists");
        }
        BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findUser(String email){
        if(!isUserPresent(email)){
            throw new UserNotFoundException("User with email: "+email+" doesn't exist");
        }
        return userRepository.findById(email).get();
    }

    public boolean isUserPresent(String email){
        Optional<User> u = userRepository.findById(email);
        if(u.isPresent()){
            return true;
        }
        return false;
    }

    public void changePassword(User user,String oldPassword,String newPassword){
        BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
        if(encoder.matches(oldPassword,user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        }
        else{
            throw new PasswordNotMatchException("Old password is wrong");
        }
    }

//    public List<Group> findGroupsForUser(User user){
//        List<UserGroup> userGroups = userGroupRepository.findByUser(user);
//        List<Group> groups = new ArrayList<>();
//        for(int i=0;i<userGroups.size();i++){
//            groups.add(userGroups.get(i).getGroup());
//        }
//        return groups;
//    }

    public List<Group> findGroupsForUserWhereAdmin(User user){
        List<UserGroup> userGroups = userGroupRepository.findByUser(user);
        List<Group> groups = new ArrayList<>();
        for(int i=0;i<userGroups.size();i++){
            if(userGroups.get(i).getRole().getName().equals("ADMIN")){
                groups.add(userGroups.get(i).getGroup());
            }
        }
        return groups;
    }

    public List<Group> findGroupsForUserWhereUser(User user){
        List<UserGroup> userGroups = userGroupRepository.findByUser(user);
        List<Group> groups = new ArrayList<>();
        for(int i=0;i<userGroups.size();i++){
            if(userGroups.get(i).getRole().getName().equals("USER")){
                groups.add(userGroups.get(i).getGroup());
            }
        }
        return groups;
    }

}
