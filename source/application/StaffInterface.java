package source.application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import source.camp.Camp;
import source.camp.Enquiry;
import source.camp.Suggestion;
import source.exception.* ;
import source.user.CampCommittee;
import source.user.Faculty;
import source.user.Staff;
import source.user.Student;
import source.application.UserManager;

public class StaffInterface extends UserInterface {

    protected static void handleStaffFunctionalities(Staff loggedInStaff) {

        if (loggedInStaff.isDefaultPassword()) handleDefaultPasswordChange(loggedInStaff);

        while (!exit) {
            System.out.println ("Welcome, " + loggedInStaff.getUserName() + " (staff id: " + loggedInStaff.getUserId() + ")") ;
            System.out.println("Press 1 to change password");
            System.out.println("Press 2 to add a new camp");
            System.out.println("Press 3 to view all camps");
            System.out.println("Press 4 to view your camps");
            System.out.println("Press 5 to edit your camps");
            System.out.println("Press 6 to toggle a camp's visibility");
            System.out.println("Press 7 to delete a camp");
            System.out.println("(todo) Press 8 to generate camp commitee report");
            System.out.println("(todo) Press 9 to generate camp attendee report");
            System.out.println("(todo) Press 10 to generate enquiries report") ;
            System.out.println("Press 11 to view and approve camp suggestions");
            System.out.println("Press 12 to view and reply enquiries");
            System.out.println("Press any other key to exit");
            System.out.print("Enter your choice: ");
            String choice = CAMSApp.scanner.nextLine() ;

            System.out.println();
            switch (choice) {
                case "1":
                    handlePasswordChange(loggedInStaff);
                    offerReturnToMenuOption();
                    break;

                case "2":
                    handleCampAdd(loggedInStaff);
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
                    handleCampEdit(loggedInStaff);
                    offerReturnToMenuOption() ;
                    break;

                case "6":
                    handleCampToggle(loggedInStaff);
                    offerReturnToMenuOption();
                    break;

                case "7":
                    handleCampDelete(loggedInStaff);
                    offerReturnToMenuOption();
                    break;

                case "8":
                    generateCommitteeMembersReportAsStaff();
                    offerReturnToMenuOption();
                    break;

                case "9":

                    offerReturnToMenuOption();
                    break;

                case "10" :

                    offerReturnToMenuOption();
                    break ;

                case "11":
                    handleSuggestionViewApprove(loggedInStaff) ;
                    offerReturnToMenuOption();
                    break;

                case "12":
                    handleEnquiryViewReply(loggedInStaff);
                    offerReturnToMenuOption();
                    break;

                default:
                    exit = true;
                    break;
            }
            Utility.redirectingPage();
        }
    }


    private static void handleCampAdd(Staff loggedInStaff) {
        try{
            System.out.print("Enter camp name: ");
            String campName = CAMSApp.scanner.nextLine();
            campName = Utility.replaceCommaWithSemicolon(campName) ;

            System.out.print("Enter start date (yyyy-mm-dd): ");
            String startDateStr = CAMSApp.scanner.nextLine();
            LocalDate startDate = Utility.convertStringToLocalDate(startDateStr) ;

            if (startDate.isBefore(LocalDate.now())) throw new DateAfterDateException("Sorry, camp start date cannot be before today.") ;

            System.out.print("Enter end date (yyyy-mm-dd): ");
            String endDateStr = CAMSApp.scanner.nextLine();
            LocalDate endDate = Utility.convertStringToLocalDate(endDateStr) ;

            if (endDate.isBefore(startDate)) throw new DateAfterDateException("Sorry, camp end date cannot be before its start date.") ;
            
            System.out.print("Enter registration closing date (yyyy-mm-dd): ");
            String registrationClosingDateStr = CAMSApp.scanner.nextLine();
            LocalDate registrationClosingDate = Utility.convertStringToLocalDate(registrationClosingDateStr) ;

            if (registrationClosingDate.isAfter(startDate)) throw new DateAfterDateException("Sorry, camp registration closing date cannot be after its start date.") ;

            System.out.print("Which group of students is this camp open to? (Enter a faculty acryonym or'NTU' for all students): ");
            String userGroupStr = CAMSApp.scanner.nextLine();
            Faculty userGroup = Faculty.valueOf(userGroupStr) ;

            System.out.print("Enter camp location: ");
            String location = CAMSApp.scanner.nextLine();
            location = Utility.replaceCommaWithSemicolon(location) ;

            System.out.print("Enter total slots (including committee and attendee): ");
            int totalSlots = CAMSApp.scanner.nextInt();

            System.out.print("Enter camp committee slots (max 10): ");
            int campCommitteeSlots = CAMSApp.scanner.nextInt();

            CAMSApp.scanner.nextLine(); // clear buffer
            System.out.print("Enter camp description: ");
            String description = CAMSApp.scanner.nextLine();
            description = Utility.replaceCommaWithSemicolon(description) ;

            if (loggedInStaff.createCamp(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description)){
                System.out.println("Camp successfully created!") ;
            } 
            else {
                System.out.println("Sorry, there is already a camp with the same name.") ;
            }
            
        } catch (DateTimeException dte) {
            System.out.println("Sorry, unsupported date format. Use yyyy-mm-dd") ;
        } catch (IllegalArgumentException iae) {
            System.out.println("Sorry, the entered faculty cannot be found. Try 'SCSE', 'SPMS', 'NTU', etc.");
        } catch (InputMismatchException ipe) {
            System.out.println("Sorry, you did not enter a integer value for slots input.");
        } catch (DateAfterDateException dade) {
            System.out.println(dade.getMessage()) ; 
        } catch (ExceedMaximumException eme) {
            System.out.println("Sorry, camp committee slots cannot exceed 10 or total slots.") ;
        }
    }
    

    private static void handleCampEdit(Staff loggedInStaff) {
        System.out.print("Enter the name of the camp you wish to edit: ") ;
        String campNameToEdit = CAMSApp.scanner.nextLine() ;
        Camp camp ;

        try {
            camp = CampManager.findCampByName(campNameToEdit) ;
            if(!camp.getCampInfo().getStaffInCharge().equals(loggedInStaff)) {
                throw new NoAccessException("You are not the staff in charge");
            }
        } catch (CampNotFoundException cnfe) {
            System.out.println("Sorry, the camp you entered does not exist.");
            return ;
        } catch (NoAccessException nae) {
            System.out.println("Sorry, you have no access");
            return;
        }


        System.out.println("1. Start date");
        System.out.println("2. End date");
        System.out.println("3. Registration closing date");
        System.out.println("4. User group");
        System.out.println("5. Location");
        System.out.println("6. Total slots");
        System.out.println("7. Camp committee slots");
        System.out.println("8. Description");
        System.out.print("Choose an option that you wish to edit: ");
        String option = CAMSApp.scanner.nextLine() ;

        try {

            switch (option) {
                case "1" :
                    System.out.print("Enter new start date (yyyy-mm-dd): ");
                    String startDateStr = CAMSApp.scanner.nextLine();
                    LocalDate startDate = Utility.convertStringToLocalDate(startDateStr) ;
                    if (startDate.isBefore(LocalDate.now())) throw new DateAfterDateException("Sorry, camp start date cannot be before today.") ;

                    loggedInStaff.editCamp(campNameToEdit, startDate, camp.getCampInfo().getEndDate(), camp.getCampInfo().getRegistrationClosingDate(), camp.getCampInfo().getUserGroup(), camp.getCampInfo().getLocation(), camp.getCampInfo().getTotalSlots() , camp.getCampInfo().getCampCommitteeSlots(), camp.getCampInfo().getDescription());
                    break ;
                
                case "2" :
                    System.out.print("Enter new end date (yyyy-mm-dd): ");
                    String endDateStr = CAMSApp.scanner.nextLine();
                    LocalDate endDate = Utility.convertStringToLocalDate(endDateStr) ;
                    if (endDate.isBefore(camp.getCampInfo().getStartDate())) throw new DateAfterDateException("Sorry, camp end date cannot be before its start date.") ;

                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), endDate, camp.getCampInfo().getRegistrationClosingDate(), camp.getCampInfo().getUserGroup(), camp.getCampInfo().getLocation(), camp.getCampInfo().getTotalSlots() , camp.getCampInfo().getCampCommitteeSlots(), camp.getCampInfo().getDescription());
                    break ;
                
                case "3" :
                    System.out.print("Enter new registration closing date (yyyy-mm-dd): ");
                    String registrationClosingDateStr = CAMSApp.scanner.nextLine();
                    LocalDate registrationClosingDate = Utility.convertStringToLocalDate(registrationClosingDateStr) ;
                    if (registrationClosingDate.isAfter(camp.getCampInfo().getStartDate())) throw new DateAfterDateException("Sorry, camp registration closing date cannot be after its start date.") ;
                  
                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), camp.getCampInfo().getEndDate(), registrationClosingDate, camp.getCampInfo().getUserGroup(), camp.getCampInfo().getLocation(), camp.getCampInfo().getTotalSlots() , camp.getCampInfo().getCampCommitteeSlots(), camp.getCampInfo().getDescription());
                    break ;

                case "4" :
                    System.out.print("Which group of students is this camp open to? (Enter a faculty acryonym or'NTU' for all students): ");
                    String userGroupStr = CAMSApp.scanner.nextLine();
                    Faculty userGroup = Faculty.valueOf(userGroupStr) ;

                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), camp.getCampInfo().getEndDate(), camp.getCampInfo().getRegistrationClosingDate(), userGroup, camp.getCampInfo().getLocation(), camp.getCampInfo().getTotalSlots() , camp.getCampInfo().getCampCommitteeSlots(), camp.getCampInfo().getDescription());
                    break ;

                case "5" :
                    System.out.print("Enter new camp location: ");
                    String location = CAMSApp.scanner.nextLine();
                    location = Utility.replaceCommaWithSemicolon(location) ;

                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), camp.getCampInfo().getEndDate(), camp.getCampInfo().getRegistrationClosingDate(), camp.getCampInfo().getUserGroup(), location, camp.getCampInfo().getTotalSlots() , camp.getCampInfo().getCampCommitteeSlots(), camp.getCampInfo().getDescription());
                    break;

                case "6" :
                    System.out.print("Enter new total slots (including committee and attendee): ");
                    int totalSlots = CAMSApp.scanner.nextInt();
                    if (camp.getNumAttendees() + camp.getNumCommittees() > totalSlots) throw new CampFullException() ;

                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), camp.getCampInfo().getEndDate(), camp.getCampInfo().getRegistrationClosingDate(), camp.getCampInfo().getUserGroup(), camp.getCampInfo().getLocation(), totalSlots , camp.getCampInfo().getCampCommitteeSlots(), camp.getCampInfo().getDescription());
                    break ;
                
                case "7" :
                    System.out.print("Enter new camp committee slots (max 10): ");
                    int campCommitteeSlots = CAMSApp.scanner.nextInt();
                    if (camp.getNumCommittees() > campCommitteeSlots) throw new CampFullException() ;

                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), camp.getCampInfo().getEndDate(), camp.getCampInfo().getRegistrationClosingDate(), camp.getCampInfo().getUserGroup(), camp.getCampInfo().getLocation(), camp.getCampInfo().getTotalSlots() , campCommitteeSlots, camp.getCampInfo().getDescription());
                    break ;

                case "8" :
                    System.out.print("Enter new camp description: ");
                    String description = CAMSApp.scanner.nextLine();
                    description = Utility.replaceCommaWithSemicolon(description) ;

                    loggedInStaff.editCamp(campNameToEdit, camp.getCampInfo().getStartDate(), camp.getCampInfo().getEndDate(), camp.getCampInfo().getRegistrationClosingDate(), camp.getCampInfo().getUserGroup(), camp.getCampInfo().getLocation(), camp.getCampInfo().getTotalSlots() , camp.getCampInfo().getCampCommitteeSlots(), description);
                    break ;
                
                default :
                    System.out.println("Sorry, invalid option selected");
                    return ;
            }
            System.out.println("Camp successfully edited!");
        }   
        catch (CampNotFoundException cnfe) {
            System.out.println("Sorry, the camp you entered does not exist.");
        } 
        catch (DateTimeException dte) {
            System.out.println("Sorry, unsupported date format. Use yyyy-mm-dd") ;
        } 
        catch (IllegalArgumentException iae) {
            System.out.println("Sorry, the entered faculty cannot be found. Try 'SCSE', 'SPMS', 'NTU', etc.");
        } 
        catch (InputMismatchException ipe) {
            System.out.println("Sorry, you did not enter a integer value for slots input.");
        } 
        catch (DateAfterDateException dade) {
            System.out.println(dade.getMessage()) ; 
        } 
        catch (ExceedMaximumException eme) {
            System.out.println("Sorry, camp committee slots cannot exceed 10 or total slots.") ;
        }
        catch (CampFullException cfe) {
            System.out.println("Sorry the current number of participants is larger than the new slots.");
        }
    }


    private static void handleCampToggle (Staff loggedInStaff) {
        System.out.print("Enter the name of the camp you wish to toggle visibility (visible <-> unvisible): ") ;
        String campNameToToggle = CAMSApp.scanner.nextLine() ;

        try {
            boolean newVisibility = loggedInStaff.toggleVisibility(campNameToToggle) ;
            System.out.printf("Camp's visibility has been set from %s to %s.\n" , 
                (newVisibility ? "unvisible" : "visible") ,
                (newVisibility ? "visible" : "unvisible")
            );

        } catch (CampNotFoundException cnfe) {
            System.out.println("Sorry, the camp you entered does not exist.");
        } catch (NoAccessException nae) {
            System.out.println("Sorry, you cannot toggle the visibility of camps that already have students signed up.");
        }
    }


    private static void handleCampDelete(Staff loggedInStaff) {
        System.out.print("Enter the name of the camp you wish to delete: ") ;
        String campNameToDelete = CAMSApp.scanner.nextLine() ;

        try {
            loggedInStaff.deleteCamp(campNameToDelete);
            System.out.println("Camp successfully deleted!");
        } catch (CampNotFoundException cnfe) {
            System.out.println("Sorry, the camp you entered does not exist.");
        }
    }


    private static void handleSuggestionViewApprove (Staff loggedInStaff) {
        ArrayList<Suggestion> suggestions = SuggestionManager.findAllSuggestions(loggedInStaff , true) ;
        if (suggestions.size() == 0) {
            System.out.println("There are currently no unapproved suggestions regarding this camp.");
            return ;
        }

        for (int i = 0 ; i < suggestions.size() ; i++) {
            System.out.println("Suggestions " + (i + 1) + ": ");
            suggestions.get(i).viewSuggestion();
            System.out.println();
        }

        System.out.print("Choose an suggestion that you wish to approve: ") ;
        String suggestionChoice = CAMSApp.scanner.nextLine() ;

        try {
            suggestions.get (Integer.parseInt(suggestionChoice) - 1).approveSuggestion(loggedInStaff) ;
            System.out.println("Suggestion has been successfully approved!") ;
        } 
        catch (NumberFormatException | IndexOutOfBoundsException ee) {
            System.out.println("Invalid suggestion choice.");
        }
    }


    private static void handleEnquiryViewReply(Staff loggedInStaff) {
        ArrayList<Enquiry> enquiries = EnquiryManager.findAllEnquiry(loggedInStaff, true) ;

        if (enquiries.size() == 0) {
            System.out.println("There are currently no unanswered enquiries regarding this camp.");
            return ;
        }

        for (int i = 0 ; i < enquiries.size() ; i++) {
            System.out.println("Enquiry " + (i + 1) + ": ");
            enquiries.get(i).viewEnquiry();
            System.out.println();
        }

        System.out.print("Choose an enquiry that you wish to reply: ") ;
        String enquiryChoice = CAMSApp.scanner.nextLine() ;

        System.out.print ("Enter your reply here: ") ;
        String reply = CAMSApp.scanner.nextLine() ;
        reply = Utility.replaceCommaWithSemicolon(reply);

        try {
            enquiries.get(Integer.parseInt(enquiryChoice) - 1).replyEnquriy(loggedInStaff, reply) ;
            System.out.println("Enquiry has been successfully replied!") ;
        } 
        catch (NumberFormatException | IndexOutOfBoundsException ee) {
            System.out.println("Invalid enquiry choice.");
        }
    }

    private static void generateCommitteeMembersReportAsStaff() {
        //Scanner scanner = new Scanner(System.in);

        //System.out.println("Enter the name of the camp for which you want to generate a committee members report:");
        //String targetCampName = scanner.nextLine();

        String filePath =  "report//committee_report.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Student ID,Student Email,Student UserName, Points");
            writer.newLine();

            for (Camp camp : CAMSApp.camps) {
                    String campName = camp.getCampInfo().getCampName();

                    System.out.println("List of committee members registered for: " + campName);

                    for (Student students: CAMSApp.students) {
                        //if (students.getCampCommittee().getCamp().getCampInfo().getCampName().equalsIgnoreCase(campName) &&
                        //        students.isCampCommittee()) {
                            String studentId = students.getUserId();

                            Student student = UserManager.findStudentByUserId(studentId);


                                writer.write(studentId + "," + studentId + "@e.ntu.edu.sg," + student.getUserName()+ "," + student.getCampCommittee().getPoints());
                                writer.newLine();

                        }

                }


            System.out.println("Committee members report generated successfully. File: "+ filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
