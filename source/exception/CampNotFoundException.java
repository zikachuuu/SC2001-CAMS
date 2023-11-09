package source.exception;

public class CampNotFoundException extends RuntimeException {
    public CampNotFoundException () {
        super ("No such camp with given camp name!") ;
    }

    public CampNotFoundException (String message) {
        super (message) ;
    }
}