package source.user;

import source.camp.Camp;

/**
 * Represents a camp role for a student, which can be either attendee or committee (ie both CampAttendee and CampCommittee inherit from this class).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-8
 */
public class CampRole {
    /**
     * The Camp this camp role belongs to.
     */
    private Camp camp ;

    /**
     * The student who took this role.
     */
    private Student student;

    /**
     * Creates a camp role.
     * @param camp The camp this role belongs to.
     * @param student The student that take this role.
     */
    public CampRole (Camp camp , Student student) {
        this.camp = camp ;
        this.student = student ;
    }

    /**
     * @return The camp this role belong to.
     */
    public Camp getCamp() {return camp ;}


    /**
     * @return The student this role belong to.
     */
    public Student getStudent() {return student ;}


    /**
     * Check if the 2 camp roles are taken by the same student
     * @return True if same, false otherwise.
     */
    public boolean sameStudent(CampRole other) {
        return this.student.equals(other.getStudent()) ;
    }
}
