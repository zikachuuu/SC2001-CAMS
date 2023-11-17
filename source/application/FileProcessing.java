package source.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import source.camp.Camp;
import source.camp.CampInformation;
import source.camp.Enquiry;
import source.camp.Suggestion;
import source.user.Faculty;
import source.user.Student;
import source.user.Staff;
import source.user.User;


public class FileProcessing {

    public static void readDataFromFile () {
        readStaffsFromFile() ;
        readCampsFromFile() ;
        readStudentsFromFile() ;
        readEnquiriesFromFile() ;
        readSuggestionsFromFile() ;
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
     * This method generates the default Camp object, without any participants, withdrawn participants, enquires, and suggestions under it.
     * Use readStudentsFromFile(), readEnquiriesFromFile(), readSuggestionsFromFile() to restore them.
     */
    private static void readCampsFromFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.CAMP_FILE_PATH))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 13) { 
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
                    int numAttendees = 0 ;
                    int numCommittees = 0 ; // they will be incremented later by restoreParticipant()
                    String description = data[10].trim();

                    String staffInChargeUserId = data[11].trim();
                    Staff staffInCharge = UserManager.findStaffByUserId (staffInChargeUserId) ;

                    boolean visible = data[12].trim().equals("visible"); 

                    // Create new camp and add it to the list
                    Camp camp = new Camp(new CampInformation(campName, startDate, endDate, registrationClosingDate, userGroup, location, totalSlots, campCommitteeSlots, description, staffInCharge), numCommittees , numAttendees, visible) ;
                    CAMSApp.camps.add(camp) ;
                    
                    // Add the camp to the list of created camps in that staff
                    restoreCreatedCamp(staffInCharge, camp);

                } else {
                    System.out.println("Invalid data format in the camp file: " + line);
                    System.exit(-1);
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
                    System.exit(-1);
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

                    Student student = UserManager.findStudentByUserId(studentId) ;
                    restoreCampRole(student, CampManager.findCampByName(campName), isCommittee, active, points);
                    
                } else {
                    System.out.println("Invalid data format in the file: " + line);
                    System.exit(-1);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    /**
     * Read from enquiries.csv to restore the enquries in camps.
     */
    private static void readEnquiriesFromFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.ENQUIRIES_FILE_PATH))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String campName = data[0].trim();
                    String studentId = data[1].trim();
                    String enquiriesText = data[2].trim();
                    boolean processed = Boolean.parseBoolean(data[3].trim());
                    String processedBy = data[4].trim();
                    String replies = data[5].trim() ;

                    Camp camp = CampManager.findCampByName(campName) ;
                    Student student = UserManager.findStudentByUserId(studentId) ;
                    User user ;
                    if (processed) user = UserManager.findUserByUserId(processedBy) ;
                    else user = null ;
                    restoreEnquiry(camp , new Enquiry(camp, student, enquiriesText , processed , user , replies));

                } else {
                    System.out.println("Invalid data format in the camp file: " + line);
                    System.exit(-1);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    /**
     * Read from suggestions.csv to restore suggestions in camps.
     */
    private static void readSuggestionsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMSApp.SUGGESTIONS_FILE_PATH))) {
            String line;
            br.readLine() ; // first line is heading
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String campName = data[0].trim();
                    String studentId = data[1].trim();
                    String suggestionsText = data[2].trim();
                    boolean approvalState = Boolean.parseBoolean(data[3].trim());

                    Camp camp = CampManager.findCampByName(campName) ;
                    Student student = UserManager.findStudentByUserId(studentId) ;
                    restoreSuggestion(camp , new Suggestion(camp, student, suggestionsText, approvalState));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    /**
     * Restore a previously created camps back to the staff's createdCamps ArrayList.
     * @param staff
     * @param camp
     */
    private static void restoreCreatedCamp (Staff staff , Camp camp) {
        staff.addCreatedCamps(camp);
    }


    /**
     * Restore a previous camp role of a student.
     * @param student
     * @param camp
     * @param committeeRole True for committee, false for attendee.
     * @param active True for active role, false for withdrawn role.
     * @param points Points (for committee).
     */
    private static void restoreCampRole (Student student , Camp camp , boolean committeeRole , boolean active , int points) {
        
        restoreParticipant(camp, student, committeeRole, active);
        if (committeeRole) student.addCampCommittee(camp , 0) ;
        else if (active) student.addCampAttendee(camp); 
    }


    /**
     * Restore a previous participant back into a camp.
     * @param camp
     * @param student
     * @param committeeRole True for committee, false for attendee.
     * @param active True if student is currently in camp, false if student has withdrawn.
     */
    private static void restoreParticipant(Camp camp, Student student, boolean committeeRole , boolean active) {
        if (active) camp.addParticipant(student, committeeRole); 
        else camp.addWithdrawnParticipant(student);
    }


    /**
     * Restore a previous enquiry back to a camp.
     * @param camp
     * @param enquiry
     */
    private static void restoreEnquiry(Camp camp, Enquiry enquiry) {
        camp.addEnquiry(enquiry);
    }


    /**
     * Restore a previous suggestion back to a camp.
     * @param camp
     * @param suggestion
     */
    private static void restoreSuggestion(Camp camp, Suggestion suggestion) {
        camp.addSuggestion(suggestion);
    }


    protected static void writeDataToFile() {
        writeStaffsToFile() ;
        writeCampsToFile() ;
        writeStudentsToFile();
        writeEnquiriesToFile();
        writeSuggestionsToFiles();
    }


    private static void writeStaffsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMSApp.STAFF_FILE_PATH, false))) {
            writer.write ("Name,Email@e.ntu.edu.sg,Faculty,Password\r\n") ;

            for (Staff staff : CAMSApp.staffs) {
                writer.write(
                    staff.getUserName() + "," + 
                    staff.getUserId() + "@e.ntu.edu.sg," + 
                    staff.getFaculty().toString() + "," + 
                    staff.getPassword()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void writeCampsToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMSApp.CAMP_FILE_PATH, false))) {
            writer.write("CampName, StartDate, EndDate, RegistrationClosingDate,UserGroup, Location,TotalSlots, CampCommitteeSlots, NumAttendees, NumCommittees, Description, StaffInCharge,Visibility\r\n");
            
            for (Camp camp : CAMSApp.camps) {
                // Write each camp's details to the file
                if (! camp.isActive()) continue ;

                writer.write(
                    camp.getCampInfo().getCampName() + "," + 
                    camp.getCampInfo().getStartDate().toString() + "," + 
                    camp.getCampInfo().getEndDate().toString() + "," + 
                    camp.getCampInfo().getRegistrationClosingDate().toString() + "," + 
                    camp.getCampInfo().getUserGroup().toString() + "," + 
                    camp.getCampInfo().getLocation() + "," +
                    camp.getCampInfo().getTotalSlots() + "," +
                    camp.getCampInfo().getCampCommitteeSlots() + "," +
                    camp.getNumAttendees() + "," +
                    camp.getNumCommittees() + "," +
                    camp.getCampInfo().getDescription() + "," +
                    camp.getCampInfo().getStaffInCharge().getUserId()+ "," +
                    (camp.isVisible() == true ? "visible" : "unvisible" )
                ) ;
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMSApp.STUDENT_FILE_PATH, false))) {
            writer.write ("Name,Email@e.ntu.edu.sg,Faculty,Password\r\n") ;

            for (Student student : CAMSApp.students) {
                writer.write(
                    student.getUserName() + "," + 
                    student.getUserId() + "@e.ntu.edu.sg," + 
                    student.getFaculty().toString() + "," + 
                    student.getPassword()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static void writeEnquiriesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMSApp.ENQUIRIES_FILE_PATH , false))) {
            writer.write("Camp,Student,EnquiryText,Processed,ProcessedBy,Replies\r\n");
            ArrayList<Enquiry> enquiries = EnquiryManager.findAllEnquiry(false) ;

            for (Enquiry enquiry : enquiries) {
                writer.write(
                    enquiry.getCamp().getCampInfo().getCampName() + "," + 
                    enquiry.getStudent().getUserId() + "," + 
                    enquiry.getContent() + "," + 
                    Boolean.toString(enquiry.isReplied())  + "," + 
                    (enquiry.isReplied() ? enquiry.getRepliedBy().getUserId() : "null") + "," +
                    enquiry.getReplies()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void writeSuggestionsToFiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMSApp.SUGGESTIONS_FILE_PATH , false))) {
            writer.write("Camp,Student,Suggestion,Approved\r\n");
            ArrayList<Suggestion> suggestions = SuggestionManager.findAllSuggestions() ;

            for (Suggestion suggestion : suggestions) {
                writer.write(
                    suggestion.getCamp().getCampInfo().getCampName() + "," +
                    suggestion.getStudent().getUserId() + "," +
                    suggestion.getContent() + "," +
                    Boolean.toString(suggestion.isApproved()) 
                );
                writer.newLine();
            }

        } catch (IOException e) {
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
