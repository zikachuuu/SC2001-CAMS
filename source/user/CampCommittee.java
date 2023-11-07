package source.user;

import source.camp.Camp;

/**
 * Represents a camp committee member of a certain camp. A camp committee is part of a student (composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class CampCommittee {

    private Camp camp ;
    private Student student ;
    private int points;

    /**
     * Creates a new default camp committee with 0 points.
     * @param camp The camp this committee belongs to.
     * @param student The student that take this committee role.
     */
    public CampCommittee(Camp camp , Student student) {
        this.camp = camp ;
        this.student = student ;
        this.points = 0 ;
    }



    /**
     * Creates a new camp committee from the database with the given points.
     * @param camp The camp this committee belongs to.
     * @param student The student that take this committee role.
     * @param points The number of points this committee have.
     */
    public CampCommittee (Camp camp , Student student , int points) {
        this (camp , student) ;
        this.points = points ;
    }


    
    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}
    public int getPoints() {return points ;}
    public void addPoint() {points++ ;}




    /**
     * Check if the 2 camp committees are the same person. 2 Camp committees are the same if they have the same student. 
     * @param other The other committee to compare with.
     * @return True if same, false otherwise.
     */
    public boolean equals (CampCommittee other) {
        return student.equals(other.getStudent()) ;
    }
}
