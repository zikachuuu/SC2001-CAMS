package source.ngui;

import source.user.User;

public interface ICampAdminInterface {
    public void handleCampAdd(User user) ;
    public void handleCampEdit(User user) ;
    public void handleCampToggle (User user) ;
    public void handleCampDelete(User user) ;
}
