package source.camp;

import java.time.LocalDate;
import java.util.ArrayList ;

import source.exception.CampFullException;
import source.exception.DeadlineOverException;
import source.exception.NoAccessException;
import source.exception.withdrawnException;
import source.user.CampAttendee;
import source.user.CampCommittee;
import source.user.Staff;
import source.user.Student;
import source.user.User;

/**
 * to be fixed...
 */
public class Camp {
    
    private CampInformation campInfo ;
    private int numCommittees ;
    private int numAttendees ;
    private ArrayList<Student> participants;
    private ArrayList<Student> withdrawnParticipants;
    private ArrayList<Enquiry> enquiries ;
    private ArrayList<Suggestion> suggestions ;


    /**
     * Create a new Camp with the provided camp information.
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
    }

    
    /**
     * Create a new Camp from the database with the provided data.
     * @param campInfo
     * @param numCommittees
     * @param numAttendees
     * @param enquiries
     * @param suggestions
     */
    public Camp (CampInformation campInfo , int numCommittees , int numAttendees , ArrayList<Student> participants , ArrayList<Student> withdrawnParticipants , ArrayList<Enquiry> enquiries , ArrayList<Suggestion> suggestions) {
        this.campInfo = campInfo ;
        this.numCommittees = numCommittees ;
        this.numAttendees = numAttendees ;
        this.participants = participants ;
        this.withdrawnParticipants = withdrawnParticipants ;
        this.enquiries = enquiries ;
        this.suggestions = suggestions ;
    }


    public CampInformation getCampInfo () {return campInfo ;}


    /**
     * Print out the information of this camp. This can only be done by the commmittee of this camp or any staff.
     * @param user The user who attempts to view.
     */
    public void viewCampDetails(User user) {
        //todo
        if ( user instanceof Student)
        {
            Student student = (Student) user ;
            if( ! student.isCampCommittee(this) ) throw new NoAccessException();
        }
        else // all staff can view details of camp  && camp committee member can also view details of his camp
        {
            //print camp details
        }
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

        return campInfo.toggleVisibility() ;
    }


    /**
     * Add a participant to the camp. This method is called by student.registerForCamp(), which have already checked: <p>
     * 1) student's faculty belongs to the user group of the camp <p>
     * 2) student has no committee role <p>
     * 3) student has no clash in dates (as well as not already signed up for this camp) <p>
     * This method will check: <p>
     * 1) registration deadline has not pass yet <p>
     * 2) camp still has slots left <p>
     * 3) student did not withdraw from this camp before <p>
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered. <p>
     * To register a student for a camp, one should only call student.registerForCamp(). This method should not be called standalone.
     * @param student The student to be added into the camp.
     * @param committeeRole True for camp committee, false for camp attendee.
     * @throws DeadlineOverException
     * @throws CampFullException
     * @throws withdrawnException If student has already withdrawn from the camp before.
     */
    public void addParticipant (Student student , boolean committeeRole) {
        if (LocalDate.now().isAfter(campInfo.getRegistrationClosingDate())) throw new DeadlineOverException() ;
        
        if (
            (committeeRole && numCommittees == campInfo.getCampCommitteeSlots()) ||
            (! committeeRole && numAttendees + numCommittees == campInfo.getTotalSlots())
        ) throw new CampFullException() ;

        if (withdrawnParticipants.contains(student)) throw new withdrawnException();

        participants.add(student) ;
        
        if (committeeRole) numCommittees++ ;
        else numAttendees++ ;
    }


    /**
     * Withdraw an participant. The student is removed from the participants list and added to the withdrawnParticipants list. <p>
     * This method is called by student.withdrawFromCamp(), which have already checked if student is actually a camp attendee. <p>
     * To withdraw a student from a camp, one should only call student.withdrawFromCamp(). This method should not be called standalone.
     * @param attendee The attendee to withdraw.
     */
    public void withdrawParticipant (Student student) {
        participants.remove(student) ;
        withdrawnParticipants.add(student) ;
        numAttendees-- ;
    }


    /**
     * Check if 2 camps are the same, using their campName (campName is unique!).
     * @param other The camp to compare with.
     * @return True if same, false otherwise.
     */
    public boolean equals (Camp other) {
        return this.getCampInfo().getCampName() == other.getCampInfo().getCampName() ;
    }
}
