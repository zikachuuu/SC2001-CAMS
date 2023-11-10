package source.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import source.camp.Camp;
import source.user.Faculty;
import source.user.Student;

public class FileProcessing {

    /**
     * todo
     * @param filePath
     * @return
     */
    public static ArrayList<Camp> readCampsFromFile(String filePath) {
        ArrayList<Camp> camps = new ArrayList<>();

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
                } else {
                    //System.out.println("Invalid data format in the camp file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return camps;
    }

    
    /**
     * (todo) Read from student_list.csv and camp_members.csv to generate the student arraylist.
     * @param studentFilePath File path of student_list.csv.
     * @param participantsFilePath File path of camp_members.csv.
     * @return ArrayList of students.
     */
    public static ArrayList<Student> readStudentsFromFile (String studentFilePath, String participantsFilePath) {
        ArrayList<Student> students = new ArrayList<Student>() ;


        try (BufferedReader br = new BufferedReader(new FileReader(studentFilePath))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String userName = data[0].trim(); 
                    String email = data[1].trim(); 
                    String faculty = data[2].trim();
                    String password = data[3].trim();
                    
                    // Extract User ID from email
                    String userId = extractUserIdFromEmail(email);

                    Faculty facultyEnum = Faculty.valueOf(faculty) ;

                    // Create a new student and add it to the list
                    Student student = new Student(userId, userName,  facultyEnum , password);
                    students.add(student);
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(participantsFilePath))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String studentId = data[0].trim();
                    String campName = data[1].trim();
                    boolean isCommittee = data[2].trim().equalsIgnoreCase("committee");
                    boolean active = data[3].trim().equalsIgnoreCase("active");
                    int points = Integer.parseInt(data[4].trim());

                    for (Student student : students) {
                        if (student.getUserId() != studentId) continue ;
                        
                        // todo
                        break ;
                    }
                    
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return students ;
    }

    /**
     * Extract out the userId from the email provided. UserId is the part before '@' 
     * @param email The email to extract from.
     * @return userId as string.
     */
    private static String extractUserIdFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            return email;
        }
    }
}
