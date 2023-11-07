package source.exception;

public class NoAccessException extends RuntimeException {
    public NoAccessException() {
        super ("You do not have access!") ;
    }

    public NoAccessException(String message) {
        super (message) ;
    }
}
