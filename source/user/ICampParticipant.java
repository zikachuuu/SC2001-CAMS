package source.user;

public interface ICampParticipant {
    
    public void registerForCamp (String campName , boolean committeeRole) ;
    public boolean withdrawFromCamp (String campName) ;
}
