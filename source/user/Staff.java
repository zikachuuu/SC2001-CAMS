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
}