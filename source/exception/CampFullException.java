package source.exception;

public class CampFullException extends RuntimeException {
    public CampFullException () {
        super ("Camp is full.") ;
    }
}
