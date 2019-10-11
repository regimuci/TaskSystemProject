package TaskSystemProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String date;

    @NotEmpty
    private String startTime;

    @NotEmpty
    private String stopTime;

    @NotEmpty
    private String description;

    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonSetter
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL)
    private List<Comment> comments;


    public Task(){

    }

    public Task(@NotEmpty String startTime, @NotEmpty String stopTime, @NotEmpty String description, String status) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.description = description;
        this.status = status;
    }

    public Task(@NotEmpty String startTime, @NotEmpty String stopTime, @NotEmpty String description, String status, Group group) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.description = description;
        this.status = status;
        this.group = group;
    }

    public Task(@NotEmpty String startTime, @NotEmpty String stopTime, @NotEmpty String description, String status, User user) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
