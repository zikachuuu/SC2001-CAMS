package source.exception;

/**
 * Represents an exception to ensure that the end date set for camp registration exceeds the start date 
 * @author ranielle chio
 * @version 1
 * @since 2023-11-19
 */
public class DateAfterDateException extends RuntimeException {

    /**
     * Create a new DateAfterDateException for the case where the start date exceeds the end date for camp registration date 
     * @param preDate
     * @param postDate
     */
    public DateAfterDateException(String preDate, String postDate) {
        super (preDate + " cannot be after " + postDate + "!") ;
    }

    public DateAfterDateException(String message) {
        super (message) ;
    }
}
