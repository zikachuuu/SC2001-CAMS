package source.application;

import java.util.ArrayList;

import source.camp.Enquiry;
import source.exception.CampFullException;
import source.exception.CampNotFoundException;
import source.exception.DateClashException;
import source.exception.DeadlineOverException;
import source.exception.InvalidUserGroupException;
import source.exception.MultipleCommitteeRoleException;
import source.exception.WithdrawnException;
import source.user.Student;

public class StudentInterface extends UserInterface {

    protected static void handleStudentFunctionalities(Student loggedInStudent) {

        if (loggedInStudent.isDefaultPassword()) handleDefaultPasswordChange(loggedInStudent);

        while (! exit) {
            // Prompt user to choose an option
            System.out.println ("Welcome, " + loggedInStudent.getUserName() + " (student id: " + loggedInStudent.getUserId() + ")") ;
            System.out.println("Press 1 to change password");
            System.out.println("Press 2 to view camps in your faculty");
            System.out.println("Press 3 to register for a camp");
            System.out.println("Press 4 to view camps you've registered for");
            System.out.println("Press 5 to withdraw from a camp");
            System.out.println("Press 6 to submit an enquiry regarding a camp");
            System.out.println("Press 7 to view enquiries");
            System.out.println("Press 8 to edit an enquiry");
            System.out.println("Press 9 to delete an enquiry");
            if (loggedInStudent.isCampCommittee()) {
                System.out.println("Press 10 to access committee member functionalities");
            }
            System.out.println("Press any other key to exit");
            System.out.print ("Enter your choice: ") ;
            String choice = CAMSApp.scanner.nextLine();

            System.out.println();
            switch (choice) {
                case "1":
                    handlePasswordChange(loggedInStudent);
                    offerReturnToMenuOption();
                    break;

                case "2":
                    loggedInStudent.viewOpenCamps();
                    offerReturnToMenuOption();
                    break;

                case "3":
                    handleCampRegister(loggedInStudent);
                    offerReturnToMenuOption();
                    break;

                case "4":
                    loggedInStudent.viewRegisteredCamps();
                    offerReturnToMenuOption();
                    break;

                case "5":
                    handleCampWithdraw(loggedInStudent) ;
                    offerReturnToMenuOption();
                    break;

                case "6":
                    handleSubmitEnquiry(loggedInStudent);
                    offerReturnToMenuOption();
                    break;

                case "7":
                    loggedInStudent.viewSubmittedEnquiries() ;
                    offerReturnToMenuOption();
                    break;

                case "8":
                    handleEditEnquiry(loggedInStudent);
                    offerReturnToMenuOption();
                    break;

                case "9":
                    handleDeleteEnquiry(loggedInStudent);
                    offerReturnToMenuOption();
                    break;

                case "10":
                    if (loggedInStudent.isCampCommittee()) {
                        Utility.redirectingPage() ;
                        CommitteeInterface.handleCampCommiteeFunctionalities(loggedInStudent, loggedInStudent.getCampCommittee().getCamp());
                    } else {
                        System.out.println("You have no camp committee role.");
                    }
                    break;

                default:
                    exit = true;
                    break;
            }
            Utility.redirectingPage() ;
        }
    }


    private static void handleCampRegister (Student loggedInStudent) {
        System.out.print ("Enter the name of the camp you wish to register: ") ;
        String campNameToRegister = CAMSApp.scanner.nextLine();
        System.out.print ("Register as 1.attendee 2.committee: ") ;
        boolean committeeRole = CAMSApp.scanner.nextLine().equals("2") ;

        try {
            loggedInStudent.registerForCamp(campNameToRegister , committeeRole) ;
            System.out.println("Registration successful!");

        } catch (CampNotFoundException cnfe) {
            System.out.println ("Sorry, the camp you entered does not exist.") ;
        } catch (InvalidUserGroupException iuge) {
            System.out.println ("Sorry, the camp is not opened to your faculty.") ;
        } catch (MultipleCommitteeRoleException mcre) {
            System.out.println("Sorry, you already have an active committee role.");
        } catch (DateClashException dce) {
            System.out.println("Sorry, the camp you wish to sign up clashes with your other camps.");
        } catch (DeadlineOverException doe) {
            System.out.println("Sorry, the registration deadline for this camp has passed");
        } catch (CampFullException cfe) {
            System.out.println("Sorry, the camp is already full. You can try registering again as a different role.") ;
        } catch (WithdrawnException we) {
            System.out.println ("Sorry, you are not allowed to register for camps that you have withdrawn previously.") ;
        }
    }


    private static void handleCampWithdraw(Student loggedInStudent) {
        System.out.print("Enter the name of the camp you want to withdraw from: ");
        String campNameToWithdraw = CAMSApp.scanner.nextLine();

        try {
            if (! loggedInStudent.withdrawFromCamp(campNameToWithdraw)) {
                System.out.println ("Sorry, you can only withdraw from camps that you registered as attendee.") ;
            } else {
                System.out.println("Successfully withdrawn.");
            }
        } catch (CampNotFoundException cnfe) {
            System.out.println ("Sorry, the camp you entered does not exist.") ;
        }
    }

    
    private static void handleSubmitEnquiry(Student loggedInStudent) {
        System.out.print("Enter the name of the camp you want to submit an enquiry for: ");
        String campNameToEnquire = CAMSApp.scanner.nextLine();
        System.out.print("Enter the content of the enquiry: ") ;
        String content = CAMSApp.scanner.nextLine() ;
        content = Utility.replaceCommaWithSemicolon(content);

        try {
            loggedInStudent.submitEnquiry(campNameToEnquire, content);
            System.out.println("Your enquiry has been successfully submitted!");
        } catch (CampNotFoundException cnfe) {
            System.out.println ("Sorry, the camp you entered does not exist.") ;
        }
    }


    private static void handleEditEnquiry (Student loggedInStudent) {
        ArrayList<Enquiry> enquiries = EnquiryManager.findAllEnquiry(loggedInStudent , true) ;

        if (enquiries.size() == 0) {
            System.out.println("You do have any enquiries that you can edit.");
            return ;
        }

        for (int i = 0 ; i < enquiries.size() ; i++) {
            System.out.println("Enquiry " + (i + 1) + ": ");
            System.out.println("Camp: " + enquiries.get(i).getCamp().getCampInfo().getCampName());
            System.out.println("Content: " + enquiries.get(i).getContent()) ;
            System.out.println();
        }

        System.out.print("Choose an enquiry that you wish to edit: ") ;
        String enquiryChoice = CAMSApp.scanner.nextLine() ;

        System.out.print ("Enter the new content of this enquiry: ") ;
        String newContent = CAMSApp.scanner.nextLine() ;
        newContent = Utility.replaceCommaWithSemicolon(newContent);

        try {
            enquiries.get(Integer.parseInt(enquiryChoice) - 1).editEnquiry(loggedInStudent,newContent) ;
            System.out.println("Enquiry successfully updated!") ;
        } 
        catch (NumberFormatException | IndexOutOfBoundsException ee) {
            System.out.println("Invalid enquiry choice.");
        }
    }


    private static void handleDeleteEnquiry(Student loggedInStudent) {
        ArrayList<Enquiry> enquiries2 = EnquiryManager.findAllEnquiry(loggedInStudent , true) ;

        if (enquiries2.size() == 0) {
            System.out.println("You do have any enquiries that you can delete.");
            return ;
        }

        for (int i = 0 ; i < enquiries2.size() ; i++) {
            System.out.println("Enquiry " + (i + 1) + ": ");
            System.out.println("Camp: " + enquiries2.get(i).getCamp().getCampInfo().getCampName());
            System.out.println("Content: " + enquiries2.get(i).getContent()) ;
            System.out.println();
        }

        System.out.print("Choose an enquiry that you wish to delete: ") ;
        String enquiryChoice2 = CAMSApp.scanner.nextLine() ;

        try {
            enquiries2.get(Integer.parseInt(enquiryChoice2) - 1).deleteEnquiry(loggedInStudent) ;
            System.out.println("Enquiry successfully deleted!") ;
        } 
        catch (NumberFormatException | IndexOutOfBoundsException ee) {
            System.out.println("Invalid enquiry choice.");
        }
    }
}
