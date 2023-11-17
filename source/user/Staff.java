package source.user;

import java.time.LocalDate;
import java.util.ArrayList;

import source.application.CampManager;
import source.camp.Camp;
import source.camp.CampInformation;
import source.exception.CampNotFoundException;
import source.exception.NoAccessException;

public class Staff extends User{

    private ArrayList<Camp> createdCamps ;
    
    
    public Staff (String userId, String userName, Faculty faculty, String password) {
        super (userId , userName , faculty, password) ;
        createdCamps = new ArrayList<Camp>() ;
    }

    /**
     * the vanilla methods, returned array list includes deleted cammps as well
     * @return
     */
    public ArrayList<Camp> getCreatedCamps() {return createdCamps ;}

    public void addCreatedCamps(Camp camp) {createdCamps.add(camp) ;}


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
     * @throws ExceedMaximumException
     */
    public boolean createCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) {
        
        if (CampManager.campExists(campName)) return false ;
        CampInformation campInfo = new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, this) ;
        Camp camp = new Camp(campInfo) ;
        createdCamps.add(camp);
        CampManager.recordNewCamp(this, camp);
        return true;
    }


    /**
     * Delete the camp.
     * @param campName
     * @throws CampNotFoundException If camp name is not found or camp has already been deleted.
     */
    public void deleteCamp (String campName) {

        for (Camp camp : createdCamps) {
            if (camp.equals(campName) && camp.isActive()) {
                camp.deleteCamp(this);
                return ;
            }
        }
        throw new CampNotFoundException() ;
    }


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
    public void editCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) {
        for (Camp camp : createdCamps) {
            if (! camp.equals(campName)) continue ;

            CampInformation campInfo = new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, this) ;
            camp.setCampInfo(this, campInfo);
            return ;
        }   
        throw new CampNotFoundException() ;
    }


    /**
     * Iterate throught all the camps in createdCamps list, and display camp details to the command line interface.
     */
    public void viewCreatedCamps () {

        System.out.println("List of camps that you have created:\n");

        if (createdCamps.size() == 0) {
            System.out.println("You have yet to create any camps.");
            return ;
        }

        for(int i = 0; i < createdCamps.size(); i++) {
            createdCamps.get(i).viewDetailedCampInfo(this) ;
            System.out.println();
        }
    }

    
    /**
     * Print out a list of all active camps (including those created by other staffs)
     */
    public void viewAllCamps() {
        CampManager.viewAllCamps(this);
    }

    
    /**
     * Toggle the visibility of the camp (visible <-> not visible).
     * @param campName
     * @return The new visibility of the camp.
     * @throws CampNotFoundException If no active camp found for the given camp name.
     * @throws NoAccessException If students have already signed up for this camp.
     */
    public boolean toggleVisibility (String campName) {
        for (Camp camp : createdCamps) {
            if (! camp.equals(campName)) continue ;
            
            if (! camp.isActive()) throw new CampNotFoundException() ;
            return camp.toggleVisibility(this) ;
        }
        throw new CampNotFoundException() ;
    }
}
