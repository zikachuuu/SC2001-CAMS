package source.ngui;

import source.user.User;

/**
 * Represents an interface for enquiry class 
 * @author daryl tan 
 * @version 1
 * @since 2023-11-19
 */
public interface IEnquiryAdminInterface {
    /**
     * Create a new handle for enquiries and viewing the replies to enquiries 
     * @param user
     */
    public void handleEnquiryViewReply(User user) ;
}
