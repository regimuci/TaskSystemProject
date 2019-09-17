package TaskSystemProject;

import TaskSystemProject.entities.Role;
import TaskSystemProject.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TaskSystemProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSystemProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(RoleService roleService) {
		return args -> {
			roleService.save(new Role("ADMIN"));
			roleService.save(new Role("USER"));
		};
	}

}
