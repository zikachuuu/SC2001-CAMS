package source.user;

import source.camp.Camp;

/**
 * Represents a camp attendee of a certain camp. A camp attendee is part of a student (Composition).
 * @author Le Yanzhi
 * @version beta 2
 * @since 2023-11-7
 */
public class CampAttendee extends CampRole {
    
    /**
     * Creates a new camp attendee.
     * @param camp The camp this attendee belongs to.
     * @param student The student that take this attendee role.
     */
    public CampAttendee(Camp camp , Student student) {
        super(camp, student) ;
    }
}
