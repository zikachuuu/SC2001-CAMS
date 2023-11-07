package source.camp;

import java.time.LocalDate;
import java.util.ArrayList ;

import source.user.CampAttendee;
import source.user.CampCommittee;
import source.user.Faculty ;
import source.user.Staff;

/**
 * Represents the information regarding a camp. A camp information is part of a camp (composition).
 * No logic is implemented within this class. All this class does is to store the information and provide get and set methods.
 * Implement logic within camp class.
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
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
    private ArrayList<CampCommittee> committees ;
    private ArrayList<CampAttendee> attendees ;
    private ArrayList<CampAttendee> withdrawnAttendees ;


    public CampInformation (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description, Staff staffInCharge , boolean visibility , ArrayList<CampCommittee> committees , ArrayList<CampAttendee> attendees , ArrayList<CampAttendee> withdrawnAttendees) {

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
        this.committees = committees ;
        this.attendees = attendees ;
        this.withdrawnAttendees = withdrawnAttendees ;
    }

    public String getCampName() {return campName ;}
    public void setCampname(String campName) {this.campName = campName ;} 
    public LocalDate getStarDate() {return startDate ;}
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
    public void incrementTotalSlots() {totalSlots++ ;}
    public void decrementTotalSlots() {totalSlots-- ;}

    public int getCampCommitteeSlots() {return campCommitteeSlots ;}
    public void setCampCommitteeSlots(int campCommitteeSlots) {this.campCommitteeSlots = campCommitteeSlots ;}
    public void incrementCampCommitteeSlots() {campCommitteeSlots++ ;}
    public void decrementCampCommitteeSlots() {campCommitteeSlots-- ;} 

    public String getDescription() {return description ;}
    public void setDescription(String description) {this.description = description ;}

    public Staff getStaffInCharge() {return staffInCharge ;}
    public boolean getVisibility() {return visibility ;}
    public void setVisibility(boolean visibility) {this.visibility = visibility ;}

    public ArrayList<CampCommittee> getCommittees() {return committees ;}
    public void setCommittees(ArrayList<CampCommittee> committees) {this.committees = committees ;}
    public boolean addCommittee(CampCommittee committee) {return this.committees.add(committee) ;}
    public boolean removeCommittee(CampCommittee committee) {return this.committees.remove(committee) ;}

    public ArrayList<CampAttendee> getAttendees() {return attendees ;}
    public void setAttendees(ArrayList<CampAttendee> attendees) {this.attendees = attendees ;}
    public boolean addAttendee(CampAttendee attendee) {return this.attendees.add(attendee) ;}
    public boolean removeAttendee(CampAttendee attendee) {return this.attendees.remove(attendee) ;}
    
    public ArrayList<CampAttendee> getWithdrawnAttendees() {return withdrawnAttendees ;}
    public void setWithdrawnAttendees(ArrayList<CampAttendee> withdrawnAttendees) {this.withdrawnAttendees = withdrawnAttendees;}
    public boolean addWithdrawnAttendee(CampAttendee withdrawnAttendee) {return this.withdrawnAttendees.add(withdrawnAttendee) ;}
}
