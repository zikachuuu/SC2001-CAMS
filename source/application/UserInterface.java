package source.application;

import source.user.User;

public class UserInterface {

    protected static boolean exit = false ;
    
    protected static void offerReturnToMenuOption() {
        System.out.print("Press 'M' to go back to the menu or any other key to exit: ");
        String backChoice = CAMSApp.scanner.nextLine();
        if (!"M".equalsIgnoreCase(backChoice)) {
            exit = true;
        }
    }

    protected static boolean handlePasswordChange(User user) {
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

    protected static void handleDefaultPasswordChange (User user) {
        
        System.out.println ("You are using the default password. Please change your password before proceeding.") ;
        System.out.println() ;

        while (! handlePasswordChange (user)) {
            System.out.print ("Press 'M' to try again or any other key to exit: ") ;

            String backChoice = CAMSApp.scanner.nextLine();
            if (!"M".equalsIgnoreCase(backChoice)) {
                exit = true;
                return ;
            }
            System.out.println();
        }
        Utility.redirectingPage();
    }
}
