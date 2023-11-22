package source.exception;

/**
 * Represents an exception for when the camp is not yet opened to the student user yet.
 * @author raniellechio 
 * @version 1
 * @since 2023-11-19
 */
public class InvalidUserGroupException extends RuntimeException{

    /**
     * Create an InvalidUserGroupException that prints a message to student user that the camp registration has not been opened to them yet.
     */
    public InvalidUserGroupException() {
        super("Camp not opened to the student's user group!") ;
    }
    /**
     * Create an InvalidUserGroupException that prints a message to student user that the camp registration has not been opened to them yet.
     * @param message 
     */
    public InvalidUserGroupException(String message) {
        super (message) ;
    }
}
