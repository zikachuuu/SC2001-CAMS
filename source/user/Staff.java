package source.user;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import source.application.Utility;
import source.camp.Camp;
import source.camp.CampInformation;

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
     */
    public boolean createCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) {
        
        if (Utility.campExists(campName)) return false ;
        CampInformation campInfo = new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, this) ;
        Camp newCamp = new Camp(campInfo);
        createdCamps.add(newCamp);
        allCamps.add(newCamp);
        String filePath = "data\\camps_list.csv";
        String data = newCamp.toString();
        appendData(filePath, data);
        return true;
    }


    /**
     * Iterate through the list of camps and get the name of the current camp accessed.
     * If the campName is the same as the one that user entered, remove the camp from createdCamps list.
     * If successfully deleted, update both allCamps and csv file camps_list.
     * If the camp is not found at all in the list of createdCamps, failure to delete.
     */
    public boolean deleteCamp (String campName) {
        for (int i = 0; i < createdCamps.size(); i++) {
            if (Objects.equals(createdCamps.get(i).getCampInfo().getCampName(), campName)) {
                createdCamps.remove(i);
                int rowToRemove  = removeCampFromAllCamps(campName);
                String filePath = "data\\camps_list.csv";
                deleteRow(filePath, rowToRemove);
                return true;
            }
        }
        return false;
    }

    /**
     * Iterate through all the camps in createdCamps list, and display camp details to the command line interface.
     */
    public void viewCreatedCamps () {
        for(Camp camp : allCamps)
            camp.getCampInfo().printCampInfo();
    }

    
    /**
     * Iterate through all the camps created by every staff.
     * Print out the camp information of every camp each in a line.
     */
    public void viewAllCamps() {
        for(Camp camp : allCamps)
            camp.getCampInfo().printCampInfo();
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
