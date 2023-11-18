package source.ngui;

import source.user.User;

public interface IReportInterface {
    
    public void generateCommitteeReport(User user) ;
    public void generateParticipantsReport (User user) ;
    public void generateEnquiryReport (User user) ;
}
