package source;

import source.exception.NoAccessException;

/**
 * Represents an enquiry for a camp. An enquiry is part of a camp (Composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class Enquiry {

    /**
     * false denotes that this enquiry has been deleted
     */
    private boolean active ;
    
    private Camp camp ;
    private Student student ;
    private String content ;

    private boolean replied ;
    private User repliedBy ;
    private String replies ;

    public Enquiry (Camp camp , Student student , String content) {
        this.active = true ;
        this.camp = camp ;
        this.student = student ;
        this.content = content ;
        this.replied = false ;
    }
    
    public Enquiry (Camp camp , Student student , String content , boolean replied , User repliedBy , String replies) {
        this (camp , student , content) ;
        this.replied = replied ;
        this.repliedBy = repliedBy ;
        this.replies = replies ;
    }

    public boolean getActive() {return active ;}
    public Camp getCamp() {return camp ;}
    public Student getStudent() {return student ;}
    public String getContent() {return content ;}
    public boolean getReplied() {return replied ;}
    public User getRepliedBy() {return repliedBy ;}
    public String getReplies() {return replies ;}


    /** 
     * @param student The student who attempts to edit this enquiry.
     * @param newContent The new content of enquiry.
     * @return true if content is successfully updated, false if enquiry has already been deleted / answered.
     * @throws NoAccessException if student is not the author of this enquiry.
     */
    public boolean editEnquiry(Student student , String newContent) {

        if (! active || replied) return false ;
        if (! student.equals(this.student))  throw new NoAccessException("Only the author of the enquiry can edit his own enquiry!") ;

        content = newContent ;
        return true ;
    }


    
    /** 
     * @param student The student who attempts to delete this enquiry.
     * @return true if enquiry is successfully deleted, false if enquiry has already been deleted / answered.
     * @throws NoAccessException if student is not the author of this enquiry.
     */
    public boolean deleteEnquiry(Student student) {

        if (! active || replied) return false ;
        if (! student.equals(this.student)) throw new NoAccessException("Only the author of the enquiry can delete his own enquiry!") ;

        active = false ;
        return true ;
    }


    /**
     * For use by replyEnquiry of Student and Staff.
     * @param user The user who attempts to answer this enquiry.
     * @param replies The replies to this enquiry.
     * @return true if successfully replied, false if enquiry has already been deleted / answered.
     */
    private boolean replyEnquriy (User user , String replies) {
        if (! active || replied) return false ;
        
        this.replied = true ;
        this.repliedBy = user ;
        this.replies = replies ;
        return true ;
    }



    /** 
     * @param student The student who attempts to answer this enquiry.
     * @param replies The replies to this enquiry.
     * @return true if successfully replied, false if enquiry has already been deleted / answered.
     * @throws NoAccessException if student is not the camp committee of this camp.
     */
    public boolean replyEnquriy (Student student , String replies) {

        if (! student.getCampCommittee().getCamp().equals(camp)) throw new NoAccessException("Only Camp Committee are allowed to answer enquiry!") ;
        
        student.getCampCommittee().addPoint() ;
        return replyEnquriy((User)student, replies) ;
    }



    /** 
     * @param staff The staff who attempts to answer this enquiry.
     * @param replies The replies to this enquiry.
     * @return true if successfully replied, false if enquiry has already been deleted / answered.
     */
    public boolean replyEnquriy (Staff staff , String replies) {
        return replyEnquriy((User)staff, replies) ;
    }

}

