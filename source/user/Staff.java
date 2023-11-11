import java.util.ArrayList;
package source.user;

import java.util.ArrayList;

import source.camp.Camp;
import source.camp.CampInformation;

public class Staff extends User{

    private ArrayList<Camp> createdCamps ;
    
    public Staff (String userId, String userName, Faculty faculty, String password) {
        super (userId , userName , faculty, password) ;
        createdCamps = new ArrayList<Camp>() ;
    }

    /**
     * Add a camp to the list of created camps. Used by readCampsFromFile() to convert csv into staff objects. <p>
     * Use createCamp() instead when the staff want to create a new camp.
     * @param camp
     */
    public void addCamp (Camp camp) {
        createdCamps.add(camp) ;
    }

    /**
     * Create a camp and append it to the createdCamp list.
     */
    public boolean createCamp (CampInformation campInfo) {
        createdCamps.add(new Camp(campInfo));
        return true;
    }

    /**
     * Iterate through the list of camps and get the name of the current camp accessed.
     * If the campName is the same as the one that user entered, remove the camp from createdCamps list.
     * If the camp is not found at all in the list of createdCamps, failure to delete.
     */
    public boolean deleteCamp (string campName) {
        for(int i = 0; i < createdCamps.size(); i++) {
            if(createdCamps.get(i).getCampInfo(this).getCampName() == campName) {
                createdCamps.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Iterate throught all the camps in createdCamps list, and display campName to the command line interface.
     */
    public void viewAllCamps () {
        for(int i = 0; i < createdCamps.size(); i++)
            System.out.println(createdCamps.get(i).getCampInfo(this).getCampName());
    }
}
