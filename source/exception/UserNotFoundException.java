package source.exception;

/**
 * Represents an exception for when user is not found in the camps registration 
 * @author florian 
 * @version 1
 * @since 2023-11-19
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Create a UserNotFoundException for when the user is not found in the camp registration 
     * @param message 
     */
    public UserNotFoundException() {
        super ("User not found") ;
    }

    public UserNotFoundException (String message) {
        super (message) ;
    }
}
