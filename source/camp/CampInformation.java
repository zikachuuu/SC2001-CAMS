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
    
    /**
     * Name of the camp
     */
    private String campName ;
     
    /**
     * Start date of the camp
     */
    private LocalDate startDate ;
     
    /**
     * End date of the camp
     */
    private LocalDate endDate ;
     
    /**
     * registration closing date of this camp
     */
    private LocalDate registrationClosingDate ;
     
    /**
     * user group that this camp is opened to.
     */
    private Faculty userGroup ;
     
    /**
     * location the camp will be held at
     */
    private String location ;
      
    /**
     * total number of slots the camp has
     */
    private int totalSlots ;
     
    /**
     * number of camp committee slots the camps has
     */
    private int campCommitteeSlots ;
     
    /**
     * description of the camp
     */
    private String description ;
     
    /**
     * staff that created camp.
     */
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
    
    /**
     * retrieve campName from this camp.
     * @return campName
     */
    public String getCampName() {return campName ;}
 
    /**
     * set this camp's campName to campName
     * @param campName
     */
    public void setCampname(String campName) {this.campName = campName ;} 
        
    /**
     * retrieve the start date of this camp.
     * @return startDate
     */
    public LocalDate getStartDate() {return startDate ;}
        
    /**
     * set this camp's start date to startDate
     * @param startDate
     */
    public void setStartState(LocalDate startDate) {this.startDate = startDate ;}
        
    /**
     * retrieve the end date of this camp.
     * @return endDate
     */
    public LocalDate getEndDate() {return endDate ;}
        
    /**
     * set this camps's end date to endDate.
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {this.endDate = endDate ;}
        
    /**
     * retreive registration closing date of this camp.
     * @return registrationClosingDate
     */
    public LocalDate getRegistrationClosingDate() {return registrationClosingDate ;}
        
    /**
     * set the registration closing date of this camp to registationClosingDate
     * @param registrationClosingDate
     */
    public void setRegistrationClosingDate(LocalDate registrationClosingDate) {this.registrationClosingDate = registrationClosingDate ;}
        
    /**
     * retrieve user group data from this camp.
     * @return userGroup
     */
    public Faculty getUserGroup() {return userGroup ;}
        
    /**
     * set the user group of this camp to userGroup
     * @param userGroup
     */
    public void setUserGroup(Faculty userGroup) {this.userGroup = userGroup ;}
        
    /**
     * retrieve the location information of this camp
     * @return location
     */
    public String getLocation() {return location ;}
        
    /**
     * set the location of the camp into location
     * @param location
     */
    public void setLocation(String location) {this.location = location ;}
        
    /**
     * retrieve total slots info from this camp.,
     * @return totalSlots
     */
    public int getTotalSlots() {return totalSlots ;}
        
    /**
     * set total slots info for this camp into totalSlots
     * @param totalSlots
     */
    public void setTotalSlots(int totalSlots) {this.totalSlots = totalSlots ;}
        
    /**
     * retrieve Camp committee member slots in this camp.
     * @return campCommiitteeSlots
     */
    public int getCampCommitteeSlots() {return campCommitteeSlots ;}
        
    /**
     * set method for campCommitteeSlots.
     * @param campCommitteeSlots
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {this.campCommitteeSlots = campCommitteeSlots ;}
        
    /**
     * retrieve description from this camp.
     * @return description
     */
    public String getDescription() {return description ;}
        
    /**
     * set method for description
     * @param description
     */
    public void setDescription(String description) {this.description = description ;}
        
    /**
     * retrive the staff in charge for this camp.
     * @return staffInCharge
     */
    public Staff getStaffInCharge() {return staffInCharge ;}
        
    /**
     * Change the camp information attributes into strings that can be print out.
     * @return string of campInfo
     */
    public String toString() {
        return campName + " " + startDate + " " + endDate + " " + registrationClosingDate + " " +
                                    userGroup + " " + location + " " + totalSlots + " " + campCommitteeSlots + " " +
                                    description + " " + staffInCharge.getUserName();
    }
}
