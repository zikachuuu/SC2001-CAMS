package source.exception;

public class WithdrawnException extends RuntimeException {
    public WithdrawnException() {
        super("Student has already withdrawn from the camp before!") ;
    }

    public WithdrawnException(String message) {
        super (message) ;
    }
}