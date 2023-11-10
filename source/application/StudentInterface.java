package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.user.Student;

public class StudentInterface {

    protected static void handleStudentFunctionalities(Student loggedInStudent, ArrayList<Student> students, ArrayList<Camp> camps) {
        Utility.clearConsole();
        System.out.println("test...in student interface") ;
    
    //     Scanner scanner = new Scanner(System.in);

    //     while (!exit) {
    //         // Prompt user to choose an option
    //         System.out.println("Press 1 to change password");
    //         System.out.println("Press 2 to view camps in your faculty");
    //         System.out.println("Press 3 to register for a camp");
    //         System.out.println("Press 4 View remaining slots of each camp");
    //         System.out.println("Press 5 View camps you've registered for");
    //         System.out.println("Press 6 to withdraw from a camp");
    //         System.out.println("Press 7 to submit an enquiry regarding a camp");
    //         System.out.println("Press 8 to view enquiries");
    //         System.out.println("Press 9 to edit an enquiry");
    //         System.out.println("Press 10 to delete an enquiry");
    //         boolean isCommitteeMember = checkIfCommitteeMember(loggedInStudent, studentRegistrations);
    //         if (isCommitteeMember) {
    //             System.out.println("Press 11 to access committee member functionalities");
    //         }
    //         System.out.println("Press any other key to exit");

    //         String choice = scanner.nextLine();

    //         switch (choice) {
    //             case "1":

    //                 System.out.println("Enter your current password:");
    //                 String currentPassword = scanner.nextLine();
    //                 System.out.println("Enter a new password:");
    //                 String newPassword = scanner.nextLine();
    //                 loggedInStudent.changePassword(currentPassword, newPassword, students);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "2":
    //                 // View camps withiin faculty
    //                 viewFacultyCamps(loggedInStudent, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "3":
    //                 // Register for a camp
    //                 registerForCamp(loggedInStudent, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "4":
    //                 // View remaining slots of each camp
    //                 viewRemainingSlots(loggedInStudent, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "5":
    //                 // View camps you've registered for
    //                 studentRegistrations = CampRegistration.readRegistrationsFromFile(CAMP_MEMBERS_FILE_PATH);
    //                 viewRegisteredCamps(loggedInStudent, camps, studentRegistrations);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "6":
    //                 // withdraw from camp
    //                 studentRegistrations = CampRegistration.readRegistrationsFromFile(CAMP_MEMBERS_FILE_PATH);
    //                 viewRegisteredCamps(loggedInStudent, camps, studentRegistrations);
    //                 System.out.println("Enter the name of the camp you want to withdraw from:");
    //                 String campNameToWithdraw = scanner.nextLine();

    //                 CampClass campToWithdraw = findCampByName(campNameToWithdraw, camps);
    //                 if (campToWithdraw != null) {

    //                     withdrawFromCamp(loggedInStudent, campToWithdraw);
    //                 } else {
    //                     System.out.println("Camp not found or not eligible for withdrawal.");

    //                 }
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "7":
    //                 submitCampEnquiry(loggedInStudent, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "8":
    //                 viewMyEnquiries(loggedInStudent, enquiries);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "9":
    //                 editEnquiry(loggedInStudent, enquiries);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "10":
    //                 deleteEnquiry(loggedInStudent, enquiries);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             case "11":
    //                 if (isCommitteeMember) {
    //                     handleCampCommiteeFunctionalities(loggedInStudent, students, camps, enquiries,
    //                             studentRegistrations, suggestions);
    //                     offerReturnToMenuOption(scanner);
    //                 } else {
    //                     System.out.println("Invalid option.");
    //                 }
    //                 break;
    //             default:
    //                 exit = true;
    //                 break;
    }

}
