package source.user;

import java.time.LocalDate;
import java.util.ArrayList;

import source.application.Utility;
import source.camp.Camp;
import source.camp.CampInformation;

public class Staff extends User{

    private ArrayList<Camp> createdCamps ;
    
    
    public Staff (String userId, String userName, Faculty faculty, String password) {
        super (userId , userName , faculty, password) ;
        createdCamps = new ArrayList<Camp>() ;
    }


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
     * @return True if successfully created, false if there is already a camp with the same camp name.
     */
    public boolean createCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) {
        
        if (Utility.campExists(campName)) return false ;
        CampInformation campInfo = new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, this, true) ;
        createdCamps.add(new Camp(campInfo ,0 ,0));
        return true;
    }


    /**
     * (todo) Must also remove the references to this camp in each of the students objects.<p>
     * Iterate through the list of camps and get the name of the current camp accessed.
     * If the campName is the same as the one that user entered, remove the camp from createdCamps list.
     * If the camp is not found at all in the list of createdCamps, failure to delete.
     */
    public boolean deleteCamp (String campName) {
        for(int i = 0; i < createdCamps.size(); i++) {
            if(createdCamps.get(i).getCampInfo().getCampName() == campName) {
                createdCamps.remove(i);
                return true;
            }
        }
        return false;
    }


    /**
     * Iterate throught all the camps in createdCamps list, and display camp details to the command line interface.
     */
    public void viewCreatedCamps () {
        for(int i = 0; i < createdCamps.size(); i++) {
            createdCamps.get(i).viewCampDetails(this) ;
            System.out.println();
        }
    }

    
    /**
     * (Todo) View all camps (including those created by other staffs)
     */
    public void viewAllCamps() {

    }


    /**
     * Restore a previously created camp from csv. <p>
     * Use createCamp() instead when the staff want to create a new camp.
     * @param camp
     */
    public void restoreCreatedCamp (Camp camp) {
        createdCamps.add(camp) ;
    }
}
