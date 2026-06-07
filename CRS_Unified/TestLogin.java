import com.crs.auth.AuthService;
import com.crs.model.*;
import com.crs.service.UserManager;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("=== TESTING LOGIN SYSTEM ===");
        
        // Create user manager and add test users
        UserManager userManager = new UserManager();
        
        // Create admin user
        User admin = userManager.createUser("A001", "admin", "admin123", "admin@crs.edu", 
                                           "System Administrator", Role.ADMINISTRATOR, "EMP001", "IT Department");
        
        System.out.println("Created user: " + admin.getUsername() + " with password: " + admin.getPassword());
        
        // Create auth service
        AuthService authService = new AuthService(userManager.getAllUsers());
        
        // Test authentication
        System.out.println("\nTesting authentication...");
        User authenticated = authService.authenticate("admin", "admin123");
        
        if (authenticated != null) {
            System.out.println("✅ SUCCESS: Login successful for " + authenticated.getFullName());
            System.out.println("Role: " + authenticated.getRole());
        } else {
            System.out.println("❌ FAILED: Authentication failed");
        }
        
        // Test wrong password
        System.out.println("\nTesting wrong password...");
        User failed = authService.authenticate("admin", "wrongpassword");
        
        if (failed == null) {
            System.out.println("✅ SUCCESS: Correctly rejected wrong password");
        } else {
            System.out.println("❌ FAILED: Should have rejected wrong password");
        }
        
        System.out.println("\n=== CORRECT CREDENTIALS ===");
        System.out.println("Username: admin");
        System.out.println("Password: admin123");
        System.out.println("========================");
    }
}