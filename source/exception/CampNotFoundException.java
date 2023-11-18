package source.exception;

/**
 * Represents exception for when camp is not found 
 * @author daryltan
 * @version 1
 * @since 2023-11-19
 */
public class CampNotFoundException extends RuntimeException {
    /**
     * Create an exception for when Camp is not found and prints message for user 
     * @param message 
     */
    public CampNotFoundException () {
        super ("No such camp with given camp name!") ;
    }

    public CampNotFoundException (String message) {
        super (message) ;
    }
}
