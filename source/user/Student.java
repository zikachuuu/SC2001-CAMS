package source.user;
import java.util.ArrayList ;

import source.application.Utility;
import source.camp.Camp;
import source.exception.* ;
import java.lang.String ;
import java.time.LocalDate; 

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
     * Check if the given date is within the start date and end date
     * @param starDate
     * @param endDate
     * @param date
     * @return True if within, false otherwise.
     */
    private boolean withinDates (LocalDate starDate , LocalDate endDate , LocalDate date) {
        if (starDate.isBefore(date) && endDate.isAfter(date)) return true ;
        return false ;
    }


    /**
     * Check is there is a clash in date betweeen the camp and the camps the student already signed up.
     * @param camp
     * @return True if there is no clash in date, false otherwise.
     */
    private boolean checkClashInDate(Camp camp) {
        return ! (isCampAttendee(camp) || isCampCommittee(camp)) ;
    }


    /**
     * Check if the student is already attending the given camp.
     * @param camp
     * @return True if student is not attending the camp, false otherwise.
     */
    public boolean checkAttendingCamp (Camp camp) {
        return checkClashInDate(camp) ;
    }


    /**
     * Check if the student is a camp committee of the given camp.
     * @param camp
     * @return True if student is a camp committee, false otherwise.
     */
    public boolean isCampCommittee (Camp camp) {
        if (withinDates(campCommittee.getCamp().getCampInfo().getStartDate(), campCommittee.getCamp().getCampInfo().getEndDate(), camp.getCampInfo().getStartDate())) return false ;
        if (withinDates(campCommittee.getCamp().getCampInfo().getStartDate(), campCommittee.getCamp().getCampInfo().getEndDate(), camp.getCampInfo().getEndDate())) return false ;
        return true ;
    }


    /**
     * Check if the student is a camp attendee of the given camp.
     * @param camp
     * @return True if student is a camp attendee, false otherwise.
     */
    public boolean isCampAttendee (Camp camp) {
        for (CampAttendee attendee : campAttendees) {
            if (withinDates(attendee.getCamp().getCampInfo().getStartDate(), attendee.getCamp().getCampInfo().getEndDate() , camp.getCampInfo().getStartDate())) return false ;
            if (withinDates(attendee.getCamp().getCampInfo().getStartDate(), attendee.getCamp().getCampInfo().getEndDate() , camp.getCampInfo().getStartDate())) return false ;
        }
        return true ;
    }


    public CampCommittee getCampCommittee() {return campCommittee ;}
    public ArrayList<CampAttendee> getCampAttendees() {return campAttendees ;}


    /**
     * Add a camp committee role to this student.
     * @param camp The camp to sign up as committee in.
     * @return True if role successfully taken, false if student already has a camp committee role.
     */
    private boolean addCampCommittee(Camp camp) {
        if (this.campCommittee != null) return false ;
        this.campCommittee = new CampCommittee(camp, this) ;
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
     * Print out a list of open camps (valid user group and camp set to visible).
     */
    public void viewOpenCamps() {
        //todo
    }


    /**
     * Print out a list of registered camps.
     */
    public void viewRegisteredCamps() {

        System.out.println("List of camps that you have registered for: ") ;
        System.out.println ("Registered as camp committee: ") ;
        if (campCommittee != null) {
            campCommittee.getCamp().viewCampDetails() ;
        }
        else {
            System.out.println ("No camps registered as camp committee") ;
        }

        System.out.println ("Registered as camp attendee: ") ;
        if (campAttendees.size() > 0) {
            for (CampAttendee attendee : campAttendees) {
                attendee.getCamp().viewCampDetails() ;
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
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered. <p>
     * Please remenber to catch all exceptions when calling it from CAMSApp.
     * 
     * @param campName Name of the camp.
     * @param committeeRole True for committee, false for attendee.
     * @throws CampNotFoundException If camp name does not have any matching camp.
     * @throws InvalidUserGroupException
     * @throws MultipleCommitteeRoleException
     */
    public void registerForCamp (String campName , boolean committeeRole) {

        Camp camp = Utility.findCampByName(campName) ;

        if (! checkFaculty(camp)) throw new InvalidUserGroupException() ;
        if (committeeRole && this.campCommittee != null) throw new MultipleCommitteeRoleException() ;
        if (! checkClashInDate(camp)) throw new DateClashException() ;

        camp.addParticipant (this) ;

        if (committeeRole) addCampCommittee(camp) ;
        else addCampAttendee(camp); 

        // to update csv
    }

    // public boolean withdrawFromCamp (String campName) {
    //     // Camp camp = Utility.findCampByName(campName) ;

    //     // if (campCommittee.getCamp() == camp) throw new CommitteeWithdrawException() ;

    //     // for (int i = 0 ; i < campAttendees.size() ; i++) {
    //     //     if (campAttendees[i].getCamp == camp) {
    //     //         campAttendees.remove (i) ;
    //     //         // to update csv
    //     //         return true ;
    //     //     }
    //     // }
    //     // return false ;
    // }
}
