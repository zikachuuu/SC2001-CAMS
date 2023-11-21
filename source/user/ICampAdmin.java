package source.user;

import java.time.LocalDate;

/**
 * Represents an interface to provide functionalities of a camp admin.
 * @author daryl tan
 * @version 1
 * @since 2023-11-19
 */
public interface ICampAdmin {

    /**
     * Create a new camp by the user
     * @param campName
     * @param startDate
     * @param endDate
     * @param registrationClosingDate
     * @param userGroup
     * @param location
     * @param totalSlots
     * @param campCommitteeSlots
     * @param description
     * @return True if successfully created, false if there is already a camp with the same camp name.
     */
    public boolean createCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) ;
    
    
    /**
     * Delete the camp.
     * @param campName
     */
    public void deleteCamp (String campName) ;
    
    
    /**
     * Edit the details of a camp. <p>
     * @param campName
     * @param startDate
     * @param endDate
     * @param registrationClosingDate
     * @param userGroup
     * @param location
     * @param totalSlots
     * @param campCommitteeSlots
     * @param description
     */
    public void editCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) ;
   
    
    /**
     * Display camp details to the command line interface.
     */
    public void viewCreatedCamps () ;
    
    
    /**
     * Toggle the visibility of the camp (visible <-> not visible).
     * @param campName
     * @return The new visibility of the camp.
     */
    public boolean toggleVisibility(String campName) ;
}
