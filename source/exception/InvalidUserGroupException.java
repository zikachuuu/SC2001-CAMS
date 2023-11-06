package source.exception;

public class InvalidUserGroupException extends RuntimeException{
    
    public InvalidUserGroupException() {
        super("Camp not opened to the student's user group!") ;
    }
}
