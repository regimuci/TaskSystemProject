package TaskSystemProject.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String text){
        super(text);
    }
}
