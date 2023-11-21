package source.ngui;

import source.user.User;

/**
 * Represents an interface for generating reports
 * @author florian
 * @version 1
 * @since 2023-11-19
 */
public interface IReportInterface {
    /**
     * Creates a method for generating participants report 
     * @param user
     */
    public void generateParticipantsReport (User user) ;


    /**
     * Creates a method for generating enquiry report 
     * @param user
     */
    public void generateEnquiryReport (User user) ;
}
