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

    protected static void handlePasswordChange(User user) {
        System.out.println("Enter your current password:");
        String currentPassword = CAMSApp.scanner.nextLine();
        System.out.println("Enter a new password:");
        String newPassword = CAMSApp.scanner.nextLine();
        newPassword = Utility.replaceCommaWithSemicolon(newPassword);

        if (user.changePassword(currentPassword, newPassword)) {
            System.out.println("Password changed successfully");
        } else {
            System.out.println("Incorrect current password entered. Password unchanged.");
        }

    }
}
