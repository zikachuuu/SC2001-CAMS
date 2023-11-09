package source.camp;

import java.util.ArrayList ;

import source.exception.CampFullException;
import source.exception.NoAccessException;
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
     * Add a participant to the camp.
     * @param 
     * @return
     */
    public boolean addParticipant (Student student) {

        if (numCommittees == campInfo.getCampCommitteeSlots())  throw new CampFullException() ;
     
        return true ;
    }


    /**
     * Add an attendee to the camp.
     * @param attendee The attendee to add.
     * @return True if successfully added, false if attendee is already in camp or have withdrawn from camp before.
     * @throws CampFullException If camp is full.
     */
    public boolean addAttendee (Student student) {
        if (numAttendees + numCommittees == campInfo.getTotalSlots()) throw new CampFullException() ;
        if (campInfo.getWithdrawnAttendees().contains(student)) return false ; //fixed attribute
        if (/*campInfo.getAttendees().contains(attendee)*/ campInfo.getParticipant().contain(student)) return false ; //fixed attribute and method
        /*if (campInfo.getCommittees().*/

        //attendees.add(attendee) ;
        campInfo.addParticipants(student) // fixed method
        //totalSlots-- ;
        numAttendees++; //fixed attribute
        return true ;
    }



    /**
     * Withdraw an attendee. The attendee is removed from attendees list and appended to the withdrawAttendees list.
     * @param attendee The attendee to withdraw.
     * @return True if successfully withdraw, false if attendee is not in the camp.
     */
    public boolean withdrawAttendee (Student student) {
        // if ( /*attendees*/ campInfo.removeParticipants(attendee)) { //fixed method
        //     campInfo.addWithdrawnParticipants(attendee) ;
        //     //totalSlots++ ;
        //     numAttendees--; //fixed attribute
        //     return true ;
        // }
        return false ;
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
