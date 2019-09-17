package TaskSystemProject.exceptions;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String text){
        super(text);
    }
}
