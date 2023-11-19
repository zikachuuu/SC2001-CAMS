package source.exception;

/**
 * Represents an exception for when there is a clash in date between any 2 camps.
 * @author florian
 * @version 1
 * @since 2023-11-19
 */
public class DateClashException extends RuntimeException {
    /**
     * Create a DateClashException that prints a message for when there is a clash in dates between any 2 camps registered by the student 
     * @param message 
     */
    public DateClashException() {
        super ("Clash in date!") ;
    }

    public DateClashException(String message) {
        super (message) ;
    }
}
