package source.exception;

/**
 * Represents an exception for when the user does not have access
 * @author minjie
 * @version 1
 * @since 2023-11-19
 */
public class NoAccessException extends RuntimeException {
    /**
     * Create a NoAccessException for when the user has no access 
     */
    public NoAccessException() {
        super ("You do not have access!") ;
    }
    /**
     * Create a NoAccessException for when the user has no access
     * @param message 
     */
    public NoAccessException(String message) {
        super (message) ;
    }
}
