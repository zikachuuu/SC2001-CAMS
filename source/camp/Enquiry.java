package source.camp;

import source.exception.NoAccessException;
import source.user.Staff;
import source.user.Student;
import source.user.User;

/**
 * Represents an enquiry for a camp. An enquiry is part of a camp (Composition).
 * @author Le Yanzhi
 * @version beta 1
 * @since 2023-11-7
 */
public class Enquiry {

    /**
     * False denotes that this enquiry has been deleted.
     */
    private boolean active ;
    
    private Camp camp ;
    private Student student ;
    private String content ;

    private boolean replied ;
    private User repliedBy ;
    private String replies ;

    /**
     * Creates a new enquiry from a student.
     * @param camp The camp this enquiry is regarding.
     * @param student The student who submitted this enquiry.
     * @param content The content of the enquiry.
     */
    public Enquiry (Camp camp , Student student , String content) {
        this.active = true ;
        this.camp = camp ;
        this.student = student ;
        this.content = content ;
        this.replied = false ;
    }
    


    /**
     * Creates a enquiry from the database with all the information provided. 
     * Active is default to true as deleted enquries should not be inside the database.
     * @param camp The camp this enquiry is regarding.
     * @param student The student who submitted this enquiry.
     * @param content The content of the enquiry.
     * @param replied Whether it has been replied or not.
     * @param repliedBy The user (staff/committee) who replied to this enquiry.
     * @param replies The content of the replies.
     */
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
     * Check if the given student is the creator of this enquiry.
     * @param other The student to check.
     * @return True if student is creator, false otherwise.
     */
    public boolean isSubmittedBy(Student other) {
        return this.student.equals(other) ;
    }


    /**
     * Print out the content of this enquiry.
     */
    public void viewEnquiry() {
        System.out.println("Camp: " + camp.getCampInfo().getCampName()) ;
        System.out.println("Submitted by: " + student.getUserName());
        System.out.println("Enquiry: " + content);
        if (replied) {
            System.out.println ("Replied by: " + repliedBy.getUserName()) ;
            System.out.println("Reply: " + replies);
        }
    }


    /** 
     * Edit the content of the enquiry.
     * @param student The student who attempts to edit this enquiry.
     * @param newContent The new content of enquiry.
     * @return True if content is successfully updated, false if enquiry has already been deleted / answered.
     * @throws NoAccessException If student is not the author of this enquiry.
     */
    public boolean editEnquiry(Student student , String newContent) {

        if (! active || replied) return false ;
        if (! student.equals(this.student))  throw new NoAccessException("Only the author of the enquiry can edit his own enquiry!") ;

        content = newContent ;
        return true ;
    }


    
    /** 
     * Delete this enquiry by marking active as false. 
     * Application class should use this attribute to know which enquiry should be deleted from the database.
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
     * For use by replyEnquiry() of Student and Staff.
     * @param user The user who attempts to answer this enquiry.
     * @param replies The replies to this enquiry.
     * @return True if successfully replied, false if enquiry has already been deleted / answered.
     */
    private boolean replyEnquriy (User user , String replies) {
        if (! active || replied) return false ;
        
        this.replied = true ;
        this.repliedBy = user ;
        this.replies = replies ;
        return true ;
    }



    /** 
     * Reply this enquiry by a camp committee. The committee will receieve one point.
     * @param student The student who attempts to answer this enquiry.
     * @param replies The replies to this enquiry.
     * @return True if successfully replied, false if enquiry has already been deleted / answered.
     * @throws NoAccessException if student is not the camp committee of this camp.
     */
    public boolean replyEnquriy (Student student , String replies) {

        if (! student.getCampCommittee().getCamp().equals(camp)) throw new NoAccessException("Only Camp Committee are allowed to answer enquiry!") ;
        
        student.getCampCommittee().addPoint() ;
        return replyEnquriy((User)student, replies) ;
    }



    /** 
     * Reply this enquiry by a staff.
     * For now this do not check whether the staff is actually the creator of this camp. May be added in future versions.
     * @param staff The staff who attempts to answer this enquiry.
     * @param replies The replies to this enquiry.
     * @return True if successfully replied, false if enquiry has already been deleted / answered.
     */
    public boolean replyEnquriy (Staff staff , String replies) {
        return replyEnquriy((User)staff, replies) ;
    }

}

