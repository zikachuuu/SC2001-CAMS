package source.exception;

public class withdrawnException extends RuntimeException {
    public withdrawnException() {
        super("Student has already withdrawn from the camp before!") ;
    }

    public withdrawnException(String message) {
        super (message) ;
    }
}
