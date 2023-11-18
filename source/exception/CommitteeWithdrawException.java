package source.exception;

/**
 * Represents the exception for when a camp committee member tries to withdraw from a camp.
 * @author minjie
 * @version 1
 * @since 2023-11-19
 */
public class CommitteeWithdrawException extends RuntimeException{
    /**
     * Create a CommitteeWithdrawException that prints a message to Camp Committee member that tries to withdraw from the camp
     * @param message 
     */
    public CommitteeWithdrawException() {
        super ("Cannot withdraw as a camp committee!") ;
    }

    public CommitteeWithdrawException(String message) {
        super (message) ;
    }
}
