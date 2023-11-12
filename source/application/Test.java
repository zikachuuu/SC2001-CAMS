package source.application;

import java.util.ArrayList;
import java.util.Arrays;

import source.camp.Camp;
import source.user.Staff;
import source.user.Student;

public class Test {

    private static final String STUDENT_FILE_PATH = "data\\student_list.csv";
    private static final String STAFF_FILE_PATH = "data\\staff_list.csv";
    private static final String CAMP_FILE_PATH = "data\\camps_list.csv";
    private static final String CAMP_MEMBERS_FILE_PATH = "data\\camp_members.csv";
    private static final String ENQUIRIES_FILE_PATH = "data\\enquiries.csv";
    private static final String SUGGESTIONS_FILE_PATH = "data\\suggestions.csv";

    public static void main(String[] args) {
        ArrayList<Staff> staffs = FileProcessing.readStaffFromFile (STAFF_FILE_PATH) ;
        ArrayList<Camp> camps = FileProcessing.readCampsFromFile(CAMP_FILE_PATH, staffs) ;
        ArrayList<Student> students = FileProcessing.readStudentsFromFile(STUDENT_FILE_PATH, CAMP_MEMBERS_FILE_PATH, camps) ;

    }
}
