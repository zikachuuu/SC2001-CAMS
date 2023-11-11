package source.camp;

import java.time.LocalDate;
import java.util.ArrayList ;

import source.exception.CampFullException;
import source.exception.DeadlineOverException;
import source.exception.NoAccessException;
import source.exception.withdrawnException;
import source.user.CampAttendee;
import source.user.CampCommittee;
import source.user.Staff;
import source.user.Student;
import source.user.User;

/**
 * Represents a camp. A camp has students (association), enquries and suggestions (composition).
 * @author Le Yanzhi
 * @version beta 1 (Some methods have yet to be implemented)
 * @since 2023-11-10
 */
public class Camp {
    
    private CampInformation campInfo ;
    private int numCommittees ;
    private int numAttendees ;
    private ArrayList<Student> participants;
    private ArrayList<Student> withdrawnParticipants;
    private ArrayList<Enquiry> enquiries ;
    private ArrayList<Suggestion> suggestions ;


    /**
     * Create a new Camp with the provided camp information.
     * @param campInfo
     * @param numCommittees
     * @param numAttendees
     */
    public Camp(CampInformation campInfo, int numCommittees , int numAttendees) {
        this.campInfo = campInfo ;
        this.numCommittees = numCommittees ;
        this.numAttendees = numAttendees ;
        this.participants = new ArrayList<Student>() ;
        this.withdrawnParticipants = new ArrayList<Student>() ;
        this.enquiries = new ArrayList<Enquiry>() ;
        this.suggestions = new ArrayList<Suggestion>() ;
    }

    
    /**
     * Create a new Camp from the database with the provided data.
     * @param campInfo
     * @param numCommittees
     * @param numAttendees
     * @param enquiries
     * @param suggestions
     */
    public Camp (CampInformation campInfo , int numCommittees , int numAttendees , ArrayList<Student> participants , ArrayList<Student> withdrawnParticipants , ArrayList<Enquiry> enquiries , ArrayList<Suggestion> suggestions) {
        this.campInfo = campInfo ;
        this.numCommittees = numCommittees ;
        this.numAttendees = numAttendees ;
        this.participants = participants ;
        this.withdrawnParticipants = withdrawnParticipants ;
        this.enquiries = enquiries ;
        this.suggestions = suggestions ;
    }


    public CampInformation getCampInfo () {return campInfo ;}


    /**
     * Print out the information of this camp. This can only be done by the commmittee of this camp or any staff.
     * @param user The user who attempts to view.
     */
    public void viewCampDetails(User user) {
        //todo
        if ( user instanceof Student)
        {
            Student student = (Student) user ;
            if( ! student.isCampCommittee(this) ) throw new NoAccessException();
        }
        else // all staff can view details of camp  && camp committee member can also view details of his camp
        {
            //print camp details
        }
    }


    /**
     * Toggle the visibility of this camp (Not visible <-> Visible).
     * Visibility can be toggled iff no students have signed up for this camp yet.
     * @param staffInCharge The staff who attempts to toggle.
     * @return The new visibility of this camp.
     * @throws NoAccessException If staff is not the owner of this camp, or if students have already signed up for this camp. 
     */
    public boolean toggleVisibility(Staff staffInCharge) {
        if (! staffInCharge.equals(campInfo.getStaffInCharge())) throw new NoAccessException("Only the creator of this camp can toggle visibility!") ;
        if (numAttendees != 0 || numCommittees != 0) throw new NoAccessException("Cannot toggle visibility if students have already signed up for this camp!") ;

        return campInfo.toggleVisibility() ;
    }


    /**
     * Add a participant to the camp. This method is called by student.registerForCamp(), which have already checked: <p>
     * 1) student's faculty belongs to the user group of the camp <p>
     * 2) student has no committee role <p>
     * 3) student has no clash in dates (as well as not already signed up for this camp) <p>
     * This method will check: <p>
     * 1) registration deadline has not pass yet <p>
     * 2) camp still has slots left <p>
     * 3) student did not withdraw from this camp before <p>
     * Corresponding exception will be thrown if there is any error. No exceptions means student is sucessfully registered. <p>
     * To register a student for a camp, one should only call student.registerForCamp(). This method should not be called standalone.
     * @param student The student to be added into the camp.
     * @param committeeRole True for camp committee, false for camp attendee.
     * @throws DeadlineOverException
     * @throws CampFullException
     * @throws withdrawnException If student has already withdrawn from the camp before.
     */
    public void addParticipant (Student student , boolean committeeRole) {
        if (LocalDate.now().isAfter(campInfo.getRegistrationClosingDate())) throw new DeadlineOverException() ;
        
        if (
            (committeeRole && numCommittees == campInfo.getCampCommitteeSlots()) ||
            (! committeeRole && numAttendees + numCommittees == campInfo.getTotalSlots())
        ) throw new CampFullException() ;

        if (withdrawnParticipants.contains(student)) throw new withdrawnException();

        participants.add(student) ;
        
        if (committeeRole) numCommittees++ ;
        else numAttendees++ ;
    }


    /**
     * Withdraw an participant. The student is removed from the participants list and added to the withdrawnParticipants list. <p>
     * This method is called by student.withdrawFromCamp(), which have already checked if student is actually a camp attendee. <p>
     * To withdraw a student from a camp, one should only call student.withdrawFromCamp(). This method should not be called standalone.
     * @param attendee The attendee to withdraw.
     */
    public void withdrawParticipant (Student student) {
        participants.remove(student) ;
        withdrawnParticipants.add(student) ;
        numAttendees-- ;
    }


    /**
     * Check if 2 camps are the same, using their campName (campName is unique!).
     * @param other The camp to compare with.
     * @return True if same, false otherwise.
     */
    public boolean equals (Camp other) {
        return this.getCampInfo().getCampName() == other.getCampInfo().getCampName() ;
    }

        // /* Submit Camp Suggestions */
        // private static void submitCampSuggestionsToStaff(Student user, List<CampClass> camps, List<Suggestion> suggestions) {
        //     Scanner scanner = new Scanner(System.in);
        //     viewActiveCommitteeCamps(user, camps);
        //     System.out.println("Enter the name of the camp you want to submit suggestions for:");
        //     String campNameToSuggest = scanner.nextLine();
        //     CampClass campToSuggest = findCampByName(campNameToSuggest, camps);

        //     if (campToSuggest != null) {
        //         System.out.println("Enter your suggestions for changes to the camp details:");
        //         String suggestionsText = scanner.nextLine();
        //         boolean approvalState = false;
        //         Suggestion suggestion = new Suggestion(user.getUserId(), campToSuggest.getCampName(), suggestionsText, approvalState);
        //         suggestions.add(suggestion);
                
        //         writeSuggestionToFile(suggestion);

        //         System.out.println("Suggestions submitted successfully!");
        //     } else {
        //         System.out.println("Camp not found or not eligible for suggestions.");
        //     }
        // }


        

        // /* Viewing Camp Suggestions */
        // private static void viewAllSuggestions(Staff user, List<Suggestion> suggestions, List<Camp> camps, List<CampRegistration> studentRegistrations) {
        //     Scanner scanner = new Scanner(System.in);
        //     System.out.println("Enter the name of the camp for which you want to view suggestions:");
        //     String targetCampName = scanner.nextLine();

        //     // Check if the logged-in staff is the staff in charge for the specified camp
        //     boolean isStaffInCharge = false;
        //     for (Camp camp : camps) {
        //         if (camp.getCampName().equalsIgnoreCase(targetCampName) && camp.getStaffInCharge().equals(loggedInStaff.getUserId())) {
        //             isStaffInCharge = true;
        //             break;
        //         }
        //     }

        //     if (isStaffInCharge) {
        //         System.out.println("Suggestions for camp: " + targetCampName);
        //         int count = 1;
        //         for (Suggestion suggestion : suggestions) {
        //             if (suggestion.getCampName().equalsIgnoreCase(targetCampName)) {
        //                 System.out.println(count);
        //                 System.out.println("Student ID: " + suggestion.getStudentId());
        //                 System.out.println("Suggestions: " + suggestion.getSuggestionsText());
        //                 System.out.println("-------------------------");
        //                 System.out.println();
        //             }
        //             count++;
        //         }
        //     } else {
        //         System.out.println("You are not the staff in charge of the camp or the camp does not exist.");
        //     }
        // }


        // /* Submitting camp Enquiry */
        // private static void submitCampEnquiry(Student user, List<Camp> camps) {
        //     Scanner scanner = new Scanner(System.in);
        //     List<Camp> eligibleCamps = getEligibleCamps(user, camps);
        //     listEligibleCamps(eligibleCamps);

        //     System.out.println("Enter the name of the camp you want to submit an enquiry for:");
        //     String campNameToEnquire = scanner.nextLine();
        //     Camp campToEnquire = findCampByName(campNameToEnquire, eligibleCamps);

        //     if (campToEnquire != null) {
        //         System.out.println("Enter your enquiry:");
        //         String enquiryText = scanner.nextLine();
        //         Enquiry enquiry = new EnquiriesClass(user.getUserId(), campToEnquire.getCampName(), enquiryText, false, null);
                
        //         writeEnquiriesToFile(enquiry);

        //         System.out.println("Enquiry submitted successfully!");
        //     } else {
        //         System.out.println("Camp not found or not eligible for enquiry.");
        //     }
        // }



        // /* Viewing camp enquiry */
        // private static void viewAllEnquiries(Staff user, List<Enquiry> enquiries, List<Camp> camps) {
        //     Scanner scanner = new Scanner(System.in);
        //     List<Camp> staffCamps = new ArrayList<>();
        //     for (Camp camp : camps) {
        //         if (camp.getStaffInCharge().equals(user.getUserId())) {
        //             staffCamps.add(camp);
        //         }
        //     }
        //     if (staffCamps.isEmpty()) {
        //         System.out.println("You haven't created any camps with enquiries.");
        //         return;
        //     }
        //     List<Enquiry> staffEnquiries = new ArrayList<>();
        //     for (Enquiry enquiry : enquiries) {
        //         for (Camp camp : staffCamps) {
        //             if (enquiry.getCampName().equalsIgnoreCase(camp.getCampName())) {
        //                 staffEnquiries.add(enquiry);
        //             }
        //         }
        //     }

        //     if (staffEnquiries.isEmpty()) {
        //         System.out.println("There are no enquiries associated with the camps you've created.");
        //         return;
        //     }

        //     for (int i = 0; i < staffEnquiries.size(); i++) {
        //         System.out.println((i + 1) + ". " + staffEnquiries.get(i).getEnquiriesText());
        //         System.out.println( "Enquiry for Camp: " + staffEnquiries.get(i).getCampName());
        //         System.out.println( "Asked by: " + staffEnquiries.get(i).getStudentId());
        //     }
        // }
        
}
