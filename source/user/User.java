package source.user;

/**
 * Represents a user. User is the parent class of Student and Staff (Inheritance).
 * @author Le Yanzhi, Florian Goering
 * @version beta 3 (nah we do not need log in, log in will be handled by main)
 * @since 2023-11-11
 */
public abstract class User {
    private String userId ;
    private String userName ;
    private Faculty faculty ;
    private String password ;

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

    public abstract boolean isDefaultPassword() ;
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
