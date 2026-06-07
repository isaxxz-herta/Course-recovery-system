package CRS;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class User implements Serializable {
    private String userID;
    private String name;
    private String passwordHash;
    private String email;
    private String role; //"Admin","Officer","Student"
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime lastPasswordChange;
    private int failedLoginAttempts;
    private boolean accountLocked;
    private Date lastLogin;
    
    public User(String userID, String name, String passwordHash, String email, String role, boolean isActive){
        this.userID = userID;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
        this.createdDate = LocalDateTime.now();
        this.lastPasswordChange = LocalDateTime.now();
        this.failedLoginAttempts = 0;
        this.accountLocked = false;
        this.lastLogin = null;
    }
    
    //Getter & Setter
    public String getUserID(){
        return userID;
    }
    
    public String getName(){
        return name;
    }
    
    public String getPasswordHash(){
        return passwordHash;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getRole(){
        return role;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public Date getLastLogin(){
        return lastLogin;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
        this.lastPasswordChange = LocalDateTime.now();
    }
    
    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
    
    public void setLastLogin(Date lastLogin){
        this.lastLogin = lastLogin;
    }
    
    public LocalDateTime getCreatedDate(){
        return createdDate;
    }
    
    public LocalDateTime getLastPasswordChange(){
        return lastPasswordChange;
    }
    
    public int getFailedLoginAttempts(){
        return failedLoginAttempts;
    }
    
    public void setFailedLoginAttempts(int attempts){
        this.failedLoginAttempts = attempts;
    }
    
    public boolean isAccountLocked(){
        return accountLocked;
    }
    
    public void setAccountLocked(boolean locked){
        this.accountLocked = locked;
    }
    
    public void incrementFailedAttempts(){
        this.failedLoginAttempts++;
        if(this.failedLoginAttempts >= 3){ // Default max attempts, can be configured via AuthenticationSystem
            this.accountLocked = true;
        }
    }
    
    public void incrementFailedAttempts(int maxAttempts){
        this.failedLoginAttempts++;
        if(this.failedLoginAttempts >= maxAttempts){
            this.accountLocked = true;
        }
    }
    
    public void resetFailedAttempts(){
        this.failedLoginAttempts = 0;
        this.accountLocked = false;
    }
    
    @Override
    public String toString(){
        return String.format("%s (%s)", name, role);
    }
}
