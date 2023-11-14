package source.application;

import java.util.ArrayList;

import source.camp.Camp;
import source.camp.Enquiry;
import source.exception.CampNotFoundException;
import source.user.Student;

public class EnquiryManager {

    /**
     * Add enquiry to a camp.
     * @param campName
     * @param content
     * @param student
     * @throws CampNotFoundException
     */
    public static void addEnquiryToCamp(String campName, String content, Student student) {
        Camp camp = Utility.findCampByName(campName) ;
        camp.addEnquiry(student, new Enquiry(camp, student, content));
    }


    protected static ArrayList<Enquiry> findAllEnquiry() {
        ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>() ;
        
        for (Camp camp : CAMSApp.camps) {
            if (! camp.getActive()) continue ;

            for (Enquiry enquiry : camp.getEnquiries()) {
                if(enquiry.getActive()) {
                    enquiries.add(enquiry) ;
                }
            }
        }
        return enquiries ;
    }


    /**
     * Find all enquiries that a student has submitted.
     * @param student The student who submitted the enquiry.
     * @return ArrayList of enquiries.
     */
    protected static ArrayList<Enquiry> findAllEnquiry (Student student) {

        ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>() ;
        
        for (Camp camp : CAMSApp.camps) {
            if (! camp.getActive()) continue ;

            for (Enquiry enquiry : camp.getEnquiries()) {
                if(enquiry.isSubmittedBy(student) && enquiry.getActive()) {
                    enquiries.add(enquiry) ;
                }
            }
        }
        return enquiries ;
    }


    public static void viewEnquiry (Student student) {
        System.out.println("List of enquiries that you have submitted:\n");
        ArrayList<Enquiry> enquiries = findAllEnquiry(student) ;

        if (enquiries.size() == 0) {
            System.out.println("You have not submitted any enquiries.") ;
        }
        else {
            for (Enquiry enquiry : enquiries) {
                enquiry.viewEnquiry();
                System.out.println();
            }
        }
    }
}
