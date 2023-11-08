package source.exception;

public class DateClashException extends RuntimeException {
    public DateClashException() {
        super ("Clash in date!") ;
    }
}
