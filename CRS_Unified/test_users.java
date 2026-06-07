import com.crs.model.User;
import com.crs.service.UserManager;

public class test_users {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        
        System.out.println("Testing user creation...");
        
        // Create users manually to test
        try {
            userManager.createUser("A001", "admin", "admin123", "admin@crs.edu", 
                                  "System Administrator", com.crs.model.Role.ADMINISTRATOR, "EMP001", "IT Department");
            
            System.out.println("Created admin user successfully");
            System.out.println("Total users: " + userManager.getAllUsers().size());
            
            for (User user : userManager.getAllUsers()) {
                System.out.println("User: " + user.getUsername() + " / " + user.getPassword() + " (" + user.getRole() + ")");
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}