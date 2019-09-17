package TaskSystemProject.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String text){
        super(text);
    }
}
