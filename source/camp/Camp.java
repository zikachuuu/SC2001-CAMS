package source.camp;

import java.util.ArrayList ;

import source.exception.CampFullException;
import source.user.CampAttendee;
import source.user.CampCommittee;
import source.user.Staff;
import source.user.User;

/**
 * to be fixed...
 */
public class Camp {
    
    CampInformation campInfo ;
    ArrayList<Enquiry> enquiries ;
    ArrayList<Suggestion> suggestions ;


    public Camp(CampInformation campInfo) {
        this.campInfo = campInfo ;
        this.enquiries = new ArrayList<Enquiry>() ;
        this.suggestions = new ArrayList<Suggestion>() ;
    }

    
    public Camp (CampInformation campInfo , ArrayList<Enquiry> enquiries , ArrayList<Suggestion> suggestions) {
        this.campInfo = campInfo ;
        this.enquiries = enquiries ;
        this.suggestions = suggestions ;
    }

    public void viewCampDetails(User user) {
        //todo
    }

    public boolean toggleVisibility(Staff staffInCharge) {
        //todo
        return true ;
    }


    /**
     * 
    */
    public boolean addCommittee (CampCommittee committee) {

        campInfo.addCommittee(committee) ;
        totalSlots-- ;
        campCommitteeSlots-- ;
        return true ;
    }


    /**
     * Add an attendee to the camp.
     * @param attendee The attendee to add.
     * @return True if successfully added, false if attendee is already in camp or have withdrawn from camp before.
     * @throws CampFullException If camp is full.
     */
    public boolean addAttendee (CampAttendee attendee) {
        if (totalSlots - campCommitteeSlots == 0) throw new CampFullException() ;
        if (withdrawAttendees.contains(attendee)) return false ;
        if (attendees.contains(attendee)) return false ;

        attendees.add(attendee) ;
        totalSlots-- ;
        return true ;
    }



    /**
     * Withdraw an attendee. The attendee is removed from attendees list and appended to the withdrawAttendees list.
     * @param attendee The attendee to withdraw.
     * @return True if successfully withdraw, false if attendee is not in the camp.
     */
    public boolean withdrawAttendee (CampAttendee attendee) {
        if (attendees.remove(attendee)) {
            withdrawAttendees.add(attendee) ;
            totalSlots++ ;
            return true ;
        }
        return false ;
    }
}