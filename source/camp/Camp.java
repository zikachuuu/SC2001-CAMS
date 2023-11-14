package source.camp;

import java.time.LocalDate;
import java.util.ArrayList ;

import source.exception.CampFullException;
import source.exception.DeadlineOverException;
import source.exception.NoAccessException;
import source.exception.WithdrawnException;
import source.user.CampAttendee;
import source.user.CampCommittee;
import source.user.Staff;
import source.user.Student;
import source.user.User;

/**
 * Represents a camp. A camp has students (association), enquries and suggestions (composition).
 * @author Le Yanzhi
 * @version beta 1 (Some methods have yet to be implemented)
 * @since 2023-11-10
 */
public class Camp {
    
    private CampInformation campInfo ;
    private int numCommittees ;
    private int numAttendees ;
    private ArrayList<Student> participants;
    private ArrayList<Student> withdrawnParticipants;
    private ArrayList<Enquiry> enquiries ;
    private ArrayList<Suggestion> suggestions ;

    private boolean visible ;
    private boolean active ;


    /**
     * Create a new default Camp with the provided camp information.
     * @param campInfo
     */
    public Camp(CampInformation campInfo) {
        this.campInfo = campInfo ;
        this.numCommittees = 0 ;
        this.numAttendees = 0 ;
        this.participants = new ArrayList<Student>() ;
        this.withdrawnParticipants = new ArrayList<Student>() ;
        this.enquiries = new ArrayList<Enquiry>() ;
        this.suggestions = new ArrayList<Suggestion>() ;
        this.visible = true ;
        this.active = true ;
    }

    
    /**
     * Create a Camp from the database with the provided data.
     * @param campInfo
     * @param numCommittees
     * @param numAttendees
     * @param enquiries
     * @param suggestions
     */
    public Camp (CampInformation campInfo , int numCommittees , int numAttendees, boolean visible) {
        this.campInfo = campInfo ;
        this.numCommittees = numCommittees ;
        this.numAttendees = numAttendees ;
        this.participants = new ArrayList<Student>() ;
        this.withdrawnParticipants = new ArrayList<Student>() ;
        this.enquiries = new ArrayList<Enquiry>() ;
        this.suggestions = new ArrayList<Suggestion>() ;        
        this.visible = visible ;
        this.active = true ;
    }


    public CampInformation getCampInfo() {return campInfo ;}
    public int getNumCommittees() {return numCommittees ;}
    public int getNumAttendees() {return numAttendees ;}
    public ArrayList<Student> getParticipants() {return participants ;}
    public ArrayList<Student> getWithdrawnParticipants() {return withdrawnParticipants ;}
    public ArrayList<Enquiry> getEnquiries() {return enquiries ;}
    public ArrayList<Suggestion> getSuggestions() {return suggestions ;}
    public boolean getVisible() {return visible ;}

    
    /**
     * @return True if camp is still active, false if camp has been deleted.
     */
    public boolean getActive () {return active ;}


    /**
     * Print out the information of this camp. This can only be done by the commmittee of this camp or any staff.
     * @param user The user who attempts to view.
     * @throws NoAccessException If user does not have access to the information.
     */
    public void viewCampDetails(User user) {
        if (user instanceof Student)
        {
            Student student = (Student) user ;
            if(! student.isCampCommittee(this) ) throw new NoAccessException("Only committee member of this camp can view camp information!");
        }

        //print camp details
        campInfo.printCampInfo();
        System.out.println();
        System.out.println("Current number of committees: " + numCommittees);
        System.out.println("Current number of attendees: " + numAttendees);
    }


    /**
     * Toggle the visibility of this camp (Not visible <-> Visible).
     * Visibility can be toggled iff no students have signed up for this camp yet.
     * @param staffInCharge The staff who attempts to toggle.
     * @return The new visibility of this camp.
     * @throws NoAccessException If staff is not the owner of this camp, or if students have already signed up for this camp. 
     */
    public boolean toggleVisibility(Staff staffInCharge) {

        if (! staffInCharge.equals(campInfo.getStaffInCharge())) throw new NoAccessException("Only the creator of this camp can toggle visibility!") ;
        if (numAttendees != 0 || numCommittees != 0) throw new NoAccessException("Cannot toggle visibility if students have already signed up for this camp!") ;

        return visible = ! visible ;
    }


    /**
     * Add a participant to the camp. This method is only called by CampManager.addParticipantToCamp().
     * @param student The student to be added into the camp.
     * @param committeeRole True for camp committee, false for camp attendee.
     */
    public void addParticipant (Student student , boolean committeeRole) {
        
        participants.add(student) ;
        if (committeeRole) numCommittees++ ;
        else numAttendees++ ;
    }


    /**
     * Withdraw an participant. This method is only called by CampManager.removeParticipantFromCamp()
     * @param attendee The attendee to withdraw.
     */
    public void withdrawParticipant (Student student) {

        participants.remove(student) ;
        withdrawnParticipants.add(student) ;
        numAttendees-- ;
    }


    /**
     * Add a enquiry. This method is only called by EnquiryManager.addEnquiryToCamp()
     * @param student
     * @param enquiry
     */
    public void addEnquiry (Student student, Enquiry enquiry) {
        enquiries.add(enquiry) ;
    }


    /**
     * Restore a student participant from csv. <p>
     * @param student
     * @param active False for withdrawn.
     */
    public void restoreParticipant (Student student, boolean active) {

        if (active) participants.add(student) ;
        else withdrawnParticipants.add(student) ;
    }


    /**
     * Restore an enquiry from csv.
     * @param enquiry
     */
    public void restoreEnquiry (Enquiry enquiry) {
        enquiries.add (enquiry) ;
    }


    /**
     * Restore an suggestion from csv.
     * @param suggestion
     */
    public void restoreSuggestion (Suggestion suggestion) {
        suggestions.add (suggestion) ;
    }


    /**
     * Check if 2 camps are the same, using their campName (campName is unique!).
     * @param other The camp to compare with.
     * @return True if same, false otherwise.
     */
    public boolean equals (Camp other) {
        return campInfo.getCampName() == other.getCampInfo().getCampName() ;
    }        
}
