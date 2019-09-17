package TaskSystemProject.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String text){
        super(text);
    }
}
