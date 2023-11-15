package source.application;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

import source.camp.Camp;
import source.exception.CampNotFoundException;
import source.exception.NoAccessException;
import source.user.Faculty;
import source.user.Staff;
import source.user.Student;

public class StaffInterface extends UserInterface {

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
                    handlePasswordChange(loggedInStaff);
                    offerReturnToMenuOption();
                    break;

                case "2":
                    // Add a new camp
                    try{
                        System.out.print("Enter camp name:");
                        String campName = CAMSApp.scanner.nextLine();
                        campName = Utility.replaceCommaWithSemicolon(campName) ;

                        System.out.print("Enter start date (yyyy-mm-dd):");
                        String startDateStr = CAMSApp.scanner.nextLine();
                        LocalDate startDate = Utility.convertStringToLocalDate(startDateStr) ;

                        System.out.print("Enter end date (yyyy-mm-dd):");
                        String endDateStr = CAMSApp.scanner.nextLine();
                        LocalDate endDate = Utility.convertStringToLocalDate(endDateStr) ;

                        System.out.print("Enter registration closing date (yyyy-mm-dd):");
                        String registrationClosingDateStr = CAMSApp.scanner.nextLine();
                        LocalDate registrationClosingDate = Utility.convertStringToLocalDate(registrationClosingDateStr) ;

                        System.out.println("Which group of students is this camp open to? (Enter a faculty acryonym or'NTU' for all students): ");
                        String userGroupStr = CAMSApp.scanner.nextLine();
                        Faculty userGroup = Faculty.valueOf(userGroupStr) ;

                        System.out.println("Enter camp location: ");
                        String location = CAMSApp.scanner.nextLine();
                        location = Utility.replaceCommaWithSemicolon(location) ;

                        System.out.println("Enter total slots (including committee and attendee): ");
                        int totalSlots = CAMSApp.scanner.nextInt();

                        System.out.println("Camp committee slots(max 10)");
                        int campCommitteeSlots = CAMSApp.scanner.nextInt();

                        CAMSApp.scanner.nextLine(); // clear buffer
                        System.out.println("Description");
                        String description = CAMSApp.scanner.nextLine();
                        description = Utility.replaceCommaWithSemicolon(description) ;

                        loggedInStaff.createCamp(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description) ;
                        System.out.println("Camp successfully created!") ;

                    } catch (DateTimeException dte) {
                        System.out.println("Sorry, unsupported date format. Use yyyy-mm-dd") ;
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Sorry, the entered faculty cannot be found. Try 'SCSE', 'SPMS', 'NTU', etc.");
                    } catch (InputMismatchException ipe) {
                        System.out.println("Sorry, you did not enter a integer value for slots input.");
                    }

                    offerReturnToMenuOption();
                    break;

                case "3":
                    loggedInStaff.viewAllCamps();
                    offerReturnToMenuOption();
                    break;

                case "4":
                    loggedInStaff.viewCreatedCamps();
                    offerReturnToMenuOption();
                    break;

                case "5":
                    // Edit your camps

                    offerReturnToMenuOption() ;
                    break;

                case "6":
                    System.out.print("Enter the name of the camp you wish to toggle visibility (visible <-> unvisible): ") ;
                    String campName = CAMSApp.scanner.nextLine() ;

                    try {
                        boolean newVisibility = loggedInStaff.toggleVisibility(campName) ;
                        System.out.printf("Camp's visibility has been set from %s to %s.\n" , 
                            (newVisibility ? "unvisible" : "visible") ,
                            (newVisibility ? "visible" : "unvisible")
                        );

                    } catch (CampNotFoundException cnfe) {
                        System.out.println("Sorry, the camp you entered does not exist.");
                    } catch (NoAccessException nae) {
                        System.out.println("Sorry, you cannot toggle the visibility of camps that already have students signed up.");
                    }
                    offerReturnToMenuOption();
                    break;

                case "7":
                    System.out.print("Enter the name of the camp you wish to delete: ") ;
                    String campName2 = CAMSApp.scanner.nextLine() ;

                    try {
                        loggedInStaff.deleteCamp(campName2);
                        System.out.println("Camp successfully deleted!");
                    } catch (CampNotFoundException cnfe) {
                        System.out.println("Sorry, the camp you entered does not exist.");
                    }
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

}
