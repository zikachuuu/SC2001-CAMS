package source;
import java.util.ArrayList ;
import source.exception.* ;
import java.lang.String ;
import java.time.LocalDate; 

public class Student extends User {

    private CampCommittee campCommittee ;
    private ArrayList<CampAttendee> campAttendees ;

    public Student(String userId , String userName , String password , Faculty faculty) {
        super (userId , userName , password , faculty) ;

        this.campCommittee = null ;
        this.campAttendees = new ArrayList<CampAttendee>() ;
    }

    public void viewOpenCamps() {
        //todo
    }

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
            for (int i = 0 ; i < campAttendees.size() ; i++) {
                campAttendees[i].getCamp().viewCampDetails() ;
            }
        }
        else {
            System.out.println ("No camps registered as camp attendee") ;
        }
    }

    public boolean registerForCamp (String campName , boolean committeeRole) {
        if (committeeRole && campCommittee != null) throw new MultipleCommitteeRoleException() ;

        Camp camp = Utility.findCampByName(campName) ;

        if (camp.getFaculty() != Faculty.NTU || camp.getFaculty() != this.Faculty) throw new InvalidUserGroupException() ;

        if (LocalDate.now().isAfter(camp.getRegistrationClosingDate())) throw new DeadlineOverException() ;

        // to implement check clash in date

        if ((camp.getTotalSlots() - camp.getCampCommitteeSlots() == 0) || 
            (committeeRole && camp.getCampCommitteeSlots() == 0)
        ) throw new CampFullException() ;

        if (committeeRole) campCommittee = new CampCommittee (camp) ;
        else campAttendees.add(new CampAttendee (camp)) ;
    }

    public boolean withdrawFromCamp (String campName) {
        return true ;
    }
}
