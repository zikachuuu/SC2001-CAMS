package source.ngui;

import source.user.User;

/**
 * Represents an interface for handling suggestions 
 * @author ranielle chio
 * @version 1
 * @since 2023-11-19
 */
public interface ISuggestionAdminInterface {
    /**
     * Creates an abstract method for handling and approving suggestions 
     * @param user
     */
    public void handleSuggestionViewApprove (User user) ;
}
