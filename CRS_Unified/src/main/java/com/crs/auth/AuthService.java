package com.crs.auth;

import com.crs.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService {
    private List<User> users;
    private Map<String, User> loggedInUsers;
    private int maxAttempts;

    public AuthService(List<User> users) {
        this.users = users;
        this.loggedInUsers = new HashMap<>();
        this.maxAttempts = 3;
    }
    
    public User authenticate(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                // Check if account is active
                if (!u.isActive()) {
                    System.out.println("Account is deactivated. Contact admin.");
                    return null;
                }
                
                // Verify password
                if (u.getPassword().equals(password)) {
                    loggedInUsers.put(username, u);
                    u.login();
                    System.out.println("Login successful for " + u.getFullName());
                    return u;
                } else {
                    System.out.println("Invalid password for user: " + username);
                    return null;
                }
            }
        }
        System.out.println("Username not found: " + username);
        return null;
    }
    
    public void logout(User user) {
        if (user != null && loggedInUsers.containsValue(user)) {
            String usernameToRemove = null;
            for (Map.Entry<String, User> entry : loggedInUsers.entrySet()) {
                if (entry.getValue().equals(user)) {
                    usernameToRemove = entry.getKey();
                    break;
                }
            }
            
            if (usernameToRemove != null) {
                loggedInUsers.remove(usernameToRemove);
            }
            
            user.logout();
            System.out.println("User logged out: " + user.getFullName());
        }
    }
    
    public boolean authorize(User user, String resource) {
        if (user == null || !loggedInUsers.containsValue(user)) {
            return false; // User not logged in
        }
        
        String role = user.getRole().name();
        
        switch(resource.toLowerCase()) {
            case "manage_users":
            case "reset_password":
            case "deactivate_user":
            case "unlock_account":
                return role.equals("ADMINISTRATOR");
                
            case "view_reports":
            case "manage_recovery":
            case "create_recovery_plan":
                return role.equals("ADMINISTRATOR") || role.equals("ACADEMIC_OFFICER");
                
            case "view_own_progress":
            case "update_own_profile":
            case "change_password":
                return true; // All authenticated users
                
            case "view_all_users":
                return role.equals("ADMINISTRATOR");
                
            default:
                return false;
        }
    }
    
    public User getLoggedInUser(String username) {
        return loggedInUsers.get(username);
    }
    
    public boolean isUserLoggedIn(String username) {
        return loggedInUsers.containsKey(username);
    }
    
    public Map<String, User> getLoggedInUsers() {
        return new HashMap<>(loggedInUsers);
    }
}