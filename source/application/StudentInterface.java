package source.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

import source.camp.Camp;
import source.camp.Enquiry;
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
                    System.out.println("Enter your current password:");
                    String currentPassword = CAMSApp.scanner.nextLine();
                    System.out.println("Enter a new password:");
                    String newPassword = CAMSApp.scanner.nextLine();
                    newPassword = Utility.replaceCommaWithSemicolon(newPassword);

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
                    System.out.print("Enter the content of the enquiry: ") ;
                    String content = CAMSApp.scanner.nextLine() ;
                    content = Utility.replaceCommaWithSemicolon(content);

                    try {
                        loggedInStudent.submitEnquiry(campNameToEnquire, content);
                    } catch (CampNotFoundException cnfe) {
                        System.out.println ("Sorry, the camp you entered does not exist.") ;
                    } catch (Exception e) {
                        System.out.println("How tf you got here");
                    }

                    offerReturnToMenuOption();
                    break;

                case "7":
                    loggedInStudent.viewSubmittedEnquiries() ;
                    offerReturnToMenuOption();
                    break;

                case "8":
                    ArrayList<Enquiry> enquiries = EnquiryManager.findAllEnquiry(loggedInStudent) ;
                    boolean have = false ;
                    int count = 1 ;

                    Iterator<Enquiry> itr = enquiries.iterator() ;
                    while (itr.hasNext()) {
                        Enquiry en = itr.next() ;
                        if (en.getReplied() || ! en.getActive()) {
                            itr.remove();
                        }
                    }

                    for (Enquiry enquiry : enquiries) {
                        System.out.println("Enquiry " + count + ": ");
                        System.out.println("Camp: " + enquiry.getCamp().getCampInfo().getCampName());
                        System.out.println("Content: " + enquiry.getContent()) ;
                        System.out.println();
                        count++ ;
                        have = true ;
                    }

                    if (! have) {
                        System.out.println("You do have any enquiries that you can edit.");
                        break ;
                    }

                    System.out.print("Choose an enquiry that you wish to edit: ") ;
                    int enquiryChoice = CAMSApp.scanner.nextInt() ;
                    CAMSApp.scanner.nextLine() ; // clear buffer

                    System.out.print ("Enter the new content of this enquiry: ") ;
                    String newContent = CAMSApp.scanner.nextLine() ;
                    newContent = Utility.replaceCommaWithSemicolon(newContent);

                    try {
                        enquiries.get(enquiryChoice - 1).editEnquiry(loggedInStudent,newContent) ;
                        System.out.println("Enquiry successfully updated!") ;
                    } catch (IndexOutOfBoundsException ioobe) {
                        System.out.println("Invalid choice");
                    }

                    offerReturnToMenuOption();
                    break;

                case "9":
                    //todo
                    System.out.println("U/C");
                    offerReturnToMenuOption();
                    break;

                case "10":
                    if (loggedInStudent.isCampCommittee()) {
                        Utility.redirectingPage() ;
                        handleCampCommiteeFunctionalities(loggedInStudent, loggedInStudent.getCampCommittee().getCamp());
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

    private static void handleCampCommiteeFunctionalities(Student loggedInStudent, Camp camp) {

        boolean innermenu = true;
        while (innermenu) {
            System.out.println("Viewing " + loggedInStudent.getUserName() + "'s camp comittee role.");
            System.out.println("Press 1 to view details of the camp you've registered for");
            System.out.println("Press 2 to submit suggestions");
            System.out.println("Press 3 to view and reply to student enquiries");
            System.out.println("Press 4 to view students registered");
            System.out.println("Press 5 to generate reports");
            System.out.println("Press any other key to exit commitee menu");
            System.out.print ("Enter your choice: ") ;

            String choice = CAMSApp.scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\nViewing details of the camp you've registered for as a committee member\n");
                    camp.viewCampDetails(loggedInStudent);
                    System.out.println();
                    offerReturnToInnerMenuOption();
                    break;

                case "2":
                    //todo
                    offerReturnToInnerMenuOption();
                    break;

                case "3":
                    //todo
                    offerReturnToInnerMenuOption();
                    break;

                case "4":
                    //todo
                    offerReturnToInnerMenuOption();
                    break;

                case "5":
                    boolean reportMenu = true;
                    while (reportMenu) {
                        System.out.println("Press 1 to generate committee members report");
                        System.out.println("Press 2 to generate attendee members report");
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

                            default:
                                reportMenu = false;
                                break;
                        }
                    }
                    offerReturnToInnerMenuOption();
                    break;

                default:
                    innermenu = false;
                    break;
            }
            Utility.redirectingPage() ;
        }
    }


    private static void offerReturnToMenuOption () {
        System.out.print("Press 'M' to go back to the menu or any other key to exit: ");
        String backChoice = CAMSApp.scanner.nextLine();
        if (!"M".equalsIgnoreCase(backChoice)) {
            exit = true;
        }
    }

    private static void offerReturnToInnerMenuOption() {
        System.out.print("Press any key to continue...");
        CAMSApp.scanner.nextLine();
    }

}
