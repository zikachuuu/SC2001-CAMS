package source.ngui;

import source.user.User;

/**
 * Represents an interface for camp administration
 * @author ranielle chio
 * @version 1
 * @since 2023-11-19
 */
public interface ICampAdminInterface {
    /**
     * Create a method for adding a camp
     * @param user
     */
    public void handleCampAdd(User user) ;

    /**
     * Create a method for editing a camp
     * @param user
     */
    public void handleCampEdit(User user) ;

    /**
     * Create a method for toggling camp visibility 
     * @param user
     */
    public void handleCampToggle (User user) ;

    /**
     * Create a method for deleting a camp 
     * @param user
     */
    public void handleCampDelete(User user) ;
}
