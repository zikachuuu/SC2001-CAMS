package source.exception;

public class MultipleCommitteeRoleException extends RuntimeException {
    
    public MultipleCommitteeRoleException() {
        super ("Can only be in the camp committee for one camp!") ;
    }
}
