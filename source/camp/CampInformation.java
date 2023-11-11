package source.camp;

import java.time.LocalDate;

import source.user.Faculty ;
import source.user.Staff;

/**
 * Represents the information regarding a camp. A camp information is part of a camp (composition).
 * No logic is implemented within this class. All this class does is to store the information and provide get and set methods.
 * Implement logic within camp class.
 * @author Le Yanzhi
 * @version beta 3 (moved the arrayList of participants to camp instead, campInformation should only store "static" data)
 * @since 2023-11-10
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
    private boolean visibility ;


    public CampInformation (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description, Staff staffInCharge , boolean visibility) {

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
        this.visibility = visibility ;
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
    public boolean getVisibility() {return visibility ;}
    public void setVisibility(boolean visibility) {this.visibility = visibility ;}
    public boolean toggleVisibility() {return visibility = ! visibility ;}

    public void printCampInfo()
    {
        System.out.printf("Camp Name :  %s \n", campName);
        System.out.printf("Dates :  %s to %s \n ", startDate.toString() , endDate.toString());
        System.out.printf("Registration closing date: %s \n", registrationClosingDate.toString());
        System.out.printf("Opened to: %s \n", userGroup.toString());
        System.out.printf("Location: %s \n", location);
        System.out.printf("Total slots: %d \n", totalSlots);
        System.out.printf("Camp Committee Slots (max 10): %d \n", campCommitteeSlots);
        System.out.printf("Description: %s \n", description);
        System.out.printf("Staff in charge: %s \n", staffInCharge.getUserName);
    }
}
