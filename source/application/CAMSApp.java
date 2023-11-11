package source.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import source.camp.Camp;
import source.user.Staff;
import source.user.Student;


public class CAMSApp {

    private static final String STUDENT_FILE_PATH = "data\\student_list.csv";
    private static final String STAFF_FILE_PATH = "data\\staff_list.csv";
    private static final String CAMP_FILE_PATH = "data\\camps_list.csv";
    private static final String CAMP_MEMBERS_FILE_PATH = "data\\camp_members.csv";
    private static final String ENQUIRIES_FILE_PATH = "data\\enquiries.csv";
    private static final String SUGGESTIONS_FILE_PATH = "data\\suggestions.csv";

    public static void main(String[] args) {

        // just dummies
        ArrayList<Staff> staffs = FileProcessing.readStaffFromFile (STAFF_FILE_PATH) ;
        ArrayList<Camp> camps = FileProcessing.readCampsFromFile(CAMP_FILE_PATH, staffs) ;
        ArrayList<Student> students = FileProcessing.readStudentsFromFile(STUDENT_FILE_PATH, CAMP_MEMBERS_FILE_PATH, camps) ;

        Scanner scanner = new Scanner(System.in);
        int loginAttempts = 0;
        
        while (loginAttempts < 3) {
            Utility.clearConsole();
            System.out.println("Welcome to CAMS!") ;
            System.out.println("LOGIN AS:");
            System.out.println("1. Student");
            System.out.println("2. Staff");

            int userTypeChoice = 0 ;
            while (true) {
                System.out.print ("Enter your choice: ") ;
                try {
                    userTypeChoice = scanner.nextInt();
                    if (userTypeChoice != 1 && userTypeChoice != 2) throw new InputMismatchException() ;
                    break ;
                }
                catch (InputMismatchException e) {
                    System.out.println ("\nInvalid login type choice. Try again.") ;
                    scanner.nextLine();
                }
            }

            System.out.println();
            scanner.nextLine();

            System.out.print("Enter User ID: ");
            String enteredUserId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String enteredPassword = scanner.nextLine();   
            System.out.println() ; 
            
            if (userTypeChoice == 1) {
                Student loggedInStudent = authenticateStudent (enteredUserId, enteredPassword, students);

                if (loggedInStudent != null) {
                    System.out.println("Student Login successful!");
                    StudentInterface.handleStudentFunctionalities(loggedInStudent, students, camps);
                    break; 
                } else {
                    System.out.println("Invalid student credentials. Login failed.");
                }

            } else if (userTypeChoice == 2) {
                Staff loggedInStaff = authenticateStaff(enteredUserId, enteredPassword, staffs);

                if (loggedInStaff != null) {
                    System.out.println("Staff Login successful!");
                    StaffInterface.handleStaffFunctionalities(loggedInStaff, staffs, camps, students);
                    break; 
                } else {
                    System.out.println("Invalid staff credentials. Login failed.");
                }
            }

            loginAttempts++;
            System.out.println("You have " + (3 - loginAttempts) + " attempts remaining.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (loginAttempts == 3) System.out.println("Maximum login attempts reached. Exiting.") ;
        scanner.close();
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

