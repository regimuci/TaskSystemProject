package TaskSystemProject.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String text){
        super(text);
    }
}
