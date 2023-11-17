package source.camp;

import source.exception.NoAccessException;
import source.user.Staff;
import source.user.Student;

/**
 * Represents a suggestion for a camp. A suggestion is part of a camp (Composition).
 * For now it stores the student itself. Maybe in future versions this can be changed to camp committee instead.
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class Suggestion {

    /**
     * False denotes that this suggestion has been deleted.
     */
    private boolean active;

    private Camp camp;
    private Student student;
    private String content;
    private boolean approved;


    /**
     * Creates a new suggestion from the camp committee.
     * @param camp The camp this suggestion is regarding.
     * @param student The student who submitted this suggesstion.
     * @param content The content of this suggestion.
     */
    public Suggestion (Camp camp , Student student , String content) {
        this.active = true ;
        this.camp = camp ;
        this.student = student ;
        this.content = content ;
        this.approved = false ;
    }


    /**
     * Creates a suggestion from the database with all the information provided.
     * Active is default to true as deleted suggestions should not be inside the database.
     * @param camp  The camp this suggestion is regarding.
     * @param student The student who submitted this suggesstion.
     * @param content The content of this suggestion.
     * @param approved Whether it is approved or not.
     */
    public Suggestion (Camp camp , Student student , String content , boolean approved) {
        this (camp , student , content) ;
        this.approved = approved ;
    }


    public boolean isActive() {return active ;}
    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}
    public String getContent() {return content ;}
    public boolean isApproved() {return approved ;}


    /**
     * Edit the content of this suggestion.
     * @param student  The student who attempts to edit this suggestion.
     * @param newContent The new content of suggestion.
     * @return True if content is successfully updated, false if suggestion has already been deleted / approved.
     * @throws NoAccessException If student is not the author of this suggestion.
     */
    public boolean editSuggestion (Student student , String newContent) {

        if (! active || approved) return false ;
        if (! student.equals(this.student)) throw new NoAccessException("Only the author of the suggestion can edit his own suggestion!") ;

        content = newContent ;
        return true ;
    }


    /**
     * Delete this suggestion by marking active as false. 
     * Application class should use this attribute to know which suggestion should be deleted from the database.
     * @param student The student who attempts to delete this suggestion.
     * @return True if suggestion is successfully deleted, false if suggestion has already been deleted / approved.
     * @throws NoAccessException If student is not the author of this suggestion.
     */
    public boolean deleteSuggestion (Student student) {
        if (! active || approved) return false ;
        if (! student.equals(this.student)) throw new NoAccessException("Only the author of the suggestion can delete his own suggestion!") ;

        active = false ;
        return true ;
    }


    /**
     * Approve this suggestion by the staff who created the camp.
     * @param staff The staff who attempts to approve this suggestion.
     * @return True if suggestion is successfully approved, false if suggestion has already been deleted / approved.
     * @throws NoAccessException
     */
    public boolean approveSuggestion (Staff staff) {
        if (! camp.getCampInfo().getStaffInCharge().equals(staff)) throw new NoAccessException() ;

        if (! active || approved) return false ;
        this.approved = true ;
        return true ;
    }
}