package source.exception;

public class CommitteeWithdrawException extends RuntimeException{
    public CommitteeWithdrawException() {
        super ("Cannot withdraw as a camp committee!") ;
    }

    public CommitteeWithdrawException(String message) {
        super (message) ;
    }
}
