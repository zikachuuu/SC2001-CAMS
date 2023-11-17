package source.user;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import source.application.CampManager;
import source.camp.Camp;
import source.camp.CampInformation;
import source.exception.CampNotFoundException;
import source.exception.NoAccessException;


/**
 * Represents a staff. A staff is a user (inheritance).
 * @author Florian Goering, Le Yanzhi
 * @version beta 3 (added in a new attribute allCamps, so that whenever viewAllCamps is called, no need to generate data from csv all the time)
 * Methods are created for csv updates.
 * @since 2023-11-15
 */
public class Staff extends User{

    private ArrayList<Camp> createdCamps;
    private static ArrayList<Camp> allCamps = new ArrayList<Camp>();


    /**
     * @param campName
     * Iterate through the allCamps list to find the camp with campName to remove.
     * @return the row to remove from the csv file
     */
    private int removeCampFromAllCamps(String campName) {
        int lineToRemove;
        for(lineToRemove = 0; lineToRemove < allCamps.size(); lineToRemove++)
            if (Objects.equals(allCamps.get(lineToRemove).getCampInfo().getCampName(), campName))
                allCamps.remove(lineToRemove);
        return lineToRemove;
    }

    /**
     * @param filePath
     * @param data
     * Appending the campInfo to the next row of csv file.
     */
    private void appendData(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filePath
     * @param rowToDelete
     * Delete the same row as allCamps.
     * Since allCamps have been updated, just update the csv accordingly with allCamps data.
     */
    private void deleteRow(String filePath, int rowToDelete) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Camp camp : allCamps) {
                writer.write(camp.getCampInfo().toString());
                writer.newLine();
            }

            writer.close();

            System.out.println("Row deleted successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
     * Update the createdCamps list for successful creation.
     * Update the allCamps list for successful creation.
     * Update the csv file for successful creation.
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
     * Iterate through all the camps in createdCamps list, and display camp details to the command line interface.
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
