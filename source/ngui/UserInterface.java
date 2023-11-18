package source.ngui;

import source.application.CAMSApp;
import source.application.Utility;
import source.user.User;

public abstract class UserInterface {

    protected boolean exit = false ;

    /**
    * Returns user to menu if M.
    * Exits programme if other key.
    */
    protected void offerReturnToMenuOption() {
        System.out.print("Press 'M' to go back to the menu or any other key to exit: ");
        String backChoice = CAMSApp.scanner.nextLine();
        if (!"M".equalsIgnoreCase(backChoice)) {
            exit = true;
        }
    }

    /**
    * Changes password of user.
    * @param user The user who wants to change the password.
    * @return False if reentered password does not match new password.
    * @return True if password is changed successfully.
    * @return False if current password is entered incorrectly.
    */
    protected boolean handlePasswordChange(User user) {
        System.out.print("Enter your current password: ");
        String currentPassword = CAMSApp.scanner.nextLine();

        System.out.print("Enter a new password: ");
        String newPassword = CAMSApp.scanner.nextLine();
        newPassword = Utility.replaceCommaWithSemicolon(newPassword);

        System.out.print("Reenter your new password: ") ;
        if (! newPassword.equals ( Utility.replaceCommaWithSemicolon (CAMSApp.scanner.nextLine()) )) {
            System.out.println("Reentered password does not match your new password. Password unchanged.");
            return false ;
        }

        if (user.changePassword(currentPassword, newPassword)) {
            System.out.println("Password changed successfully! Note that all comma (,) in your password has been automatically replaced with semicolon (;).");
            return true ;
        } else {
            System.out.println("Incorrect current password entered. Password unchanged.");
            return false ;
        }
    }

    
    abstract protected void handleDefaultPasswordChange (User user) ;
}
