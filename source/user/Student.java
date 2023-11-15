package source.user;
import java.util.ArrayList ;

import source.application.CampManager;
import source.application.EnquiryManager;
import source.camp.Camp;
import source.exception.* ;
import java.lang.String ;


/**
 * Represents a student. A student is a user (inheritance), and can have campCommittee and campAttendee roles (composition)
 * @author Le Yanzhi
 * @version beta 2 (Some methods have yet to be implemented; no methods here update the csv, csv update is done in main)
 * @since 2023-11-10
 */
public class Student extends User {

    private CampCommittee campCommittee ;
    private ArrayList<CampAttendee> campAttendees ;


    /**
     * Create a new default student.
     * @param userId
     * @param userName
     * @param faculty
     * @param password
     */
    public Student(String userId , String userName , Faculty faculty, String password) {
        super (userId , userName, faculty , password) ;

        this.campCommittee = null ;
        this.campAttendees = new ArrayList<CampAttendee>() ;
    }

    
    /**
     * Create a new student from the database using the provided information.
     * @param userId
     * @param userName
     * @param faculty
     * @param password
     * @param campCommittee
     * @param campAttendees
     */
    public Student (String userId , String userName , Faculty faculty , String password , CampCommittee campCommittee , ArrayList<CampAttendee> campAttendees) {
        this(userId, userName, faculty, password) ;

        this.campCommittee = campCommittee ;
        this.campAttendees = campAttendees ;
    }

    
    public CampCommittee getCampCommittee() {return campCommittee ;}
    public ArrayList<CampAttendee> getCampAttendees() {return campAttendees ;}


    /**
     * Check if the student is already attending the given camp.
     * @param camp
     * @return True if student is attending the camp, false otherwise.
     */
    public boolean isAttendingCamp (Camp camp) {
        return isCampAttendee(camp) || isCampCommittee(camp) ;
    }


    /**
     * Check if the student is a camp committee of some camp.
     * @return True if student is a camp committee, false otherwise.
     */
    public boolean isCampCommittee() {
        return campCommittee != null && campCommittee.getCamp().isActive() ;
    }


    /**
     * Check if the student is a camp committee of the given camp.
     * @param camp
     * @return True if student is a camp committee, false otherwise.
     */
    public boolean isCampCommittee (Camp camp) {
        if (campCommittee == null 
            || ! campCommittee.getCamp().equals(camp) 
            || ! campCommittee.getCamp().isActive()
        ) return false ;
        return true ;
    }


    /**
     * Check if the student is a camp attendee of the given camp.
     * @param camp
     * @return True if student is a camp attendee, false otherwise.
     */
    public boolean isCampAttendee (Camp camp) {
        for (CampAttendee attendee : campAttendees) {
            if (attendee.getCamp().equals(camp) && attendee.getCamp().isActive()) return true ;
        }
        return false ;
    }


    /**
     * Print out a list of camps that the student can register (only camp information).
     */
    public void viewOpenCamps() {
        CampManager.viewOpenCamps(this);
    }


    /**
     * Print out a list of registered camps (only camp information).
     */
    public void viewRegisteredCamps() {

        System.out.println("List of camps that you have registered for: ") ;

        if (campCommittee != null) {
            System.out.println ("Registered as camp committee: ") ;
            System.out.println();
            campCommittee.getCamp().viewCampInfo();
        }
        else {
            System.out.println ("No camps registered as camp committee") ;
        }
        System.out.println();

        if (campAttendees.size() > 0) {
            System.out.println ("Registered as camp attendee: ") ;
            System.out.println();
            for (CampAttendee attendee : campAttendees) {
                attendee.getCamp().viewCampInfo();
                System.out.println();
            }
        }
        else {
            System.out.println ("No camps registered as camp attendee") ; 
        }
    }


    /**
     * Register for a camp. This method calls CampManager.addParticipantToCamp() to do the actual registration. <p>
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered.
     * @param campName Name of the camp.
     * @param committeeRole True for committee, false for attendee.
     * @throws MultipleCommitteeRoleException
     * @throws CampNotFoundException 
     * @throws InvalidUserGroupException
     * @throws DateClashException
     * @throws DeadlineOverException 
     * @throws CampFullException 
     * @throws WithdrawnException 
     */
    public void registerForCamp (String campName , boolean committeeRole) {

        if (committeeRole && this.campCommittee != null) throw new MultipleCommitteeRoleException() ;
        CampManager.addParticipantToCamp(this, campName, committeeRole);
    }


    /**
     * Add a camp committee role to this student.
     * @param camp The camp to sign up as committee in.
     * @return True if role successfully taken, false if student already has a camp committee role.
     */
    public boolean addCampCommittee(Camp camp , int points) {
        if (this.campCommittee != null) return false ;
        this.campCommittee = new CampCommittee(camp, this , points) ;
        return true ;
    }


    /**
     * Add a camp attendee role to this student.
     * @param camp The camp to sign up as attendee in.
     */
    public void addCampAttendee (Camp camp) {
        this.campAttendees.add(new CampAttendee(camp, this)) ;
    }

    
    /**
     * Withdraw from a camp. This method calls CampManager.removeParticipantFromCamp() to do the actual withdrawal.<p>
     * @param campName Name of the camp.
     * @return True if sucessfully withdrawn, false if student is not attending the camp as attendee in the first place.
     * @throws CampNotFoundException Thrown by Utility.findCampByName()
     */
    public boolean withdrawFromCamp (String campName) { 
        return CampManager.removeParticipantFromCamp(this, campName) ;
    }


    /**
     * Remove a camp attendee role from this student.
     * @param camp The camp to withdraw from.
     * @return True if successfully withdrawn, false if student is not a attendee of this camp.
     */
    public boolean removeCampAttendee (Camp camp) {
        for (CampAttendee attendee : campAttendees) {
            if (attendee.getCamp().equals(camp)) return campAttendees.remove(attendee) ;
        }
        return false ;
    }


    /**
     * Submit a enquiry regarding a camp.
     * @param campName
     * @param Enquiry
     * @throws CampNotFoundException
     */
    public void submitEnquiry(String campName, String Enquiry) {
        EnquiryManager.addEnquiryToCamp(campName, Enquiry, this);
    }


    /**
     * Print out all the enquiries that this student have submitted.
     */
    public void viewSubmittedEnquiries() {
        EnquiryManager.viewEnquiry(this);
    }
}
