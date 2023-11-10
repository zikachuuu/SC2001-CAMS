package source.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import source.camp.Camp;
import source.camp.Enquiry;
import source.camp.Suggestion;
import source.user.Faculty;
import source.user.Staff;
import source.user.Student;
import source.user.User;

public class Utility {

    /**
     * Clears the console (for aesthetic)
     */
    public static void clearConsole() {
        System.out.print("\033\143");
    }
    
    public static Camp findCampByName(String campName) {
        return null ;
    }

    public static User findUserByName (String userId) {

        User user ;
        if ((user = findStaffByName(userId)) != null) return user ;
        if ((user = findStudentByName(userId)) != null) return user ;
        return null ;
    }

    public static Student findStudentByName (String studentId) {
        return null ;
    }

    public static Staff findStaffByName (String staffId) {
        return null ;
    }

}
