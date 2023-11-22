package source.exception;


    /**
 * Represents exception for when camp committee tries to withdraw from camp 
 * @author minjie
 * @version 1
 * @since 2023-11-19
 */
public class CommitteeWithdrawException extends RuntimeException{
    /**
     * Create an exception for when Camp Committee tries to withdraw from camp
     */
    public CommitteeWithdrawException() {
        super ("Cannot withdraw as a camp committee!") ;
    }
    /**
     * Create an exception for when Camp Committee tries to withdraw from camp
     * @param message 
     */
    public CommitteeWithdrawException(String message) {
        super (message) ;
    }
}
