package source.ngui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import source.application.CAMSApp;
import source.application.EnquiryManager;
import source.application.Utility;
import source.camp.Camp;
import source.camp.Enquiry;
import source.user.Student;
import source.user.User;

/**
 * Represents a interface that contains abstract methods to be implemented in CampCommittee class 
 * @author daryl tan 
 * @version 1
 * @since 2023-11-19
 */
public class CommitteeInterface implements IReportInterface, IEnquiryAdminInterface, ISuggestionSubmitterInterface{
    /**
     * Create a new method for handling CampCommitteeFunctionalities
     * @param loggedInStudent
     * @param camp
     */
    public void handleCampCommiteeFunctionalities(Student loggedInStudent, Camp camp) {

        boolean innerMenu = true;
        while (innerMenu) {
            System.out.println("Viewing " + loggedInStudent.getUserName() + "'s camp comittee role.");
            System.out.println ("You currently have " + loggedInStudent.getCampCommittee().getPoints() + " points.") ;
            System.out.println("Press 1 to view details of the camp you've registered for");
            System.out.println("Press 2 to submit suggestions") ;
            System.out.println("Press 3 to view and reply to student enquiries");
            System.out.println("Press 4 to generate camp participant report");
            System.out.println("Press 5 to generate enquiries report");
            System.out.println("Press any other key to exit commitee menu");
            System.out.print ("Enter your choice: ") ;
            String choice = CAMSApp.scanner.nextLine();

            System.out.println();
            switch (choice) {
                case "1":
                    System.out.println("Viewing details of the camp you've registered for as a committee member\n");
                    camp.viewDetailedCampInfo(loggedInStudent);
                    System.out.println();
                    offerReturnToInnerMenuOption();
                    break;

                case "2":
                    handleSuggestionAdd(loggedInStudent);
                    offerReturnToInnerMenuOption();
                    break;

                case "3":
                    handleEnquiryViewReply(loggedInStudent) ;
                    offerReturnToInnerMenuOption();
                    break;

                case "4" :
                    generateParticipantsReport(loggedInStudent) ;
                    offerReturnToInnerMenuOption();

                case "5" :
                    generateEnquiryReport(loggedInStudent);
                    offerReturnToInnerMenuOption();

                default:
                    innerMenu = false;
                    break;
            }
            Utility.redirectingPage() ;
        }
    }

    /**
     * Create a method for handling addition of suggestions
     * @param user
     */
    public void handleSuggestionAdd (User user) {
        Student loggedInStudent = (Student) user ;

        System.out.print ("Enter the suggestion you would like to make: ") ;
        String suggestionContent = CAMSApp.scanner.nextLine() ;
        suggestionContent = Utility.replaceCommaWithSemicolon(suggestionContent) ;
        loggedInStudent.submitSuggestion(suggestionContent) ;
        System.out.println("Your suggestion has been successfully submitted!");
    }

    /**
     * Create a method for handling enquiries and viewing replies
     * @param loggedInUser
     */
    public void handleEnquiryViewReply(User loggedInUser) {
        Student loggedInStudent = (Student) loggedInUser ;
        Camp camp = loggedInStudent.getCampCommittee().getCamp() ;

        ArrayList<Enquiry> enquiries = camp.getEnquiries(true, true) ;

        if (enquiries.size() == 0) {
            System.out.println("There are currently no unanswered enquiries regarding this camp.");
            return ;
        }

        for (int i = 0 ; i < enquiries.size() ; i++) {
            System.out.println("Enquiry " + (i + 1) + ": ");
            System.out.println("Camp: " + enquiries.get(i).getCamp().getCampInfo().getCampName());
            System.out.println("Content: " + enquiries.get(i).getContent()) ;
            System.out.println();
        }

        System.out.print("Choose an enquiry that you wish to reply: ") ;
        String enquiryChoice = CAMSApp.scanner.nextLine() ;

        System.out.print ("Enter your reply here: ") ;
        String reply = CAMSApp.scanner.nextLine() ;
        reply = Utility.replaceCommaWithSemicolon(reply);

        try {
            enquiries.get(Integer.parseInt(enquiryChoice) - 1).replyEnquriy(loggedInStudent, reply) ;
            System.out.println("Enquiry has been successfully replied!") ;
        }
        catch (NumberFormatException | IndexOutOfBoundsException ee) {
            System.out.println("Invalid enquiry choice.");
        }
    }

    /**
     * Create a method for generating participants report 
     * @param loggedInUser
     */
    public void generateParticipantsReport(User loggedInUser) {

        Student loggedInStudent = (Student) loggedInUser ;
        Camp camp = loggedInStudent.getCampCommittee().getCamp() ;

        String filePath = "report//" + LocalDate.now() + "_for_camp_" + camp.getCampInfo().getCampName() + "_participantsReport.csv";
        File file = new File(filePath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("CampInfo: ");

            ArrayList<Student> currentCampParticipants = camp.getParticipants();
            writer.write(camp.getCampInfo().toString());
            writer.newLine();
            System.out.println("Select a way to generate the report");
            System.out.println("Press 1 for camp attendee only report.");
            System.out.println("Press 2 for camp committee only report.");
            System.out.println("Press 3 for report of all members.");
            System.out.print("Enter your choice: ");
            String filter = CAMSApp.scanner.nextLine();
            switch (filter) {
                case "1":
                    for(Student student : currentCampParticipants)
                        if (student.isCampAttendee(camp) && !student.isCampCommittee(camp))
                            writer.write(student.getUserId() + "," + "attendee" + '\n');
                    break;
                case "2":
                    for(Student student : currentCampParticipants)
                        if (student.isCampCommittee(camp))
                            writer.write(student.getUserId() + "," + "committee" + '\n');
                    break;
                case "3":
                    for(Student student : currentCampParticipants) {
                        if (student.isCampCommittee(camp))
                            writer.write(student.getUserId() + "," + "committee" + '\n');
                        else if (student.isCampAttendee(camp))
                            writer.write(student.getUserId() + "," + "attendee" + '\n');
                    }
                    break;
                default:
                    System.out.println("Invalid choice entered!");
                    return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Participant report has been successfully generated!");
    }

    /**
     * Create a method for generating enquiry report 
     * @param loggedInUser
     */
    public void generateEnquiryReport(User loggedInUser) {
        Student loggedInStudent = (Student) loggedInUser ;

        System.out.println("Press 1 to view enquiries that have not been replied.");
        System.out.println("Press 2 to view all enquiries including those that have been replied.");
        System.out.print("Enter your choice: ");
        String filter = CAMSApp.scanner.nextLine();
        if (!filter.equals("1") || !filter.equals("2")) {
            System.out.println("Invalid choice entered!");
            return;
        }

        ArrayList<Enquiry> enquiries = EnquiryManager.findAllEnquiry(loggedInStudent, filter == "1"?true:false);
        String filePath = "report//" + LocalDate.now() + "_enquiry_report.csv";
        File file = new File(filePath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("Enquiry Content, Active?, Replied?, Submitted by?, From which Camp?\r\n");
            for (Enquiry enquiry : enquiries) {
                writer.write(enquiry.getContent() + ", " +
                        enquiry.isActive() + ", " + enquiry.isReplied() + ", " +
                        enquiry.getStudent().getUserId() + ", " + enquiry.getCamp().getCampInfo().getCampName());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Enquiry report has been successfully generated!");
    }

    /**
     * Create a method for returning to menu option
     */
    private void offerReturnToInnerMenuOption() {
        System.out.print("Press any key to continue...");
        CAMSApp.scanner.nextLine();
    }

}
