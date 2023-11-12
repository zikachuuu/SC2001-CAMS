package source.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import source.camp.Camp;
import source.camp.CampInformation;
import source.user.Faculty;
import source.user.Student;
import source.user.Staff;


public class FileProcessing {


    public static void readDataFromFile () {
        readStaffsFromFile() ;
        readCampsFromFile() ;
        readStudentsFromFile() ;
    }

    /**
     * Read from CAMSApp.STAFF_FILE_PATH to generate the staff arrayList. <p> 
     * This methods generates the default Staff object, without any created camps under him. 
     * Use readCampsFromFile() to restore the created camps.
     */
    private static void readStaffsFromFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.STAFF_FILE_PATH))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String userName = data[0].trim(); 
                    String email = data[1].trim(); 
                    String facultyString = data[2].trim();
                    String password = data[3].trim();
                    
                    // Extract User ID from email
                    String userId = extractUserIdFromEmail(email);

                    Faculty faculty = Faculty.valueOf(facultyString) ;

                    // Create a new staff member and add it to the list
                    Staff staffMember = new Staff (userId , userName, faculty, password);
                    CAMSApp.staffs.add(staffMember);
                } else {
                    System.out.println("Invalid data format in the staff file: " + line);
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Read from camps_list.csv to generate the camp arrayList, and restores the created camps in staff. <p>
     * This method generates the default Camp object, without any participants or withdrawn participants under it.
     * Use readStudentsFromFile() to restore them.
     */
    private static void readCampsFromFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.CAMP_FILE_PATH))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) { 
                    String campName = data[0].trim();

                    String startDateString = data[1].trim();
                    LocalDate startDate = Utility.convertStringToLocalDate(startDateString) ;

                    String endDateString = data[2].trim() ;
                    LocalDate endDate = Utility.convertStringToLocalDate(endDateString) ;

                    String registrationClosingDateString = data[3].trim();
                    LocalDate registrationClosingDate = Utility.convertStringToLocalDate(registrationClosingDateString) ;

                    String userGroupString = data[4].trim();
                    Faculty userGroup = Faculty.valueOf(userGroupString) ;

                    String location = data[5].trim();
                    int totalSlots = Integer.parseInt(data[6].trim());
                    int campCommitteeSlots = Integer.parseInt(data[7].trim());
                    int numAttendees = Integer.parseInt(data[8].trim());
                    int numCommittees = Integer.parseInt(data[9].trim()) ;
                    String description = data[10].trim();

                    String staffInChargeUserId = data[11].trim();
                    Staff staffInCharge = Utility.findStaffByUserId (staffInChargeUserId) ;

                    boolean visible = data[12].trim().equals("visible"); 

                    // Create new camp and add it to the list
                    Camp camp = new Camp(new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, staffInCharge, visible), numCommittees , numAttendees) ;
                    CAMSApp.camps.add(camp) ;
                    
                    // Add the camp to the list of created camps in that staff
                    staffInCharge.restoreCreatedCamp(camp);

                } else {
                    System.out.println("Invalid data format in the camp file: " + line);
                    System.exit(0);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Read from student_list.csv and camp_members.csv to generate the student arraylist, 
     * and restore the participants and withdrawn participants in camp.
     */
    private static void readStudentsFromFile () {

        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.STUDENT_FILE_PATH))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String userName = data[0].trim(); 
                    String email = data[1].trim(); 
                    String facultyString = data[2].trim();
                    String password = data[3].trim();
                    
                    // Extract User ID from email
                    String userId = extractUserIdFromEmail(email);

                    Faculty faculty = Faculty.valueOf(facultyString) ;

                    // Create a new student and add it to the list
                    Student student = new Student(userId, userName,  faculty , password);
                    CAMSApp.students.add(student);
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.CAMP_MEMBERS_FILE_PATH))) {
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

                    Student student = Utility.findStudentByUserId(studentId) ;
                    student.restoreCampRole(campName, isCommittee, active , points);
                    
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                    System.exit(0);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
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
