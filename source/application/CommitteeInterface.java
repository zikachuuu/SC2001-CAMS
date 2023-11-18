package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.camp.Enquiry;
import source.user.Student;

public class CommitteeInterface extends UserInterface {

    protected static void handleCampCommiteeFunctionalities(Student loggedInStudent, Camp camp) {

        boolean innerMenu = true;
        while (innerMenu) {
            System.out.println("Viewing " + loggedInStudent.getUserName() + "'s camp comittee role.");
            System.out.println ("You currently have " + loggedInStudent.getCampCommittee().getPoints() + " points.") ;
            System.out.println("Press 1 to view details of the camp you've registered for");
            System.out.println("Press 2 to submit suggestions") ;
            System.out.println("Press 3 to view and reply to student enquiries");
            System.out.println("(todo) Press 4 to generate reports");
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
                    handleSuggestionAdd(loggedInStudent, camp);
                    offerReturnToInnerMenuOption();
                    break;

                case "3":
                    handleEnquiryViewReply(loggedInStudent , camp) ;
                    offerReturnToInnerMenuOption();
                    break;

                case "4":
                    System.out.println("Press 1 to generate committee members report");
                    System.out.println("Press 2 to generate attendee members report");
                    System.out.println("Press 3 to generate student's enquiry report") ;
                    System.out.println("Press any other key to return to the main menu");
                    String reportChoice = CAMSApp.scanner.nextLine();
                    switch (reportChoice) {
                        case "1":
                            // Generate committee members report
                            //todo
                            break;

                        case "2":
                            //attendee reports
                            //todo
                            break;  
                        
                        case "3":
                            break;

                        default:
                            break;
                    }
                    offerReturnToInnerMenuOption();
                    break;

                default:
                    innerMenu = false;
                    break;
            }
            Utility.redirectingPage() ;
        }
    }


    private static void handleSuggestionAdd (Student loggedInStudent , Camp camp) {
        System.out.print ("Enter the suggestion you would like to make: ") ;
        String suggestionContent = CAMSApp.scanner.nextLine() ;
        suggestionContent = Utility.replaceCommaWithSemicolon(suggestionContent) ;
        loggedInStudent.submitSuggestion(suggestionContent) ;
        System.out.println("Your suggestion has been successfully submitted!");
    }


    private static void handleEnquiryViewReply(Student loggedInStudent , Camp camp) {
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
    

    private static void offerReturnToInnerMenuOption() {
        System.out.print("Press any key to continue...");
        CAMSApp.scanner.nextLine();
    }
   
}
