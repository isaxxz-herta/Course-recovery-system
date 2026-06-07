package CRS;

import java.util.ArrayList;

public class Admin extends User{
    public Admin(String userID, String name, String passwordHash, String email, boolean isActive){
        super(userID, name, passwordHash, email, "Admin", isActive);
    }
    
    public void deactivateUser(User user){
        user.setIsActive(false);
        ActivityLog.record(user, "DEACTIVATED", "Account deactivated by admin: " + this.getName());
    }
    
    public void resetUserPassword(User user, String newPasswordHash){
        user.setPasswordHash(newPasswordHash);
        ActivityLog.record(user, "PASSWORD_RESET", "Password reset by admin: " + this.getName());
    }
    
    public void addUser(ArrayList<User> users, User newUser){
        users.add(newUser);
        ActivityLog.record(newUser, "USER_CREATED", "User account created by admin: " + this.getName());
    }
    
    public void updateUser(User user, String newName, String newEmail, String newPasswordHash, boolean newStatus){
        user.setName(newName);
        user.setEmail(newEmail);
        if (newPasswordHash != null && !newPasswordHash.isEmpty()){
            user.setPasswordHash(newPasswordHash);
        }
        user.setIsActive(newStatus);
        ActivityLog.record(user, "USER_UPDATED", "User profile updated by admin: " + this.getName());
    }
    
    public void unlockUserAccount(User user){
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);
        ActivityLog.record(user, "ACCOUNT_UNLOCKED", "Account unlocked by admin: " + this.getName());
        System.out.println("Account unlocked for user: " + user.getName());
    }
    
    public boolean validatePassword(String password){
        // Basic password validation
        if (password.length() < 6){
            System.out.println("Password must be at least 6 characters long.");
            return false;
        }
        
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        
        if (!hasDigit || !hasLetter){
            System.out.println("Password must contain both letters and numbers.");
            return false;
        }
        
        return true;
    }
}
