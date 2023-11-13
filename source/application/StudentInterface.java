package source.application;

import java.util.ArrayList;
import java.util.Scanner;

import source.camp.Camp;
import source.exception.CampFullException;
import source.exception.CampNotFoundException;
import source.exception.DateClashException;
import source.exception.DeadlineOverException;
import source.exception.InvalidUserGroupException;
import source.exception.MultipleCommitteeRoleException;
import source.exception.WithdrawnException;
import source.user.Student;

public class StudentInterface {

    private static boolean exit = false ;

    protected static void handleStudentFunctionalities(Student loggedInStudent) {

        while (! exit) {
            Utility.clearConsole();

            // Prompt user to choose an option
            System.out.println ("Logged in as student: " + loggedInStudent.getUserId()) ;
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
                    System.out.println("Enter your current password:");
                    String currentPassword = CAMSApp.scanner.nextLine();
                    System.out.println("Enter a new password:");
                    String newPassword = CAMSApp.scanner.nextLine();
                    if (loggedInStudent.changePassword(currentPassword, newPassword)) {
                        System.out.println("Password changed successfully");
                    } else {
                        System.out.println("Incorrect current password entered. Password unchanged.");
                    }
                    offerReturnToMenuOption();
                    break;

                case "2":
                    // View camps within faculty
                    loggedInStudent.viewOpenCamps();
                    offerReturnToMenuOption();
                    break;

                case "3":
                    // Register for a camp
                    System.out.print ("Enter the name of the camp you wish to register: ") ;
                    String campNameToRegister = CAMSApp.scanner.nextLine();
                    System.out.print ("Register as 1.attendee 2.committee: ") ;
                    boolean committeeRole = CAMSApp.scanner.nextLine().equals("1") ;

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
                    } catch (Exception e) {
                        System.out.println("How tf you got here");
                    }

                    offerReturnToMenuOption();
                    break;

                // does the same thing as case 1, which can also display remaining slots of each camp
                // case "4":
                //     // View remaining slots of each camp
                //     viewRemainingSlots(loggedInStudent, camps);
                //     offerReturnToMenuOption(scanner);
                //     break;

                case "4":
                    // View camps you've registered for
                    loggedInStudent.viewRegisteredCamps();
                    offerReturnToMenuOption();
                    break;

                case "5":
                    // withdraw from camp
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
                    } catch (Exception e) {
                        System.out.println("How tf you got here");
                    }

                    offerReturnToMenuOption();
                    break;

                case "6":
                    System.out.print("Enter the name of the camp you want to submit an enquiry for: ");
                    String campNameToEnquire = CAMSApp.scanner.nextLine();
                    Camp campToEnquire = Utility.findCampByName(campNameToEnquire);

                    //todo
                    System.out.println("U/C");

                    offerReturnToMenuOption();
                    break;

                case "7":
                    //todo
                    System.out.println("U/C");

                    offerReturnToMenuOption();
                    break;

                case "8":
                    //todo
                    System.out.println("U/C");
                    offerReturnToMenuOption();
                    break;

                case "9":
                    //todo
                    System.out.println("U/C");
                    offerReturnToMenuOption();
                    break;

                case "10":
                    if (loggedInStudent.isCampCommittee()) {
                        System.out.println("U/C");
                        // handleCampCommiteeFunctionalities(loggedInStudent, students, camps, enquiries,
                        //         studentRegistrations, suggestions);
                        offerReturnToMenuOption();
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;
                default:
                    exit = true;
                    break;
            }
        }
    }

    private static void offerReturnToMenuOption () {
        System.out.println("Press 'M' to go back to the menu or any other key to exit.");
        String backChoice = CAMSApp.scanner.nextLine();
        if (!"M".equalsIgnoreCase(backChoice)) {
            exit = true;
        }
    }

}
