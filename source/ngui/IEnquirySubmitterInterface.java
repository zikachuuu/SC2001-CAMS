package source.ngui;

import source.user.User;

public interface IEnquirySubmitterInterface {
    public void handleSubmitEnquiry(User user) ;
    public void handleEditEnquiry(User user) ;
    public void handleDeleteEnquiry(User user) ;
}
