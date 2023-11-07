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
}