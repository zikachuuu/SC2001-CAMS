package source;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CAMSApp {

    private static final String studentFilePath = "student_list.csv";
    private static final String staffFilePath = "staff_list.csv";
    private static final String CAMPS_FILE_PATH = "camps_list.csv";
    private static final String CAMP_MEMBERS_FILE_PATH = "camp_members.csv";
    private static final String ENQUIRIES_FILE_PATH = "enquiries.csv";
    private static final String SUGGESTIONS_FILE_PATH = "suggestions.csv";
    private static final String REPLIES_FILE_PATH = "replies.csv";

    public static void main(String[] args) {

        // Read  from the files
        List<Student> students = Utility.readStudentsFromFile(studentFilePath);
        List<Staff> staffMembers = Utility.readStaffFromFile(staffFilePath);
        List<Camp> camps = Utility.readCampsFromFile(CAMPS_FILE_PATH);
        List<Enquiry> enquiries = Utility.readEnquiriesFromFile(ENQUIRIES_FILE_PATH);
        // ArrayList<CampRegistration> studentRegistrations = CampRegistration.readRegistrationsFromFile(CAMP_MEMBERS_FILE_PATH);
        List<Suggestion> suggestions = Utility.readSuggestionsFromFile(SUGGESTIONS_FILE_PATH);
        // List<EnquiryReplies> enquiryRepliesList = EnquiryReplies.readEnquiryRepliesFromFile(REPLIES_FILE_PATH);

        Scanner scanner = new Scanner(System.in);
        int loginAttempts = 0;
        
        while (loginAttempts < 3) {
            System.out.println("LOGIN AS:");
            System.out.println("1. Student");
            System.out.println("2. Staff");
            int userTypeChoice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter User ID:");
            String enteredUserId = scanner.nextLine();
            System.out.println("Enter Password:");
            String enteredPassword = scanner.nextLine();

            if (userTypeChoice == 1) {
                Student loggedInStudent = authenticateStudent(enteredUserId, enteredPassword, students);

                if (loggedInStudent != null) {
                    System.out.println("Student Login successful!");
                    handleStudentFunctionalities(loggedInStudent, students, camps, enquiries, studentRegistrations, suggestions);
                    break; 
                } else {
                    System.out.println("Invalid student credentials. Login failed.");
                }

            } else if (userTypeChoice == 2) {
                Staff loggedInStaff = authenticateStaff(enteredUserId, enteredPassword, staffMembers);

                if (loggedInStaff != null) {
                    System.out.println("Staff Login successful!");
                    handleStaffFunctionalities(loggedInStaff, staffMembers, camps,
                            students, studentRegistrations, suggestions, enquiries);
                    break; 
                } else {
                    System.out.println("Invalid staff credentials. Login failed.");
                }

            } else {
                System.out.println("Invalid login type choice.");
            }

            loginAttempts++;
            System.out.println("Login failed. You have " + (3 - loginAttempts) + " attempts remaining.");

        }
        System.out.println("Maximum login attempts reached. Exiting.") ;
    }

    private static Student authenticateStudent(String userId, String password, List<Student> students) {
        for (Student student : students) {
            if (student.getUserId().equals(userId) && student.getPassword().equals(password)) {
                return student; 
            }
        }
        return null; 
    }

    private static Staff authenticateStaff(String userId, String password, List<Staff> staffMembers) {
        for (Staff staffMember : staffMembers) {
            if (staffMember.getUserId().equals(userId) && staffMember.getPassword().equals(password)) {
                return staffMember; 
            }
        }
        return null; 
    }

    private static void handleStudentFunctionalities(Student loggedInStudent, List<StudentClass> students,
        List<Camp> camps, List<Enquiry> enquiries, List<CampRegistration> studentRegistrations,
        List<SuggestionsClass> suggestions) {
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            // Prompt user to choose an option
            System.out.println("Press 1 to change password");
            System.out.println("Press 2 to view camps in your faculty");
            System.out.println("Press 3 to register for a camp");
            System.out.println("Press 4 View remaining slots of each camp");
            System.out.println("Press 5 View camps you've registered for");
            System.out.println("Press 6 to withdraw from a camp");
            System.out.println("Press 7 to submit an enquiry regarding a camp");
            System.out.println("Press 8 to view enquiries");
            System.out.println("Press 9 to edit an enquiry");
            System.out.println("Press 10 to delete an enquiry");
            boolean isCommitteeMember = checkIfCommitteeMember(loggedInStudent, studentRegistrations);
            if (isCommitteeMember) {
                System.out.println("Press 11 to access committee member functionalities");
            }
            System.out.println("Press any other key to exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":

                    System.out.println("Enter your current password:");
                    String currentPassword = scanner.nextLine();
                    System.out.println("Enter a new password:");
                    String newPassword = scanner.nextLine();
                    loggedInStudent.changePassword(currentPassword, newPassword, students);
                    offerReturnToMenuOption(scanner);
                    break;

                case "2":
                    // View camps withiin faculty
                    viewFacultyCamps(loggedInStudent, camps);
                    offerReturnToMenuOption(scanner);
                    break;

                case "3":
                    // Register for a camp
                    registerForCamp(loggedInStudent, camps);
                    offerReturnToMenuOption(scanner);
                    break;

                case "4":
                    // View remaining slots of each camp
                    viewRemainingSlots(loggedInStudent, camps);
                    offerReturnToMenuOption(scanner);
                    break;

                case "5":
                    // View camps you've registered for
                    studentRegistrations = CampRegistration.readRegistrationsFromFile(CAMP_MEMBERS_FILE_PATH);
                    viewRegisteredCamps(loggedInStudent, camps, studentRegistrations);
                    offerReturnToMenuOption(scanner);
                    break;

                case "6":
                    // withdraw from camp
                    studentRegistrations = CampRegistration.readRegistrationsFromFile(CAMP_MEMBERS_FILE_PATH);
                    viewRegisteredCamps(loggedInStudent, camps, studentRegistrations);
                    System.out.println("Enter the name of the camp you want to withdraw from:");
                    String campNameToWithdraw = scanner.nextLine();

                    CampClass campToWithdraw = findCampByName(campNameToWithdraw, camps);
                    if (campToWithdraw != null) {

                        withdrawFromCamp(loggedInStudent, campToWithdraw);
                    } else {
                        System.out.println("Camp not found or not eligible for withdrawal.");

                    }
                    offerReturnToMenuOption(scanner);
                    break;

                case "7":
                    submitCampEnquiry(loggedInStudent, camps);
                    offerReturnToMenuOption(scanner);
                    break;
                case "8":
                    viewMyEnquiries(loggedInStudent, enquiries);
                    offerReturnToMenuOption(scanner);
                    break;
                case "9":
                    editEnquiry(loggedInStudent, enquiries);
                    offerReturnToMenuOption(scanner);
                    break;

                case "10":
                    deleteEnquiry(loggedInStudent, enquiries);
                    offerReturnToMenuOption(scanner);
                    break;

                case "11":
                    if (isCommitteeMember) {
                        handleCampCommiteeFunctionalities(loggedInStudent, students, camps, enquiries,
                                studentRegistrations, suggestions);
                        offerReturnToMenuOption(scanner);
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

}