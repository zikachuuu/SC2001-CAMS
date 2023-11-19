package source.exception;

/**
 * Represents the exception for when the camp is full by generating a message for the user.
 * @author raniellechio
 * @version 1
 * @since 2023-11-19
 */

public class CampFullException extends RuntimeException {

    /**
     * Create exception for when camp is full with a message for user
     * @param message
     */
    public CampFullException () {
        super ("Camp is full.") ;
    }

    public CampFullException (String message) {
        super (message) ;
    }
}
