package source.user;

/**
 * Represents a user. User is the parent class of Student and Staff (Inheritance).
 * @author Le Yanzhi, Florian Goering
 * @version beta 3 (nah we do not need log in, log in will be handled by main)
 * @since 2023-11-11
 */
public abstract class User {
    /**
     * UserId (The part before '@' in email)
     */
    private String userId ;


    /**
     * Name of user.
     */
    private String userName ;


    /**
     * Faculty this user belongs to.
     */
    private Faculty faculty ;


    /**
     * Password
     */
    private String password ;


    /**
     * Construct a new user object.
     * @param userId
     * @param userName
     * @param faculty
     * @param password
     */
    public User (String userId, String userName, Faculty faculty, String password) {
        this.userId = userId ;
        this.userName = userName ;
        this.faculty = faculty ;
        this.password = password ;
    }

    
    public String getUserName() {return userName ;}
    public String getUserId() {return userId ;}
    public Faculty getFaculty() {return faculty ;}
    public String getPassword() {return password ;}
    protected void setPassword(String newPassword) {this.password = newPassword ;}


    /**
     * @return True if this user is using the default password, false otherwise.
     */
    public abstract boolean isDefaultPassword() ;


    /**
     * Change password (duh).
     * @param oldPassword The old password (to verify).
     * @param newPassword The new password.
     * @return True if successfully changed, false if oldPassword does not match.
     */
    public abstract boolean changePassword (String oldPassword, String newPassword) ;


    /**
     * Check if both users (Staff or student) are the same user, using userId.
     * @param other The other user to compare with.
     * @return True if same user, false otherwise.
     */
    public boolean equals (User other) {
        return this.userId.equals(other.getUserId()) ;
    }


    /**
     * Check if the user has the provided UserId.
     * @param userId
     * @return True if same user, false otherwise.
     */
    public boolean equals (String userId) {
        return this.userId.equals(userId) ;
    }
}
