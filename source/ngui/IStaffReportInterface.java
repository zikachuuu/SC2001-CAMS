package source.ngui;

import source.user.User;

/**
 * Represents a interface for abstract methods that generate staff report
 * @author Le Yan Zhi
 * @version 1
 * @since 2023-11-19
 */
public interface IStaffReportInterface extends IReportInterface {
    /**
     * Create a method that generate performance report of camp committee members for staff 
     * @param user
     */
    public void generatePerformanceReport(User user) ;
}
