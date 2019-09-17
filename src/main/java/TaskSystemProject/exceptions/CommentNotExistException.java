package TaskSystemProject.exceptions;



public class CommentNotExistException extends RuntimeException {
    public CommentNotExistException(String text){
        super(text);
    }
}
