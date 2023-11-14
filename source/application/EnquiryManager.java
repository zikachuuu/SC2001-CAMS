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

    
    /**
     * FInd all enquries that all students have submitted.
     * @param notReplied True to only find those that have not replied, False to find all.
     * @return ArrayList of enquiries.
     */
    protected static ArrayList<Enquiry> findAllEnquiry(boolean notReplied) {
        ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>() ;
        
        for (Camp camp : CAMSApp.camps) {
            if (! camp.getActive()) continue ;

            for (Enquiry enquiry : camp.getEnquiries()) {
                if(! enquiry.isActive() || (notReplied && enquiry.getReplied())) continue ;
                enquiries.add(enquiry) ;
            }
        }
        return enquiries ;
    }


    /**
     * Find all enquiries that a student has submitted.
     * @param student The student who submitted the enquiry.
     * @param notReplied True to only find those that have not replied, False to find all.
     * @return ArrayList of enquiries.
     */
    protected static ArrayList<Enquiry> findAllEnquiry (Student student, boolean notReplied) {

        ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>() ;
        
        for (Camp camp : CAMSApp.camps) {
            if (! camp.getActive()) continue ;

            for (Enquiry enquiry : camp.getEnquiries()) {
                if (! enquiry.isSubmittedBy(student) || ! enquiry.isActive() || (notReplied && enquiry.getReplied())) continue ;
                enquiries.add(enquiry) ;
            }
        }
        return enquiries ;
    }



    public static void viewEnquiry (Student student) {
        System.out.println("List of enquiries that you have submitted:\n");
        ArrayList<Enquiry> enquiries = findAllEnquiry(student , false) ;

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
