package source.exception;

/**
 * Represents an exception for when the maximum capacity of a camp has been exceeded.
 * @author Le Yan Zhi
 * @version 1
 * @since 2023-11-19
 */
public class ExceedMaximumException extends RuntimeException{
    /**
     * Create an ExceedMaximumException for when the maximum capacity of a camp has been exceeded. 
     * @param message 
     */
    public ExceedMaximumException() {
        super ("Maximum exceeded!") ;
    }

    public ExceedMaximumException(String message) {
        super (message) ;
    }
}
