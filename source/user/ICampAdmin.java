package source.user;

import java.time.LocalDate;

public interface ICampAdmin {

    public boolean createCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) ;
    public void deleteCamp (String campName) ;
    public void editCamp (String campName , LocalDate startDate , LocalDate endDate , LocalDate registrationClosingDate , Faculty userGroup , String location , int totalSlots , int campCommitteeSlots , String description) ;
    public void viewCreatedCamps () ;
    public boolean toggleVisibility(String campName) ;
}
