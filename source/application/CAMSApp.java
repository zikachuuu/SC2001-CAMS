package source.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import source.camp.Camp;
import source.user.Staff;
import source.user.Student;


/**
 * The entry point to the Camp Application and Management System (CAMS).
 */
public class CAMSApp {

    protected static final String STUDENT_FILE_PATH = "data\\student_list.csv";
    protected static final String STAFF_FILE_PATH = "data\\staff_list.csv";
    protected static final String CAMP_FILE_PATH = "data\\camps_list.csv";
    protected static final String CAMP_MEMBERS_FILE_PATH = "data\\camp_members.csv";
    protected static final String ENQUIRIES_FILE_PATH = "data\\enquiries.csv";
    protected static final String SUGGESTIONS_FILE_PATH = "data\\suggestions.csv";

    protected static ArrayList<Staff> staffs = new ArrayList<Staff>();
    protected static ArrayList<Camp> camps = new ArrayList<Camp>();
    protected static ArrayList<Student> students = new ArrayList<Student>();

    public static Scanner scanner ;

    public static void main(String[] args) {

        try {

            FileProcessing.readDataFromFile();

            scanner = new Scanner(System.in);
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
                    Student loggedInStudent = authenticateStudent (enteredUserId, enteredPassword);

                    if (loggedInStudent != null) {
                        System.out.println("Student Login successful!");
                        Utility.redirectingPage();
                        StudentInterface.handleStudentFunctionalities(loggedInStudent);
                        break; 
                    } else {
                        System.out.println("Invalid student credentials. Login failed.");
                    }

                } else if (userTypeChoice == 2) {
                    Staff loggedInStaff = authenticateStaff(enteredUserId, enteredPassword);

                    if (loggedInStaff != null) {
                        System.out.println("Staff Login successful!");
                        Utility.redirectingPage();
                        StaffInterface.handleStaffFunctionalities(loggedInStaff);
                        break; 
                    } else {
                        System.out.println("Invalid staff credentials. Login failed.");
                    }
                }

                loginAttempts++;
                if (loginAttempts < 3) {
                    System.out.println("You have " + (3 - loginAttempts) + " attempts remaining.");
                    Utility.redirectingPage();
                }
                else {
                break ;
                }
                
            }
            if (loginAttempts == 3) System.out.println("Maximum login attempts reached. Exiting.") ;
            else {
                System.out.println("Please wait, uploading changes to server. Do not close the program...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println ("Your changes has been saved. Thank you for using the CAMS system.") ;
                FileProcessing.writeDataToFile(); 
            }
            scanner.close();
            
        }
        catch (Exception e) {
            System.out.println(":(") ;
            System.out.println("Sorry, something went wrong on our ends.");
            System.out.println("We are unable to upload the changes you have made.");
            System.out.println("Error message:");
            e.printStackTrace();
        }
    }

    private static Student authenticateStudent(String userId, String password) {
        for (Student student : students) {
            if (student.getUserId().equals(userId) && student.getPassword().equals(password)) {
                return student; 
            }
        }
        return null; 
    }

    private static Staff authenticateStaff(String userId, String password) {
        for (Staff staffMember : staffs) {
            if (staffMember.getUserId().equals(userId) && staffMember.getPassword().equals(password)) {
                return staffMember; 
            }
        }
        return null; 
    }
}

