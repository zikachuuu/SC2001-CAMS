package source.application;

import java.time.DateTimeException;
import java.time.LocalDate;


/**
 * Utility class to provide commonly used static methods for all other classes. 
 */
public class Utility {

    /**
     * Clears the console (for aesthetic purposes).
     */
    public static void clearConsole() {
        for(int i = 0; i<100;i++)
            System.out.println();
    }


    /**
     * Does the following 3 things: <p>
     * 1) print out "redirecting..." message <p>
     * 2) sleep for 3 seconds<p>
     * 3) clears the console
     */
    public static void redirectingPage() {
        System.out.println ("Please wait, redirecting...") ;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearConsole() ;
    }

    
    /**
     * Convert a string date in the format of yyyy-mm-dd to a LocalDate object
     * @param date String in the format of yyyy-mm-dd
     * @return LocalDate object
     * @throws DateTimeException If string date provided is of the wrong format.
     */
    public static LocalDate convertStringToLocalDate(String date) {
        try {
            String[] dateSplitted = date.split("-") ;
            LocalDate newDate = LocalDate.of (Integer.valueOf(dateSplitted[0].length() == 4 ? dateSplitted[0] : "-1") , Integer.valueOf(dateSplitted[1]) , Integer.valueOf(dateSplitted[2])) ;
            return newDate ;
        } catch (Exception e) {
            throw new DateTimeException(date) ;
        }
    }


    /**
     * Comma in string will f up the csv real bad. Call this method whenever there is a string input.
     * @param string The string to replace.
     * @return The new string.
     */
    public static String replaceCommaWithSemicolon(String string) {
        return string.replaceAll(",", ";") ;
    }

}
