package source.ngui;

import source.user.User;

/**
 * Represents an interface for Camp Participants
 * @author raniellechio
 * @version 1
 * @since 2023-11-19
 */
public interface ICampParticipantInterface {
    /**
     * Create a new handle for camp registration for user
     * @param user
     */
    public void handleCampRegister(User user) ;

    /**
     * Create a new handle for camp withdrawal for user
     * @param user
     */
    public void handleCampWithdraw(User user) ;
}
