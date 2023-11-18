package source.camp;

import java.time.LocalDate;

import source.exception.ExceedMaximumException;
import source.user.Faculty ;
import source.user.Staff;

/**
 * Represents the information regarding a camp. A camp information is part of a camp (composition).
 * No logic is implemented within this class. All this class does is to store the information and provide get and set methods.
 * Implement logic within camp class.
 * @author Le Yanzhi, Florian Goering
 * @version beta 5(Overwrote toString() method to support functionalities in Staff class)
 * @since 2023-11-15
 */
public class CampInformation {

    private String campName ;
    private LocalDate startDate ;
    private LocalDate endDate ;
    private LocalDate registrationClosingDate ;
    private Faculty userGroup ;
    private String location ;
    private int totalSlots ;
    private int campCommitteeSlots ;
    private String description ;
    private Staff staffInCharge ;


    /**
     * Create a new camp information object.
     * @param campName
     * @param startDate
     * @param endDate
     * @param registrationClosingDate
     * @param userGroup
     * @param location
     * @param totalSlots
     * @param campCommitteeSlots
     * @param description
     * @param staffInCharge
     * @throws ExceedMaximumException If camp committee slots is larger than 10 or total slots.
     */
    public CampInformation (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description, Staff staffInCharge) {

        if (campCommitteeSlots > 10 || campCommitteeSlots > totalSlots) throw new ExceedMaximumException() ;

        this.campName = campName ;
        this.startDate = startDate ;
        this.endDate = endDate ;
        this.registrationClosingDate = registrationClosingDate ;
        this.userGroup = userGroup ;
        this.location = location ;
        this.totalSlots = totalSlots ;
        this.campCommitteeSlots = campCommitteeSlots ;
        this.description = description ;
        this.staffInCharge = staffInCharge ;
    }

    public String getCampName() {return campName ;}
    public void setCampname(String campName) {this.campName = campName ;} 
    public LocalDate getStartDate() {return startDate ;}
    public void setStartState(LocalDate startDate) {this.startDate = startDate ;}
    public LocalDate getEndDate() {return endDate ;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate ;}
    public LocalDate getRegistrationClosingDate() {return registrationClosingDate ;}
    public void setRegistrationClosingDate(LocalDate registrationClosingDate) {this.registrationClosingDate = registrationClosingDate ;}
    public Faculty getUserGroup() {return userGroup ;}
    public void setUserGroup(Faculty userGroup) {this.userGroup = userGroup ;}
    public String getLocation() {return location ;}
    public void setLocation(String location) {this.location = location ;}
    public int getTotalSlots() {return totalSlots ;}
    public void setTotalSlots(int totalSlots) {this.totalSlots = totalSlots ;}
    public int getCampCommitteeSlots() {return campCommitteeSlots ;}
    public void setCampCommitteeSlots(int campCommitteeSlots) {this.campCommitteeSlots = campCommitteeSlots ;}
    public String getDescription() {return description ;}
    public void setDescription(String description) {this.description = description ;}
    public Staff getStaffInCharge() {return staffInCharge ;}
    public String toString() {
        return campName + " " + startDate + " " + endDate + " " + registrationClosingDate + " " +
                                    userGroup + " " + location + " " + totalSlots + " " + campCommitteeSlots + " " +
                                    description + " " + staffInCharge.getUserName();
    }
}
