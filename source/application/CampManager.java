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
import source.user.Staff;
import source.user.Student;


/**
 * One not very oop thing in previous versions is how easy it was for student objects to access camp objects, by simply using the Utility.getCampFromName() method.
 * CampsManager intends to fix that by serving as a proxy between students and camps; students only know the camp name and need to go through here to actually access the camp objects.
 */
public class CampManager {

    /**
     * Find the camp object using the camp name provided.
     * @param campName The name of the camp.
     * @return Camp object.
     * @throws CampNotFoundException If camp not found for the provided name (for example camp is deleted).
     */
    protected static Camp findCampByName(String campName) {

        for (Camp camp : CAMSApp.camps) {
            if (camp.getCampInfo().getCampName().equals(campName) && camp.isActive())
                return camp;
        }
        throw new CampNotFoundException("Camp not found for " + campName);
    }


    /**
     * Check if the camp with the provided name already exists.
     * @param campName The name of the camp.
     * @return True if camp already exists, false otherwise.
     */
    public static boolean campExists(String campName) {
        try {
            findCampByName(campName);
            return true;
        } catch (CampNotFoundException e) {
            return false;
        }
    }


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

        if (student.isCampCommittee()) {
            if (student.getCampCommittee().getCamp().getCampInfo().getStartDate().isBefore(camp.getCampInfo().getEndDate()) &&
                student.getCampCommittee().getCamp().getCampInfo().getEndDate().isAfter(camp.getCampInfo().getStartDate())) return true ;
        }

        if (student.isCampAttendee()){
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
     * 1) camp is set to visible <p>
     * 2) student's faculty belongs to the user group of the camp <p>
     * 3) student has no clash in dates (as well as not already signed up for this camp) <p>
     * 4) registration deadline has not pass yet <p>
     * 5) camp still has slots left <p>
     * 6) student did not withdraw from this camp before <p>
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered. <p>
     * @param campName Name of the camp.
     * @param committeeRole True for committee, false for attendee.
     * @throws CampNotFoundException If camp name provided is invalid, camp is deleted, or camp is set to unvisible.
     * @throws InvalidUserGroupException
     * @throws DateClashException
     * @throws DeadlineOverException 
     * @throws CampFullException 
     * @throws WithdrawnException 
     */
    public static void addParticipantToCamp (Student student , String campName, boolean committeeRole) {
        Camp camp = findCampByName(campName);

        if (! camp.isVisible()) throw new CampNotFoundException() ;
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
        Camp camp = findCampByName(campName);

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
     * @param student The student who wish to view the open camps.
     */
    public static void viewOpenCamps(Student student) {
        System.out.println("List of camps that are open to you:\n");
        boolean have = false ;

        for (Camp camp : CAMSApp.camps) {
            if (camp.isActive() && checkFaculty(student, camp) && camp.isVisible()) {
                camp.viewCampInfo() ;
                System.out.println();
                have = true ;
            }
        }

        if (! have){
            System.out.println("Sorry, no camps are open to you.");
        }
    }

    
    /**
     * Print out a list of all camps (detailed camp info).
     * @param staff The staff who wish to view all the camps.
     */
    public static void viewAllCamps(Staff staff) {
        System.out.println("List of all camps avaliable:\n");
        boolean have = false ;

        for (Camp camp : CAMSApp.camps) {
            if (camp.isActive()) {
                camp.viewDetailedCampInfo(staff);
                System.out.println();
                have = true ;
            }
        }

        if (! have){
            System.out.println("Sorry, no camps are avaliable yet.");
        }
    }

    
    /**
     * After a staff create a new camp, call this method to record the camp in the static camps ArrayList
     * @param staff The staff who wish to record the camp after creating it.
     * @param camp The newly created camp to record.
     */
    public static void recordNewCamp(Staff staff, Camp camp) {
        CAMSApp.camps.add(camp) ;
    }   
}
