package source.exception;

/**
 * Represents the exception for when a camp registration deadline has passed 
 * @author florian
 * @version 1
 * @since 2023-11-19
 */
public class DeadlineOverException extends RuntimeException{

    /**
     * Create a DeadlineOverException which prints a message when the camp registration deadline has passed 
     * @param message 
     */
    public DeadlineOverException() {
        super("Camp registration deadline passed!") ;
    }

    public DeadlineOverException(String message) {
        super (message) ;
    }
}
