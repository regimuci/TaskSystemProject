package TaskSystemProject.services;

import TaskSystemProject.entities.Role;
import TaskSystemProject.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void save(Role role){
        roleRepository.save(role);
    }
}
