package source;

/**
 * Represents a camp attendee of a certain camp. A camp attendee is part of a camp (Composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class CampAttendee {

    private Camp camp ;
    private Student student;

    public CampAttendee(Camp camp , Student student) {
        this.camp = camp ;
        this.student = student;
    }

    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}

    public boolean equals(CampAttendee other) {
        return camp.equals(other.getCamp()) && student.equals(other.getStudent()) ;
    }
}
