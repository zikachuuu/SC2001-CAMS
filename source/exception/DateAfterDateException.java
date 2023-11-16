package source.exception;

public class DateAfterDateException extends RuntimeException {

    public DateAfterDateException(String preDate, String postDate) {
        super (preDate + " cannot be after " + postDate + "!") ;
    }

    public DateAfterDateException(String message) {
        super (message) ;
    }
}
