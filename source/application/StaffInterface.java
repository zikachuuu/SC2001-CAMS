package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.user.Staff;
import source.user.Student;

public class StaffInterface {

    private static boolean exit = false ;

    protected static void handleStaffFunctionalities(Staff loggedInStaff) {

        while (!exit) {
            System.out.println("Press 1 to change password");
            System.out.println("Press 2 to add a new camp");
            System.out.println("Press 3 to view all camps");
            System.out.println("Press 4 to view your camps");
            System.out.println("Press 5 to edit your camps");
            System.out.println("Press 6 to toggle a camp's visibility");
            System.out.println("Press 7 to delete a camp");
            System.out.println("Press 8 to generate camp commitee report");
            System.out.println("Press 9 to generate camp attendee report");
            System.out.println("Press 10 to generate enquiries report") ;
            System.out.println("Press 11 to view and approve camp suggestions");
            System.out.println("Press 12 to view and respond to enquiries");
            System.out.println("Press any other key to exit");
            System.out.print("Enter your choice: ");

            String choice = CAMSApp.scanner.nextLine() ;

            switch (choice) {
                case "1":
                    // Change password
                    System.out.println("Enter your current password:");
                    String currentPassword = CAMSApp.scanner.nextLine();
                    System.out.println("Enter a new password:");
                    String newPassword = CAMSApp.scanner.nextLine();
                    newPassword = Utility.replaceCommaWithSemicolon(newPassword);

                    if (loggedInStaff.changePassword(currentPassword, newPassword)) {
                        System.out.println("Password changed successfully");
                    } else {
                        System.out.println("Incorrect current password entered. Password unchanged.");
                    }
                    offerReturnToMenuOption();
                    break;

                case "2":
                    // Add a new camp
                    
                    
                    offerReturnToMenuOption();
                    break;

                case "3":
                    // View all camps

                    offerReturnToMenuOption();
                    break;

                case "4":
                    // View your camps

                    offerReturnToMenuOption();
                    break;

                case "5":
                    // Edit your camps

                    offerReturnToMenuOption() ;
                    break;

                case "6":
                    // Toggle visibility

                    offerReturnToMenuOption();
                    break;
                case "7":
                    // Delete a camp

                    offerReturnToMenuOption();
                    break;

                case "8":

                    offerReturnToMenuOption();
                    break;

                case "9":

                    offerReturnToMenuOption();
                    break;

                case "10" :

                    offerReturnToMenuOption();
                    break ;

                case "11":

                    offerReturnToMenuOption();
                    break;

                case "12":

                    offerReturnToMenuOption();
                    break;

                default:
                    exit = true;
                    break;
            }
            Utility.redirectingPage();
        }
    }

    private static void offerReturnToMenuOption() {
        System.out.print("Press 'M' to go back to the menu or any other key to exit: ");
        String backChoice = CAMSApp.scanner.nextLine();
        if (!"M".equalsIgnoreCase(backChoice)) {
            exit = true;
        }
    }


}
