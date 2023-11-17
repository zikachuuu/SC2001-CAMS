package source.exception;

public class ExceedMaximumException extends RuntimeException{
    public ExceedMaximumException() {
        super ("Maximum exceeded!") ;
    }

    public ExceedMaximumException(String message) {
        super (message) ;
    }
}
