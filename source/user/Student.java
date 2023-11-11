package source.user;
import java.util.ArrayList ;

import source.application.Utility;
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


    /**
     * Check if student belongs to the camp user group.
     * @param camp
     * @return True if belongs, false otherwise.
     */
    private boolean checkFaculty(Camp camp) {
        return (camp.getCampInfo().getUserGroup() == Faculty.NTU) || (camp.getCampInfo().getUserGroup() == this.getFaculty()) ;
    }
    

    /**
     * Check is there is a clash in date betweeen the camp and the camps the student already signed up.
     * @param camp
     * @return True if there is no clash in date, false otherwise.
     */
    private boolean checkClashInDate(Camp camp) {
        if (campCommittee.getCamp().getCampInfo().getStartDate().isBefore(camp.getCampInfo().getEndDate()) &&
            campCommittee.getCamp().getCampInfo().getEndDate().isAfter(camp.getCampInfo().getStartDate())) return false ;

        for (CampAttendee attendee : campAttendees) {
            if (attendee.getCamp().getCampInfo().getStartDate().isBefore(camp.getCampInfo().getEndDate()) &&
            attendee.getCamp().getCampInfo().getEndDate().isAfter(camp.getCampInfo().getStartDate())) return false ;
        }

        return true ;
    }


    /**
     * Check if the student is already attending the given camp.
     * @param camp
     * @return True if student is attending the camp, false otherwise.
     */
    public boolean isAttendingCamp (Camp camp) {
        return isCampAttendee(camp) || isCampAttendee(camp) ;
    }


    /**
     * Check if the student is a camp committee of the given camp.
     * @param camp
     * @return True if student is a camp committee, false otherwise.
     */
    public boolean isCampCommittee (Camp camp) {
        if (campCommittee == null || ! campCommittee.getCamp().equals(camp)) return false ;
        return true ;
    }


    /**
     * Check if the student is a camp attendee of the given camp.
     * @param camp
     * @return True if student is a camp attendee, false otherwise.
     */
    public boolean isCampAttendee (Camp camp) {
        for (CampAttendee attendee : campAttendees) {
            if (attendee.getCamp().equals(camp)) return true ;
        }
        return false ;
    }


    public CampCommittee getCampCommittee() {return campCommittee ;}
    public ArrayList<CampAttendee> getCampAttendees() {return campAttendees ;}


    /**
     * Add a camp committee role to this student.
     * @param camp The camp to sign up as committee in.
     * @return True if role successfully taken, false if student already has a camp committee role.
     */
    private boolean addCampCommittee(Camp camp , int points) {
        if (this.campCommittee != null) return false ;
        this.campCommittee = new CampCommittee(camp, this , points) ;
        return true ;
    }


    /**
     * Add a camp attendee role to this student.
     * @param camp The camp to sign up as attendee in.
     */
    private void addCampAttendee (Camp camp) {
        this.campAttendees.add(new CampAttendee(camp, this)) ;
    }


    /**
     * Remove a camp attendee role from this student.
     * @param camp The camp to withdraw from.
     * @return True if successfully withdrawn, false if student is not a attendee of this camp.
     */
    private boolean removeCampAttendee (Camp camp) {
        for (CampAttendee attendee : campAttendees) {
            if (attendee.getCamp().equals(camp)) return campAttendees.remove(attendee) ;
        }
        return false ;
    }


    /**
     * (todo) Print out a list of open camps (valid user group and camp set to visible).
     */
    public void viewOpenCamps() {
        //todo
    }


    /**
     * Print out a list of registered camps (only camp names).
     */
    public void viewRegisteredCamps() {

        System.out.println("List of camps that you have registered for: ") ;
        System.out.println ("Registered as camp committee: ") ;
        if (campCommittee != null) {
            System.out.println(campCommittee.getCamp().getCampInfo().getCampName()) ;
        }
        else {
            System.out.println ("No camps registered as camp committee") ;
        }

        System.out.println ("Registered as camp attendee: ") ;
        if (campAttendees.size() > 0) {
            for (CampAttendee attendee : campAttendees) {
                System.out.println(attendee.getCamp().getCampInfo().getCampName()) ;
            }
        }
        else {
            System.out.println ("No camps registered as camp attendee") ; 
        }
    }


    /**
     * Register for a camp. This methods checks: <p>
     * 1) student's faculty belongs to the user group of the camp <p>
     * 2) student has no committee role <p>
     * 3) student has no clash in dates (as well as not already signed up for this camp) <p>
     * It then calls camp.addParticipant() to add the student inside the camp, which will check: <p>
     * 1) registration deadline has not pass yet <p>
     * 2) camp still has slots left <p>
     * 3) student did not withdraw from this camp before <p>
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered. <p>
     * Please remenber to catch all exceptions when calling it from CAMSApp.
     * 
     * @param campName Name of the camp.
     * @param committeeRole True for committee, false for attendee.
     * @throws CampNotFoundException Thrown by Utility.findCampByName()
     * @throws InvalidUserGroupException
     * @throws MultipleCommitteeRoleException
     * @throws DeadlineOverException Thrown by camp.addParticipant()
     * @throws CampFullException Thrown by camp.addParticipant()
     * @throws withdrawnException Thrown by camp.addParticipant()
     */
    public void registerForCamp (String campName , boolean committeeRole) {
        addCampRole(campName, committeeRole, 0);
    }

    public void addCampRole (String campName , boolean committeeRole , int points) {

        Camp camp = Utility.findCampByName(campName) ;

        if (! checkFaculty(camp)) throw new InvalidUserGroupException() ;
        if (committeeRole && this.campCommittee != null) throw new MultipleCommitteeRoleException() ;
        if (! checkClashInDate(camp)) throw new DateClashException() ;

        camp.addParticipant (this , committeeRole) ;

        if (committeeRole) addCampCommittee(camp , points) ;
        else addCampAttendee(camp); 
    }


    /**
     * Withdraw from a camp. This method checks: <p>
     * 1) student is actually an attendee of the camp. <p>
     * It then calls camp.withdrawParticipant() to add the student to the withdrawnParticipants list. <p>
     * @param campName
     * @return True if sucessfully withdrawn, false if student is not attending the camp as attendee in the first place.
     * @throws CampNotFoundException Thrown by Utility.findCampByName()
     */
    public boolean withdrawFromCamp (String campName) {
        Camp camp = Utility.findCampByName(campName) ;

        if (! isCampAttendee(camp)) return false ;

        removeCampAttendee(camp) ;
        camp.withdrawParticipant(this) ;

        return true ;
    }

    public void addRole () {

    }
}
