package source.exception;

/**
 * Represents an exception that limits camp committee members to only be the camp committee member for one camp.
 * @author daryl tan
 * @version 1
 * @since 2023-11-19
 */
public class MultipleCommitteeRoleException extends RuntimeException {

    /**
     * Creates a MultipleCommitteeRoleException that prints an error message to Camp Committee member if they were to 
     * register as a camp committee member for more than one camp 
     * @param message 
     */
    public MultipleCommitteeRoleException() {
        super ("Can only be in the camp committee for one camp!") ;
    }

    public MultipleCommitteeRoleException(String message) {
        super (message) ;
    }
}
