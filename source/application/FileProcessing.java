package source.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import source.camp.Camp;
import source.camp.CampInformation;
import source.user.Faculty;
import source.user.Student;
import source.user.Staff;


public class FileProcessing {

    /**
     * Read from staff_list.csv to generate the staff arrayList. <p> 
     * This methods generates the default Staff object, without any created camps under him. 
     * Use readCampsFromFile() to restore the created camps.
     * @param filePath File path for staff_list.csv
     * @return ArrayList of Staff
     */
    public static ArrayList<Staff> readStaffFromFile(String filePath) {
        ArrayList<Staff> staffMembers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
                    staffMembers.add(staffMember);
                } else {
                    System.out.println("Invalid data format in the staff file: " + line);
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return staffMembers;
    }


    /**
     * Read from camps_list.csv to generate the camp arrayList, and restores the created camps in staff. <p>
     * This method generates the default Camp object, without any participants or withdrawn participants under it.
     * Use readStudentsFromFile() to restore them. <p>
     * Note that staffs arrayList is passed by reference, so the staffs arrayList will be updated as well.
     * @param campFilePath File path for camps_list.csv
     * @param staffs ArrayList of staffs to update.
     * @return ArrayList of Camp
     */
    public static ArrayList<Camp> readCampsFromFile(String campFilePath , ArrayList<Staff> staffs) {
        ArrayList<Camp> camps = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(campFilePath))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 9) { 
                    String campName = data[0].trim();

                    String startDateString = data[1].trim();
                    LocalDate startDate = convertStringToLocalDate(startDateString) ;

                    String endDateString = data[2].trim() ;
                    LocalDate endDate = convertStringToLocalDate(endDateString) ;

                    String registrationClosingDateString = data[3].trim();
                    LocalDate registrationClosingDate = convertStringToLocalDate(registrationClosingDateString) ;

                    String userGroupString = data[4].trim();
                    Faculty userGroup = Faculty.valueOf(userGroupString) ;

                    String location = data[5].trim();
                    int totalSlots = Integer.parseInt(data[6].trim());
                    int campCommitteeSlots = Integer.parseInt(data[7].trim());
                    int numAttendees = Integer.parseInt(data[8].trim());
                    int numCommittees = Integer.parseInt(data[9].trim()) ;
                    String description = data[10].trim();

                    String staffInChargeString = data[11].trim();
                    int index = 0 ;
                    for (index = 0 ; index < staffs.size() ; index++) {
                        if (staffs.get(index).getUserId() == staffInChargeString) break ;
                    }

                    if (index == 5) {
                        system.out.printf ("No staff found for %s" , staffInChargeString) ;
                    }

                    boolean visible = data[12].trim().equals("visible"); 


                    // Create new camp and add it to the list
                    Camp camp = new Camp(new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, staffs.get(index), visible), numCommittees , numAttendees) ;
                    camps.add(camp) ;
                    
                    // Add the camp to the list of created camps in that staff
                    staffs.get(index).addCamp(camp);

                } else {
                    System.out.println("Invalid data format in the camp file: " + line);
                    System.exit(0);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return camps;
    }

    
    /**
     * Read from student_list.csv and camp_members.csv to generate the student arraylist, and restore the participants and withdrawn participants in camp.
     * @param studentFilePath File path of student_list.csv.
     * @param participantsFilePath File path of camp_members.csv.
     * @param camps ArrayList of camps to update
     * @return ArrayList of students.
     */
    public static ArrayList<Student> readStudentsFromFile (String studentFilePath, String participantsFilePath, ArrayList<Camp> camps) {
        ArrayList<Student> students = new ArrayList<Student>() ;


        try (BufferedReader br = new BufferedReader(new FileReader(studentFilePath))) {
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
                    students.add(student);
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                    System.exit(0);
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
                        
                        student.addCampRole(campName, isCommittee, points);
                        break ;
                    }
                    
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                    System.exit(0);
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

    /**
     * Convert a string date in the format of dd/mm/yyyy to a LocalDate object
     * @param date String in the format of dd/mm/yyyy
     * @return LocalDate object
     */
    private static LocalDate convertStringToLocalDate(String date) {
        String[] dateSplitted = date.split("/") ;
        LocalDate newDate = LocalDate.of (Integer.valueOf(dateSplitted[2]) , Integer.valueOf(dateSplitted[1]) , Integer.valueOf(dateSplitted[0])) ;
        return newDate ;
    }
}
