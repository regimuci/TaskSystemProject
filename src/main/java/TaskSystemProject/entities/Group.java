package TaskSystemProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    @JsonIgnore
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<Task> tasks;


    public Group(){

    }

    public Group(@NotEmpty String name) {
        this.name = name;
    }

    public Group(@NotEmpty String name, List<UserGroup> userGroups, List<Task> tasks) {
        this.name = name;
        this.userGroups = userGroups;
        this.tasks = tasks;
    }

    public Group(@NotEmpty String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
