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
    public boolean isVisible() {return visible ;}
    public boolean isActive () {return active ;}


    /**
     * Add a participant to the camp. This will increment the corresponding counter.
     * @param student The student to be added into the camp.
     * @param committeeRole True for camp committee, false for camp attendee.
     */
    public void addParticipant (Student student , boolean committeeRole) {
        
        participants.add(student) ;
        if (committeeRole) numCommittees++ ;
        else numAttendees++ ;
    }


    /**
     * Add a participant to the withdrawn list of the camp. <p>
     * Unlike withdrawParticipant(), this method neither remove the student from the participant list nor decrement the numAttendees counter.
     * @param student The attendee to be added into the withdrawn list.
     */
    public void addWithdrawnParticipant(Student student) {
        withdrawnParticipants.add(student) ;
    }


    /**
     * Withdraw an participant from the camp. <p>
     * Unlike addWithdrawnParticipant(), this method remove the student from the participant list and decrement the numAttendees counter.
     * @param attendee The attendee to withdraw.
     */
    public void withdrawParticipant (Student student) {

        participants.remove(student) ;
        withdrawnParticipants.add(student) ;
        numAttendees-- ;
    }


    /**
     * Add an enquiry to the camp.
     * @param enquiry
     */
    public void addEnquiry (Enquiry enquiry) {
        enquiries.add(enquiry) ;
    }


    /**
     * Add a suggestion to the camp.
     * @param suggestion
     */
    public void addSuggestion (Suggestion suggestion) {
        suggestions.add(suggestion) ;
    }


    /**
     * Print out the basic information of this camp, which can be done by any user.
     */
    public void viewCampInfo() {
        System.out.printf("Camp Name: %s \n", campInfo.getCampName());
        System.out.printf("Dates: %s to %s \n", campInfo.getStartDate().toString() , campInfo.getEndDate().toString());
        System.out.printf("Registration closing date: %s \n", campInfo.getRegistrationClosingDate().toString());
        System.out.printf("Opened to: %s \n", campInfo.getUserGroup().toString());
        System.out.printf("Location: %s \n", campInfo.getLocation());
        System.out.printf("Total slots: %d \n", campInfo.getTotalSlots());
        System.out.printf("Camp Committee Slots (max 10): %d \n", campInfo.getCampCommitteeSlots());
        System.out.printf("Description: %s \n", campInfo.getDescription());
        System.out.printf("Staff in charge: %s \n", campInfo.getStaffInCharge().getUserName());

    }


    /**
     * Print out the detailed information of this camp, which includes the current committee members and attendees. <p>
     * This can only be done by the commmittee of this camp or any staff.
     * @param user The user who attempts to view.
     * @throws NoAccessException If user does not have access to the information.
     */
    public void viewDetailedCampInfo(User user) {
        if (user instanceof Student)
        {
            Student student = (Student) user ;
            if(! student.isCampCommittee(this) ) throw new NoAccessException("Only committee member of this camp can view camp information!");
        }

        //print camp details
        viewCampInfo();
        System.out.println();
        System.out.println("Current number of committees: " + numCommittees);
        System.out.println("Current number of attendees: " + numAttendees);

        if (numCommittees != 0) {
            System.err.print("Current committee members: ") ;
            for (Student participant : participants) {
                if (participant.isCampCommittee(this)) System.out.print (participant.getUserName() + ", ") ;
            }
        }
        if (numAttendees != 0){
            System.err.print("Current atttendee members: ") ;
            for (Student participant : participants) {
                if (participant.isCampAttendee(this)) System.out.print (participant.getUserName() + ", ") ;
            }
        }
    }


    /**
     * Delete this camp by setting active as false. Inactive camp will not be written back to csv.
     * @param staffInCharge The staff who attempts to delete.
     * @throws NoAccessException If staff is not the owner of this camp, or if this camp has already been deleted.
     */
    public void deleteCamp(Staff staffInCharge) {
        if (! staffInCharge.equals(campInfo.getStaffInCharge())) throw new NoAccessException("Only the creator of this camp can toggle visibility!") ;
        if (! active) throw new NoAccessException("Camp has already been deleted!") ;

        active = false ;
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
     * Check if 2 camps are the same, using their campName.
     * @param other The camp to compare with.
     * @return True if same, false otherwise.
     */
    public boolean equals (Camp other) {
        return campInfo.getCampName().equals(other.getCampInfo().getCampName()) ;
    }   
    
    
    /**
     * Check if the camp has the provided campName.
     * @param campName The camp name to check.
     * @return True if same, false otherwise.
     */
    public boolean equals (String campName) {
        return campInfo.getCampName().equals(campName) ;
    }
}
