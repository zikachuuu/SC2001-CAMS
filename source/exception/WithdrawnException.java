package source.exception;

/**
 * Represents an exception for when the student has already withdrawn from the camp before 
 * @author Le Yan Zhi
 * @version 1
 * @since 2023-11-19
 */
public class WithdrawnException extends RuntimeException {
    /**
     * Create a WithdrawnException that prints a message for the user 
     * @param message 
     */
    public WithdrawnException() {
        super("Student has already withdrawn from the camp before!") ;
    }

    public WithdrawnException(String message) {
        super (message) ;
    }
}
