package com.crs.main;

import com.crs.model.*;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;
import com.crs.ui.MainGUI;
import javax.swing.SwingUtilities;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("=== COURSE RECOVERY SYSTEM (CRS) ===");
        System.out.println("=== User & Student Domain Demo ===\n");

        UserManager userManager = new UserManager();
        PersistenceService persistence = new PersistenceService("crs_data.bin");

        // If user wants GUI or default to GUI
        boolean useGui = args.length == 0 || (args.length > 0 && args[0].equalsIgnoreCase("gui"));

        if (useGui) {
            // launch GUI on EDT
            SwingUtilities.invokeLater(() -> {
                MainGUI gui = new MainGUI(userManager, persistence);
                gui.setVisible(true);
            });
            return;
        }

        System.out.println("Running console demo (non-GUI)...");
        // console demo: create sample data, show reports, demonstrate features, save to disk
        Student student1 = (Student) userManager.createUser(
        "U001", "alex123", "password123", "alex.tan@student.edu", 
        "Alex Tan", Role.STUDENT, "2025A1234", "Bachelor of Computer Science"
        );
        Student student2 = (Student) userManager.createUser(
        "U002", "sarah456", "password456", "sarah.lee@student.edu", 
        "Sarah Lee", Role.STUDENT, "2025B5678", "Bachelor of Information Technology"
        );
        userManager.createUser(
        "U003", "admin01", "admin123", "admin@crs.edu", 
        "John Smith", Role.ADMINISTRATOR, "EMP001", "IT Department"
        );
        userManager.createUser(
        "U004", "officer01", "officer123", "officer@crs.edu", 
        "Dr. Emily Wong", Role.ACADEMIC_OFFICER, "EMP002", "Faculty of Computing"
        );

        // add some grades
        student1.addCourseGrade("CS201", "Data Structures", 3, "A", 4.0);
        student1.addCourseGrade("CS205", "Database Systems", 3, "B+", 3.3);
        student1.addCourseGrade("CS210", "Software Engineering I", 3, "B", 3.0);
        student1.addCourseGrade("MA202", "Discrete Mathematics", 4, "C+", 2.3);
        student1.addCourseGrade("EN201", "Academic Writing", 2, "A-", 3.7);
        student1.addFailedCourse("CS101", "Programming Fundamentals", 3, "Failed Final Exam");

        student2.addCourseGrade("CS201", "Data Structures", 3, "C", 2.0);
        student2.addCourseGrade("CS205", "Database Systems", 3, "D+", 1.3);
        student2.addCourseGrade("MA202", "Discrete Mathematics", 4, "F", 0.0);
        student2.addFailedCourse("MA202", "Discrete Mathematics", 4, "Failed both exam and assignment");
        student2.addFailedCourse("CS205", "Database Systems", 3, "Failed Final Exam");

        System.out.println("\nAuthentication demo:");
        User authenticatedUser = userManager.authenticate("alex123", "password123");
        if (authenticatedUser != null) {
            System.out.println("Logged in as: " + authenticatedUser.getFullName());
            authenticatedUser.logout();
        }

        System.out.println("\nEligibility check:");
        student1.checkEligibility();
        System.out.println();
        student2.checkEligibility();

        System.out.println("\nAcademic report for student1:\n");
        System.out.println(student1.generateAcademicReport(1, 2025));

        // Save to disk
        boolean saved = persistence.save(userManager);
        System.out.println("Save operation: " + (saved ? "success" : "failed"));
        System.out.println("\n=== DEMO COMPLETED ===");
    }
}
