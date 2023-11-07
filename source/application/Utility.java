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

    private static String extractUserIdFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            return email;
        }
    }

    public static List<Student> readStudentsFromFile(String filePath) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String userName = data[0].trim(); 
                    String email = data[1].trim(); 
                    String faculty = data[2].trim();
                    String password = data[3].trim();

                    Faculty facultyEnum = Faculty.valueOf(faculty) ;
                    
                    // Extract User ID from email
                    String userId = extractUserIdFromEmail(email);

                    // Create a new student and add it to the list
                    Student student = new Student (userName, userId, facultyEnum , password);
                    students.add(student);
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }

    public static List<Staff> readStaffFromFile(String filePath) {
        List<Staff> staffMembers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String userName = data[0].trim();
                    String email = data[1].trim();
                    String faculty = data[2].trim();
                    String password = data[3].trim();
                    String userId = extractUserIdFromEmail(email);

                    Faculty facultyEnum = Faculty.valueOf(faculty) ;

                    // Create a new staff member and add it to the list
                    Staff staffMember = new Staff(userName, userId, facultyEnum, password);
                    staffMembers.add(staffMember);
                } else {
                    System.out.println("Invalid data format in the staff file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return staffMembers;
    }

    public static List<Camp> readCampsFromFile(String filePath) {
        List<Camp> camps = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) {
                    String campName = data[0].trim();
                    String dates = data[1].trim();
                    String registrationClosingDate = data[2].trim();
                    String userGroup = data[3].trim();
                    String location = data[4].trim();
                    int totalSlots = Integer.parseInt(data[5].trim());
                    int campCommitteeSlots = Integer.parseInt(data[6].trim());
                    String description = data[7].trim();
                    String staffInCharge = data[8].trim();
                    String visibility = data[9].trim();

                    // Create new camp and add it to the list
                    Camp camp = new Camp(campName, dates, registrationClosingDate, userGroup,
                            location, totalSlots, campCommitteeSlots, description, staffInCharge, visibility);
                    camps.add(camp);
                } else {
                    // System.out.println("Invalid data format in the camp file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return camps;
    }

    public static List<Enquiry> readEnquiriesFromFile(String filePath) {
        List<Enquiry> enquiries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String studentId = data[0].trim();
                    String campName = data[1].trim();
                    String enquiriesText = data[2].trim();
                    boolean processed = Boolean.parseBoolean(data[3].trim());
                    String processedBy = data[4].trim();
                    String replies ;

                    // Create a new camp and add it to the list
                    Enquiry enquiry = new Enquiry(findCampByName(campName) , findStudentByName(studentId) , enquiriesText , processed , findUserByName(processedBy) , replies) ;
                    enquiries.add(enquiry);
                } else {
                    System.out.println("Invalid data format in the camp file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return enquiries;
    }

    public static List<Suggestion> readSuggestionsFromFile(String filePath) {
        List<Suggestion> suggestions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String studentId = data[0].trim();
                    String campName = data[1].trim();
                    String suggestionsText = data[2].trim();
                    boolean approvalState = Boolean.parseBoolean(data[3].trim());

                    Suggestion suggestion = new Suggestion (findCampByName(campName) , findStudentByName(studentId) , suggestionsText, approvalState);
                    suggestions.add(suggestion);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return suggestions;
    }

}
