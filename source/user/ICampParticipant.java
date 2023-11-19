package source.user;


public interface ICampParticipant {

    /**
    * Print out a list of camps that the student can register (only camp information).<p>
    * This method checks: <p>
    * 1) camp's user group is NTU or matches student's faculty.<p>
    * 2) camp's visibility is set to on.<p>
    * 3) camp is active (not deleted).
    * @param student The student who wish to view the open camps.
    */
    public void viewOpenCamps() ;

    /**
    * Print out a list of registered camps (only camp information).
    */
    public void viewRegisteredCamps() ;

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
    public void registerForCamp (String campName , boolean committeeRole) ;

    /**
    * Withdraw from a camp. This method calls CampManager.removeParticipantFromCamp() to do the actual withdrawal.<p>
    * @param campName Name of the camp.
    * @return True if sucessfully withdrawn, false if student is not attending the camp as attendee in the first place.
    * @throws CampNotFoundException Thrown by Utility.findCampByName()
    */
    public boolean withdrawFromCamp (String campName) ;
}
