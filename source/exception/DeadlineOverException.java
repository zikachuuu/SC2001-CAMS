package source.exception;

public class DeadlineOverException extends RuntimeException{
    
    public DeadlineOverException() {
        super("Camp registration deadline passed!") ;
    }
}