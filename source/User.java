package source;

public class User {
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

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getPassword() {
        return password;
    }
    
}
