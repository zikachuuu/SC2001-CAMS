package source.application;

import java.time.LocalDate;

import source.camp.Camp;
import source.exception.CampFullException;
import source.exception.CampNotFoundException;
import source.exception.DateClashException;
import source.exception.DeadlineOverException;
import source.exception.InvalidUserGroupException;
import source.exception.WithdrawnException;
import source.user.CampAttendee;
import source.user.Faculty;
import source.user.Student;


/**
 * One not very oop thing in previous versions is how easy it was for student objects to access camp objects, by simply using the Utility.getCampFromName() method.
 * CampsManager intends to fix that by serving as a proxy between students and camps; students only know the camp name and need to go through here to actually access the camp objects.
 */
public class CampManager {

    /**
     * Check if student belongs to the camp user group.
     * @param student
     * @param camp
     * @return True if belongs, false otherwise.
     */
    private static boolean checkFaculty(Student student, Camp camp) {
        return (camp.getCampInfo().getUserGroup() == Faculty.NTU) || (camp.getCampInfo().getUserGroup() == student.getFaculty()) ;
    }
    

    /**
     * Check is there is a clash in date betweeen the camp and the camps the student already signed up.
     * @param student
     * @param camp
     * @return True if there is clash in date, false otherwise.
     */
    private static boolean checkClashInDate(Student student, Camp camp) {

        if (student.getCampCommittee() != null) {
            if (student.getCampCommittee().getCamp().getCampInfo().getStartDate().isBefore(camp.getCampInfo().getEndDate()) &&
                student.getCampCommittee().getCamp().getCampInfo().getEndDate().isAfter(camp.getCampInfo().getStartDate())) return true ;
        }

        if (student.getCampAttendees() != null){
            for (CampAttendee attendee : student.getCampAttendees()) {
                if (attendee.getCamp().getCampInfo().getStartDate().isBefore(camp.getCampInfo().getEndDate()) &&
                attendee.getCamp().getCampInfo().getEndDate().isAfter(camp.getCampInfo().getStartDate())) return true ;
            }
        }

        return false ;
    }


    /**
     * Check if the deadline for camp registration has passed.
     * @param camp
     * @return True if has passed, false otherwise.
     */
    private static boolean checkDeadlinePassed(Camp camp) {
        return LocalDate.now().isAfter(camp.getCampInfo().getRegistrationClosingDate()) ;
    }


    /**
     * Check if camp is full for the given role.
     * @param camp
     * @param committeeRole True for comittee, false for attendee.
     * @return True if full, false otherwise.
     */
    private static boolean checkCampFull (Camp camp, boolean committeeRole) {
        if (
            (committeeRole && camp.getNumCommittees() == camp.getCampInfo().getCampCommitteeSlots()) ||
            (! committeeRole && camp.getNumAttendees() + camp.getNumCommittees() == camp.getCampInfo().getTotalSlots())
        ) return true ;

        return false ;
    }


    /**
     * Check if student has withdrawn from the camp before.
     * @param student
     * @param camp
     * @return True if student have withdrawn before, false otherwise.
     */
    private static boolean checkWithdrawnStudent (Student student, Camp camp) {
        if (camp.getWithdrawnParticipants().contains(student)) return true ;
        return false ;
    }


    /**
     * Add student to a camp, and create the corresponding role under student. <p>
     * This method checks: <p>
     * 1) student's faculty belongs to the user group of the camp <p>
     * 2) student has no clash in dates (as well as not already signed up for this camp) <p>
     * 3) registration deadline has not pass yet <p>
     * 4) camp still has slots left <p>
     * 5) student did not withdraw from this camp before <p>
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered. <p>
     * @param campName Name of the camp.
     * @param committeeRole True for committee, false for attendee.
     * @throws CampNotFoundException 
     * @throws InvalidUserGroupException
     * @throws DateClashException
     * @throws DeadlineOverException 
     * @throws CampFullException 
     * @throws WithdrawnException 
     */
    public static void addParticipantToCamp (Student student , String campName, boolean committeeRole) {
        Camp camp = Utility.findCampByName(campName);

        if (! checkFaculty(student, camp)) throw new InvalidUserGroupException() ;
        if (checkClashInDate(student, camp)) throw new DateClashException();
        if (checkDeadlinePassed(camp)) throw new DeadlineOverException() ;
        if (checkCampFull(camp, committeeRole)) throw new CampFullException() ;
        if (checkWithdrawnStudent(student, camp)) throw new WithdrawnException();

        camp.addParticipant(student, committeeRole);
        if (committeeRole) student.addCampCommittee(camp , 0) ;
        else student.addCampAttendee(camp); 
    }


    /**
     * Withdraw student from a camp, and remove the corresponding role under student.<p>
     * This method checks if student is actually an attendee of the camp. <p>
     * @param campName
     * @return True if sucessfully withdrawn, false if student is not attending the camp as attendee in the first place.
     * @throws CampNotFoundException
     */

    public static boolean removeParticipantFromCamp(Student student, String campName) {
        Camp camp = Utility.findCampByName(campName);

        if (! student.isCampAttendee(camp)) return false ;
        camp.withdrawParticipant(student);
        student.removeCampAttendee(camp) ;

        return true ;
    }


    /**
     * Print out a list of camps that the student can register (only camp information).<p>
     * This method checks: <p>
     * 1) camp's user group is NTU or matches student's faculty.<p>
     * 2) camp's visibility is set to on.<p>
     * 3) camp is active (not deleted).
     * @param student
     */
    public static void viewOpenCamps(Student student) {
        System.out.println("List of camps that are open to you:\n");
        boolean have = false ;

        for (Camp camp : CAMSApp.camps) {
            if (camp.getActive() && checkFaculty(student, camp) && camp.getVisible()) {
                camp.getCampInfo().printCampInfo();
                System.out.println();
                have = true ;
            }
        }

        if (! have){
            System.out.println("Sorry, no camps are open to you.");
        }
    }
}
