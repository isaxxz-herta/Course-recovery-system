import com.crs.model.User;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;

public class ActivateAccounts {
    public static void main(String[] args) {
        System.out.println("=== CRS Account Activation Utility ===");
        
        PersistenceService persistence = new PersistenceService("crs_data.bin");
        UserManager userManager = persistence.load();
        
        if (userManager == null) {
            System.out.println("No data file found. Run the main application first to create accounts.");
            return;
        }
        
        System.out.println("Found " + userManager.getAllUsers().size() + " users. Activating all accounts...");
        
        int activatedCount = 0;
        for (User user : userManager.getAllUsers()) {
            if (!user.isActive()) {
                user.setActive(true);
                activatedCount++;
                System.out.println("✅ Activated: " + user.getUsername() + " (" + user.getFullName() + ")");
            } else {
                System.out.println("✓ Already active: " + user.getUsername() + " (" + user.getFullName() + ")");
            }
        }
        
        // Save the updated data
        boolean saved = persistence.save(userManager);
        
        if (saved) {
            System.out.println("\n🎉 SUCCESS: Activated " + activatedCount + " accounts and saved data.");
            System.out.println("\n=== ALL ACCOUNTS ARE NOW ACTIVE ===");
            System.out.println("Administrator: admin / admin123");
            System.out.println("Academic Officer: officer / officer123");
            System.out.println("Student 1: student1 / student123");
            System.out.println("Student 2: student2 / student123");
        } else {
            System.out.println("\n❌ ERROR: Failed to save data. Please try again.");
        }
    }
}