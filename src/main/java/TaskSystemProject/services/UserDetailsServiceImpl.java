package TaskSystemProject.services;

import TaskSystemProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional <TaskSystemProject.entities.User> user = userRepository.findById(email);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("Email or password incorrect");
        }
        return new User(user.get().getEmail(),user.get().getPassword(),emptyList());
    }
}
