package source.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import source.camp.Camp;
import source.camp.Enquiry;
import source.camp.Suggestion;
import source.user.Staff;
import source.user.Student;


public class CAMSApp {

    public static void main(String[] args) {

        // just dummies
        ArrayList<Student> students = new ArrayList<>() ;
        ArrayList<Staff> staffs = new ArrayList<>() ;
        ArrayList<Camp> camps = new ArrayList<>() ;

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

        System.out.println("Maximum login attempts reached. Exiting.") ;
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

