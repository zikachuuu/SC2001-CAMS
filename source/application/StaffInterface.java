package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.user.Staff;
import source.user.Student;

public class StaffInterface {
    protected static void handleStaffFunctionalities(Staff loggedInStaff, ArrayList<Staff> staffMembers,
            ArrayList<Camp> camps, ArrayList<Student> students) {
        Utility.clearConsole();
        System.out.println("test...in student interface") ;

    //     Scanner scanner = new Scanner(System.in);

    //     while (!exit) {
    //         System.out.println("Press 1 to change password");
    //         System.out.println("Press 2 to add a new camp");
    //         System.out.println("Press 3 to view all camps");
    //         System.out.println("Press 4 to view your camps");
    //         System.out.println("Press 5 to edit your camps");
    //         System.out.println("Press 6 to toggle a camp's visibility");
    //         System.out.println("Press 7 to delete a camp");
    //         System.out.println("Press 8 to generate a camp commitee report");
    //         System.out.println("Press  9 to generate a camp attendee report");
    //         System.out.println("Press  10 view and approve camp suggestions");
    //         System.out.println("Press 11 to view and respond to enquiries");
    //         System.out.println("Any other key to exit");
    //         System.out.println("Enter your choice:");

    //         String choice = scanner.nextLine();

    //         switch (choice) {
    //             case "1":
    //                 // Change password
    //                 System.out.println("Enter your current password:");
    //                 String currentPassword = scanner.nextLine();
    //                 System.out.println("Enter a new password:");
    //                 String newPassword = scanner.nextLine();
    //                 loggedInStaff.changePassword(currentPassword, newPassword, staffMembers);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "2":
    //                 // Add a new camp
    //                 addNewCamp(loggedInStaff, staffMembers);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "3":
    //                 // View all camps
    //                 camps = CampClass.readCampsFromFile(CAMPS_FILE_PATH);
    //                 listAllCamps(camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "4":
    //                 // View your camps
    //                 camps = CampClass.readCampsFromFile(CAMPS_FILE_PATH);
    //                 listStaffCamps(camps, loggedInStaff);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "5":
    //                 // Edit your camps
    //                 editStaffCamps(loggedInStaff, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "6":
    //                 // Toggle visibility
    //                 toggleVisibility(loggedInStaff, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "7":
    //                 // Delete a camp
    //                 deleteCamp(loggedInStaff, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "8":
    //                 listStaffCamps(camps, loggedInStaff);
    //                 generateCommiteeMembersReportAsStaff(loggedInStaff, studentRegistrations,
    //                         students, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "9":
    //                 listStaffCamps(camps, loggedInStaff);
    //                 generateAttendeeMembersReportAsStaff(loggedInStaff, studentRegistrations,
    //                         students, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "10":
    //                 listStaffCamps(camps, loggedInStaff);
    //                 viewCampSuggestions(loggedInStaff, suggestions, camps, studentRegistrations);
    //                 offerReturnToMenuOption(scanner);
    //                 break;
    //             case "11":
    //                 respondToEnquiries(loggedInStaff, enquiries, camps);
    //                 offerReturnToMenuOption(scanner);
    //                 break;

    //             default:
    //                 System.out.println("Exiting staff functionalities.");
    //                 exit = true;
    //                 break;
    //         }
    //     }
    }


}
