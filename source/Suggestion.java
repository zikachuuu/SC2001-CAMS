package source;

import source.exception.NoAccessException;

/**
 * Represents a suggestion for a camp. A suggestion is part of a camp (Composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class Suggestion {

    /**
     * false denotes that this suggestion has been deleted
     */
    private boolean active;

    private Camp camp;
    private Student student;
    private String content;

    private boolean approved;

    public Suggestion (Camp camp , Student student , String content) {
        this.active = true ;
        this.camp = camp ;
        this.student = student ;
        this.content = content ;
        this.approved = false ;
    }

    public Suggestion (Camp camp , Student student , String content , boolean approved) {
        this (camp , student , content) ;
        this.approved = approved ;
    }

    public boolean getActive() {return active ;}
    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}
    public String getContent() {return content ;}
    public boolean getApproved() {return approved ;}



    /**
     * @param student  The camp student who attempts to edit this suggestion.
     * @param newContent The new content of suggestion.
     * @return true if content is successfully updated, false if suggestion has already been deleted / approved.
     * @throws NoAccessException if student is not the author of this suggestion.
     */
    public boolean editSuggestion (Student student , String newContent) {

        if (! active || approved) return false ;
        if (! student.equals(this.student)) throw new NoAccessException("Only the author of the suggestion can edit his own suggestion!") ;

        content = newContent ;
        return true ;
    }



    /**
     * @param student The camp student who attempts to delete this suggestion.
     * @return true if suggestion is successfully deleted, false if suggestion has already been deleted / approved.
     * @throws NoAccessException if student is not the author of this suggestion.
     */
    public boolean deleteSuggestion (Student student) {
        if (! active || approved) return false ;
        if (! student.equals(this.student)) throw new NoAccessException("Only the author of the suggestion can delete his own suggestion!") ;

        active = false ;
        return true ;
    }



    /**
     * @param staff The staff who attempts to approve this suggestion
     * @return true if suggestion is successfully approved, false if suggestion has already been deleted / approved.
     */
    public boolean approveSuggestion (Staff staff) {
        if (! active || approved) return false ;
        
        this.approved = true ;
        return true ;
    }
}