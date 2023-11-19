package source.ngui;

import source.user.User;

/**
 * Represents an interface for submitting enquiries 
 * @author minjie 
 * @version 1
 * @since 2023-11-19
 */
public interface IEnquirySubmitterInterface {
    /**
     * Create an abstract method for submission of enquiries 
     * @param user
     */
    public void handleSubmitEnquiry(User user) ;

    /**
     * Create an abstract method for editing enquiries 
     * @param user
     */
    public void handleEditEnquiry(User user) ;

    /**
     * Create an abstract method for deleting enquiries 
     * @param user
     */
    public void handleDeleteEnquiry(User user) ;
}
