package CRS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private ArrayList<User> users;
    private Map<String, User> loggedInUsers;
    private int maxAttempts;

    
    public AuthService(ArrayList<User> users){
        this.users = users;
        this.loggedInUsers = new HashMap<>();
        this.maxAttempts = 3;
    }
    
    public User authenticate(String username, String password){
        for (User u : users){
            if (u.getName().equals(username)){
                // Check if account is locked
                if (u.isAccountLocked()){
                    System.out.println("Account is locked due to multiple failed attempts. Contact admin.");
                    return null;
                }
                
                // Check if account is active
                if (!u.isActive()){
                    System.out.println("Account is deactivated. Contact admin.");
                    return null;
                }
                
                // Verify password
                if (u.getPasswordHash().equals(password)){
                    loggedInUsers.put(username, u); // Add to logged in users map
                    u.resetFailedAttempts(); // Reset on successful login
                    u.setLastLogin(new java.util.Date()); // Update last login
                    
                    // Log the login activity
                    ActivityLog.record(u, "LOGIN", "User logged in successfully");
                    
                    UserManager.saveUsers(users); // Save updated user data
                    System.out.println("Login successful for " + u.getName());
                    return u;
                } else {
                    u.incrementFailedAttempts(maxAttempts);
                    UserManager.saveUsers(users); // Save updated attempt count
                    
                    // Log the failed login attempt
                    ActivityLog.record(u, "LOGIN_FAILED", "Invalid password attempt " + u.getFailedLoginAttempts() + "/" + maxAttempts);
                    
                    System.out.println("Invalid password. Attempts: " + u.getFailedLoginAttempts() + "/" + maxAttempts);
                    return null;
                }
            }
        }
        System.out.println("Username not found.");
        return null;
    }
    
    public void logout(User user){
        if (user != null && loggedInUsers.containsValue(user)){
            // Find and remove user from logged in users map
            String usernameToRemove = null;
            for (Map.Entry<String, User> entry : loggedInUsers.entrySet()){
                if (entry.getValue().equals(user)){
                    usernameToRemove = entry.getKey();
                    break;
                }
            }
            
            if (usernameToRemove != null){
                loggedInUsers.remove(usernameToRemove);
            }
            
            // Log the logout activity
            ActivityLog.record(user, "LOGOUT", "User logged out successfully");
            
            System.out.println("User logged out: " + user.getName());
        }
    }
    
    public boolean authorize(User user, String resource){
        if (user == null || !loggedInUsers.containsValue(user)){
            return false; // User not logged in
        }
        
        String role = user.getRole();
        
        switch(resource.toLowerCase()){
            case "manage_users":
            case "reset_password":
            case "deactivate_user":
            case "unlock_account":
                return role.equals("Admin");
                
            case "view_reports":
            case "manage_recovery":
            case "create_recovery_plan":
                return role.equals("Admin") || role.equals("Officer");
                
            case "view_own_progress":
            case "update_own_profile":
            case "change_password":
                return true; // All authenticated users
                
            case "view_all_users":
                return role.equals("Admin");
                
            default:
                return false;
        }
    }
    
    public boolean isAccountLocked(String username){
        for (User u : users){
            if (u.getName().equals(username)){
                return u.isAccountLocked();
            }
        }
        return false; // Username not found
    }
    
    public void resetLoginAttempts(String username){
        for (User u : users){
            if (u.getName().equals(username)){
                u.resetFailedAttempts();
                UserManager.saveUsers(users);
                
                // Log the reset activity
                ActivityLog.record(u, "RESET_ATTEMPTS", "Login attempts reset by admin");
                
                System.out.println("Login attempts reset for user: " + username);
                return;
            }
        }
        System.out.println("Username not found: " + username);
    }
    
    // Helper method to get logged in user by username
    public User getLoggedInUser(String username){
        return loggedInUsers.get(username);
    }
    
    // Helper method to check if user is logged in
    public boolean isUserLoggedIn(String username){
        return loggedInUsers.containsKey(username);
    }
    
    // Helper method to get all logged in users
    public Map<String, User> getLoggedInUsers(){
        return new HashMap<>(loggedInUsers);
    }
    
    // Helper method for backward compatibility and user data access control
    public boolean canAccessUserData(User user, String targetUserID){
        if (user == null || !loggedInUsers.containsValue(user)) return false;
        
        // Admins can access all user data
        if (user.getRole().equals("Admin")) return true;
        
        // Users can only access their own data
        return user.getUserID().equals(targetUserID);
    }
    
    // Getter for maxAttempts
    public int getMaxAttempts(){
        return maxAttempts;
    }
    
    // Setter for maxAttempts
    public void setMaxAttempts(int maxAttempts){
        this.maxAttempts = maxAttempts;
    }
}