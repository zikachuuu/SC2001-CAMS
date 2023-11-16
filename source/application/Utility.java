package source.application;

import java.time.DateTimeException;
import java.time.LocalDate;

import source.exception.UserNotFoundException;
import source.user.Staff;
import source.user.Student;
import source.user.User;


/**
 * Utility class to provide commonly used static methods for all other classes. 
 */
public class Utility {

    /**
     * Clears the console (for aesthetic purposes).
     */
    public static void clearConsole() {
        System.out.print("\033\143");
    }


    /**
     * Does the following 3 things: <p>
     * 1) print out "redirecting..." message <p>
     * 2) sleep for 3 seconds<p>
     * 3) clears the console
     */
    public static void redirectingPage() {
        System.out.println ("Please wait, redirecting...") ;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearConsole() ;
    }


    /**
     * Find the user object using the userId provided.
     * @param userId 
     * @return User object (can be downcasted to either staff or student).
     * @throws UserNotFoundException If user not found for the provided Id.
     */
    public static User findUserByUserId (String userId) {

        User user ;
        try {
            user = findStaffByUserId(userId) ;
        } catch (UserNotFoundException e1) {
            try {
                user = findStudentByUserId(userId) ;
            } catch (UserNotFoundException e2) {
                throw new UserNotFoundException("User not found for " + userId) ;
            }
        }
        return user ;
    }


    /**
     * Find the student object using the userId provided.
     * @param userId 
     * @return Student object.
     * @throws UserNotFoundException If student not found for the provided Id.
     */
    public static Student findStudentByUserId (String userId) {
        for (Student student : CAMSApp.students) {
            if (student.getUserId().equals(userId)) return student ;
        }
        throw new UserNotFoundException("Student not found for " + userId) ;
    }

    
    /**
     * Find the staff object using the userId provided.
     * @param userId
     * @return Staff object.
     * @throws UserNotFoundException If staff not found for the provided Id.
     */
    public static Staff findStaffByUserId (String userId) {
        for (Staff staff : CAMSApp.staffs) {
            if (staff.getUserId().equals(userId)) return staff ;
        }
        throw new UserNotFoundException("Staff not found for " + userId) ;
    }

    
    /**
     * Convert a string date in the format of yyyy-mm-dd to a LocalDate object
     * @param date String in the format of yyyy-mm-dd
     * @return LocalDate object
     * @throws DateTimeException If string date provided is of the wrong format.
     */
    public static LocalDate convertStringToLocalDate(String date) {
        try {
            String[] dateSplitted = date.split("-") ;
            LocalDate newDate = LocalDate.of (Integer.valueOf(dateSplitted[0].length() == 4 ? dateSplitted[0] : "-1") , Integer.valueOf(dateSplitted[1]) , Integer.valueOf(dateSplitted[2])) ;
            return newDate ;
        } catch (Exception e) {
            throw new DateTimeException(date) ;
        }
    }


    /**
     * Comma in string will fk up the csv real bad. Call this method whenever there is a string input.
     * @param string The string to replace.
     * @return The new string.
     */
    public static String replaceCommaWithSemicolon(String string) {
        return string.replaceAll(",", ";") ;
    }

}
