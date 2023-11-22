package source.exception;

/**
 * Represents an exception to ensure that the end date set exceeds the start date 
 * @author ranielle chio
 * @version 1
 * @since 2023-11-19
 */
public class DateAfterDateException extends RuntimeException {

    /**
     * Create a new DateAfterDateException for the case where the start date exceeds the end date
     * @param preDate
     * @param postDate
     */
    public DateAfterDateException(String preDate, String postDate) {
        super (preDate + " cannot be after " + postDate + "!") ;
    }
    /**
     * Create a new DateAfterDateException for the case where the start date exceeds the end date
     * @param message
     */
    public DateAfterDateException(String message) {
        super (message) ;
    }
}
