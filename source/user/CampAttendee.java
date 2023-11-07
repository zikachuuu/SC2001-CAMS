package source.user;

import source.camp.Camp;

/**
 * Represents a camp attendee of a certain camp. A camp attendee is part of a student (Composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class CampAttendee {

    private Camp camp ;
    private Student student;

    /**
     * Creates a new camp attendee.
     * @param camp The camp this attendee belongs to.
     * @param student The student that take this attendee role.
     */
    public CampAttendee(Camp camp , Student student) {
        this.camp = camp ;
        this.student = student;
    }


    
    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}



    /**
     * Check if the 2 camp attendees are the same person. 2 Camp attendees are the same if they have the same student and camp. 
     * @param other The other attendee to compare with.
     * @return True if same, false otherwise.
     */
    public boolean equals(CampAttendee other) {
        return camp.equals(other.getCamp()) && student.equals(other.getStudent()) ;
    }
}
