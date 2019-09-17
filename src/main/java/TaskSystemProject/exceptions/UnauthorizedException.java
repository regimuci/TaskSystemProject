package TaskSystemProject.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String text){
        super(text);
    }
}
