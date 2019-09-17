package TaskSystemProject.services;

import TaskSystemProject.entities.*;
import TaskSystemProject.exceptions.GroupNotFoundException;
import TaskSystemProject.repositories.GroupRepository;
import TaskSystemProject.repositories.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    public void createGroup(Group group, User user){
        List<UserGroup> userGroups = new ArrayList<>();
        UserGroup userGroup = new UserGroup();
        userGroup.setRole(new Role("ADMIN"));
        userGroup.setGroup(group);
        userGroup.setUser(user);
        userGroups.add(userGroup);
        group.setUserGroups(userGroups);
        groupRepository.save(group);
    }


    public void addMember(Group group, User user){
        UserGroup userGroup = new UserGroup();
        userGroup.setRole(new Role("USER"));
        userGroup.setGroup(group);
        userGroup.setUser(user);
        group.getUserGroups().add(userGroup);
        groupRepository.save(group);
    }


    public void deleteGroup(Long id){
        if(!existGroupById(id)){
            throw new GroupNotFoundException("Group with id = "+id+" doesn't exist");
        }
        groupRepository.deleteById(id);
    }

    public Group findById(Long id){
        if(!existGroupById(id)){
            throw new GroupNotFoundException("Group with id = "+id+" doesn't exist");
        }
        return groupRepository.findById(id).get();
    }

    public boolean existGroupById(Long id){
        return groupRepository.findById(id).isPresent();
    }

    public List<User> findUsersForGroup(Group group){
        List<UserGroup> userGroups = userGroupRepository.findByGroup(group);
        List<User> users = new ArrayList<>();
        for(int i=0;i<userGroups.size();i++){
            users.add(userGroups.get(i).getUser());
        }
        return users;
    }

    @Transactional
    public void deleteUserAtGroup(Group group,User user){
        userGroupRepository.deleteByGroupAndUser(group,user);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getGroupAdmin(Group group){
        Role admin = new Role("ADMIN");
        UserGroup userGroup = userGroupRepository.findByGroupAndRole(group,admin);
        return userGroup.getUser();
    }

}
