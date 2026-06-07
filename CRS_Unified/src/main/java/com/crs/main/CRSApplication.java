package com.crs.main;

import com.crs.auth.AuthService;
import com.crs.model.*;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;
import com.crs.ui.*;
import java.util.Scanner;
import javax.swing.SwingUtilities;

/**
 * Course Recovery System (CRS) Application
 * Role-based authentication system with dedicated interfaces for each user type
 */
public class CRSApplication {
    private static UserManager userManager;
    private static PersistenceService persistence;
    private static AuthService authService;
    
    public static void main(String[] args) {
        System.out.println("=== COURSE RECOVERY SYSTEM (CRS) ===");
        System.out.println("=== Role-Based Access System ===");
        System.out.println("Starting GUI Mode...");
        System.out.println("(To use Console Mode, run: java com.crs.main.CRSApplication console)\n");
        
        // Initialize system components
        initializeSystem();
        
        // Check if console mode is specifically requested via command line argument
        boolean forceConsoleMode = false;
        if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
            forceConsoleMode = true;
        }
        
        if (forceConsoleMode) {
            System.out.println("Console mode requested via command line argument.");
            runConsoleMode();
        } else {
            // Start GUI mode directly
            runGUIMode();
        }
    }
    
    private static void initializeSystem() {
        userManager = new UserManager();
        persistence = new PersistenceService("crs_data.bin");
        
        System.out.println("DEBUG: Checking for existing data file...");
        
        // Try to load existing data
        UserManager loadedManager = persistence.load();
        System.out.println("DEBUG: Loaded manager: " + (loadedManager != null ? "not null" : "null"));
        
        if (loadedManager != null) {
            System.out.println("DEBUG: Loaded manager has " + loadedManager.getAllUsers().size() + " users");
            if (!loadedManager.getAllUsers().isEmpty()) {
                userManager = loadedManager;
                System.out.println("Loaded existing user data. Users: " + userManager.getAllUsers().size());
                // Debug: show loaded users
                for (com.crs.model.User user : userManager.getAllUsers()) {
                    System.out.println("DEBUG: Loaded user: " + user.getUsername());
                }
            } else {
                System.out.println("DEBUG: Loaded manager is empty, creating default users");
                createDefaultUsers();
            }
        } else {
            System.out.println("DEBUG: No existing data, creating default users");
            // Create default users if no data exists or data is empty
            createDefaultUsers();
        }
        
        System.out.println("DEBUG: Final user count: " + userManager.getAllUsers().size());
        System.out.println("=== DEFAULT LOGIN CREDENTIALS ===");
        System.out.println("Administrator: admin / admin123");
        System.out.println("Academic Officer: officer / officer123");
        System.out.println("Student 1: student1 / student123");
        System.out.println("Student 2: student2 / student123");
        System.out.println("=====================================");
        
        authService = new AuthService(userManager.getAllUsers());
    }
    
    private static void createDefaultUsers() {
        // Create default admin
        userManager.createUser("A001", "admin", "admin123", "admin@crs.edu", 
                              "System Administrator", Role.ADMINISTRATOR, "EMP001", "IT Department");
        
        // Create default academic officer
        userManager.createUser("O001", "officer", "officer123", "officer@crs.edu", 
                              "Dr. Sarah Wilson", Role.ACADEMIC_OFFICER, "EMP002", "Faculty of Computing");
        
        // Create sample students
        Student student1 = (Student) userManager.createUser("S001", "student1", "student123", "student1@crs.edu", 
                                                           "John Smith", Role.STUDENT, "TP001", "Computer Science");
        Student student2 = (Student) userManager.createUser("S002", "student2", "student123", "student2@crs.edu", 
                                                           "Jane Doe", Role.STUDENT, "TP002", "Information Technology");
        
        // Add sample grades for students
        student1.addCourseGrade("CS101", "Programming Fundamentals", 3, "B+", 3.3);
        student1.addCourseGrade("CS201", "Data Structures", 3, "A", 4.0);
        student1.addCourseGrade("MA101", "Mathematics", 4, "B", 3.0);
        
        student2.addCourseGrade("CS101", "Programming Fundamentals", 3, "C", 2.0);
        student2.addCourseGrade("CS201", "Data Structures", 3, "D+", 1.3);
        student2.addFailedCourse("MA101", "Mathematics", 4, "Failed Final Exam");
        
        // Save initial data
        persistence.save(userManager);
    }
    
    private static void runGUIMode() {
        System.out.println("Starting GUI Mode...");
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI(userManager, persistence, authService);
            loginGUI.setVisible(true);
        });
    }
    
    private static void runConsoleMode() {
        System.out.println("Starting Console Mode...");
        
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;
        
        // Login loop
        while (loggedInUser == null) {
            System.out.println("\n=== LOGIN ===");
            System.out.println("Default accounts:");
            System.out.println("Admin: admin/admin123");
            System.out.println("Officer: officer/officer123");
            System.out.println("Student: student1/student123 or student2/student123");
            System.out.println();
            
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            loggedInUser = authService.authenticate(username, password);
            
            if (loggedInUser == null) {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
        
        // Route to appropriate interface based on role
        switch (loggedInUser.getRole()) {
            case ADMINISTRATOR:
                new AdminConsoleInterface((Administrator) loggedInUser, userManager, authService, persistence).start();
                break;
            case ACADEMIC_OFFICER:
                new OfficerConsoleInterface((AcademicOfficer) loggedInUser, userManager, authService, persistence).start();
                break;
            case STUDENT:
                new StudentConsoleInterface((Student) loggedInUser, userManager, authService, persistence).start();
                break;
            default:
                System.out.println("Unknown role. Access denied.");
        }
    }
    
    // Getters for other classes to access system components
    public static UserManager getUserManager() { return userManager; }
    public static PersistenceService getPersistence() { return persistence; }
    public static AuthService getAuthService() { return authService; }
}