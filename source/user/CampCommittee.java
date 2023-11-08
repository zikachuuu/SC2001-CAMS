package source.user;

import source.camp.Camp;

/**
 * Represents a camp committee member of a certain camp. A camp committee is part of a student (composition).
 * @author Le Yanzhi
 * @version beta 2
 * @since 2023-11-7
 */
public class CampCommittee extends CampRole {


    private int points;

    /**
     * Creates a new default camp committee with 0 points.
     * @param camp The camp this committee belongs to.
     * @param student The student that take this committee role.
     */
    public CampCommittee(Camp camp , Student student) {
        super(camp, student) ;
        this.points = 0 ;
    }



    /**
     * Creates a new camp committee from the database with the given points.
     * @param camp The camp this committee belongs to.
     * @param student The student that take this committee role.
     * @param points The number of points this committee have.
     */
    public CampCommittee (Camp camp , Student student , int points) {
        super(camp, student) ;
        this.points = points ;
    }


    public int getPoints() {return points ;}
    public void addPoint() {points++ ;}
}
