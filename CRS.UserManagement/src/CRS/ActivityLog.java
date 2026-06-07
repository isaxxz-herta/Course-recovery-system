package CRS;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ActivityLog implements Serializable {
    private String logID;
    private User user;
    private String action;
    private Date timestamp;
    private String details;
    
    public ActivityLog(User user, String action, String details) {
        this.logID = UUID.randomUUID().toString();
        this.user = user;
        this.action = action;
        this.timestamp = new Date();
        this.details = details;
    }
    
    // Static method to record activity
    public static void record(User user, String action, String details) {
        ActivityLog log = new ActivityLog(user, action, details);
        ActivityLogFileHandler.saveLog(log);
    }
    

    
    // Getters
    public String getLogID() {
        return logID;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getAction() {
        return action;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public String getDetails() {
        return details;
    }
    
    // Setters
    public void setDetails(String details) {
        this.details = details;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s (%s)", 
            timestamp, user.getName(), action, details, logID);
    }
}