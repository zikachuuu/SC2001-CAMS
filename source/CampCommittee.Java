package source;

/**
 * Represents a camp committee member of a certain camp. A camp committee is part of a student (composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class CampCommittee {

    private Camp camp;
    private Student student ;
    private int points;

    public CampCommittee(Camp camp , Student student) {
        this.camp = camp ;
        this.student = student ;
        this.points = 0 ;
    }

    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}
    public int getPoints() {return points ;}
    public void addPoint() {points++ ;}

    public boolean equals (CampCommittee other) {
        return student.equals(other.getStudent());
    }
}
