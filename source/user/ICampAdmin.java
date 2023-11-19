package source.user;

import java.time.LocalDate;

/**
 * Represents an interface for handling the functionalities for ICampAdmin
 * @author daryl tan
 * @version 1
 * @since 2023-11-19
 */
public interface ICampAdmin {

    /**
     * Create a new camp by the staff.<p>
     * @param campName
     * @param startDate
     * @param endDate
     * @param registrationClosingDate
     * @param userGroup
     * @param location
     * @param totalSlots
     * @param campCommitteeSlots
     * @param description
     * Update the createdCamps list for successful creation.
     * Update the allCamps list for successful creation.
     * Update the csv file for successful creation.
     * @return True if successfully created, false if there is already a camp with the same camp name.
     * @throws ExceedMaximumException
     */
    public boolean createCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) ;
    /**
     * Delete the camp.
     * @param campName
     * @throws CampNotFoundException If camp name is not found or camp has already been deleted.
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
     * @throws ExceedMaximumException
     * @throws CampNotFoundException If no camp with camp name can be found under this staff.
     */
    public void editCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) ;
    /**
     * Iterate through all the camps in createdCamps list, and display camp details to the command line interface.
     */
    public void viewCreatedCamps () ;
    /**
     * Toggle the visibility of the camp (visible <-> not visible).
     * @param campName
     * @return The new visibility of the camp.
     * @throws CampNotFoundException If no active camp found for the given camp name.
     * @throws NoAccessException If students have already signed up for this camp.
     */
    public boolean toggleVisibility(String campName) ;
}
