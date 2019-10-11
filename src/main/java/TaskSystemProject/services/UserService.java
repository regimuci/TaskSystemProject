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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    //@CachePut(value = "usersCache",key = "#user.email")
    public User createUser(User user){
        if(userRepository.findById(user.getEmail()).isPresent()){
            throw new UserExistException("User with email: "+user.getEmail()+" exists");
        }
        BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //@Cacheable(value = "usersCache",key = "#email")
    public User findUser(String email){
        if(!userRepository.findById(email).isPresent()){
            throw new UserNotFoundException("User with email: "+email+" doesn't exist");
        }
        return userRepository.findById(email).get();
    }

    //@CachePut(value = "usersCache",key = "#user.email")
    public User changePassword(User user,String oldPassword,String newPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPassword,user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            return userRepository.save(user);
        }
        else{
            throw new PasswordNotMatchException("Old password is wrong");
        }
    }

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

        public List<Group> findGroupsForUser(User user){
        List<UserGroup> userGroups = userGroupRepository.findByUser(user);
        List<Group> groups = new ArrayList<>();
        for(int i=0;i<userGroups.size();i++){
            groups.add(userGroups.get(i).getGroup());
        }
        return groups;
    }

}
