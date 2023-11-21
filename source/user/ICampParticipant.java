package source.user;

/**
 * Represents an interface to provide functionalities of a camp participant.
 * @author Le Yanzhi
 * @version 1
 * @since 2023-11-19
 */
public interface ICampParticipant {

    /**
    * Print out a list of camps that the student can register (only camp information).<p>
    */
    public void viewOpenCamps() ;


    /**
    * Print out a list of registered camps (only camp information).
    */
    public void viewRegisteredCamps() ;


    /**
    * Register for a camp.
    * @param campName Name of the camp.
    * @param committeeRole True for committee, false for attendee.
    */
    public void registerForCamp (String campName , boolean committeeRole) ;

    
    /**
    * Withdraw from a camp.
    * @param campName Name of the camp.
    * @return True if sucessfully withdrawn, false if student is not attending the camp as attendee in the first place.
    */
    public boolean withdrawFromCamp (String campName) ;
}
