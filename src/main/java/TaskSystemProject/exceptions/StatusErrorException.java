package TaskSystemProject.exceptions;

public class StatusErrorException extends RuntimeException {
    public StatusErrorException(String text){
        super(text);
    }
}
