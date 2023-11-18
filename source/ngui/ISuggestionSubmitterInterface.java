package source.ngui;

import source.user.User;

/**
 * Represents an interface for submitting suggestions
 * @author daryl tan
 * @version 1
 * @since 2023-11-19
 */
public interface ISuggestionSubmitterInterface {
    /**
     * Create a method for handling addition of suggestions by users. 
     * @param user
     */
    public void handleSuggestionAdd(User user) ;
}
