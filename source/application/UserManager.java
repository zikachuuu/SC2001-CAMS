package source.application;

import source.exception.UserNotFoundException;
import source.user.Staff;
import source.user.Student;
import source.user.User;

/**
 * Manage users.
 */
public class UserManager {
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
}
