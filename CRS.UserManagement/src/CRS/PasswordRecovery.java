package CRS;

import java.util.ArrayList;
import java.util.Random;

public class PasswordRecovery {
    private static final int TEMP_PASSWORD_LENGTH = 8;
    
    public static String generateTemporaryPassword(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder tempPassword = new StringBuilder();
        
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++){
            tempPassword.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return tempPassword.toString();
    }
    
    public static boolean initiatePasswordRecovery(String username, String email, ArrayList<User> users){
        for (User user : users){
            if (user.getName().equals(username) && user.getEmail().equals(email) && user.isActive()){
                String tempPassword = generateTemporaryPassword();
                user.setPasswordHash(tempPassword);
                user.setAccountLocked(false); // Unlock account during recovery
                user.setFailedLoginAttempts(0);
                
                // Log the password recovery activity
                ActivityLog.record(user, "PASSWORD_RECOVERY", "Password recovery initiated via email verification");
                
                // In a real system, this would send an email
                System.out.println("Password recovery initiated for: " + username);
                System.out.println("Temporary password: " + tempPassword);
                System.out.println("Please change your password after logging in.");
                
                UserManager.saveUsers(users);
                return true;
            }
        }
        
        System.out.println("Username and email combination not found or account is inactive.");
        return false;
    }
    
    public static boolean changePassword(User user, String currentPassword, String newPassword){
        if (!user.getPasswordHash().equals(currentPassword)){
            System.out.println("Current password is incorrect.");
            return false;
        }
        
        if (currentPassword.equals(newPassword)){
            System.out.println("New password must be different from current password.");
            return false;
        }
        
        // Basic password validation
        if (newPassword.length() < 6){
            System.out.println("Password must be at least 6 characters long.");
            return false;
        }
        
        user.setPasswordHash(newPassword);
        
        // Log the password change activity
        ActivityLog.record(user, "PASSWORD_CHANGED", "User changed their own password");
        
        System.out.println("Password changed successfully.");
        return true;
    }
}