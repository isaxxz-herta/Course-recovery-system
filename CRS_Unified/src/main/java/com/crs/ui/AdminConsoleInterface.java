package com.crs.ui;

import com.crs.auth.AuthService;
import com.crs.model.*;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;
import java.util.Scanner;

public class AdminConsoleInterface {
    private Administrator admin;
    private UserManager userManager;
    private AuthService authService;
    private PersistenceService persistence;
    private Scanner scanner;

    public AdminConsoleInterface(Administrator admin, UserManager userManager, AuthService authService, PersistenceService persistence) {
        this.admin = admin;
        this.userManager = userManager;
        this.authService = authService;
        this.persistence = persistence;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n=== ADMINISTRATOR CONSOLE ===");
        System.out.println("Welcome, " + admin.getFullName());
        System.out.println("Department: " + admin.getDepartment());
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    createNewUser();
                    break;
                case 3:
                    editUser();
                    break;
                case 4:
                    deactivateUser();
                    break;
                case 5:
                    activateUser();
                    break;
                case 6:
                    resetUserPassword();
                    break;
                case 7:
                    viewStudentReports();
                    break;
                case 8:
                    checkStudentEligibility();
                    break;
                case 9:
                    changeMyPassword();
                    break;
                case 10:
                    saveData();
                    break;
                case 11:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== ADMINISTRATOR MENU ===");
        System.out.println("1. View All Users");
        System.out.println("2. Create New User");
        System.out.println("3. Edit User");
        System.out.println("4. Deactivate User");
        System.out.println("5. Activate User");
        System.out.println("6. Reset User Password");
        System.out.println("7. View Student Reports");
        System.out.println("8. Check Student Eligibility");
        System.out.println("9. Change My Password");
        System.out.println("10. Save Data");
        System.out.println("11. Logout");
        System.out.print("Enter choice (1-11): ");
    }

    private int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        } finally {
            scanner.nextLine(); // Consume newline
        }
    }

    private void viewAllUsers() {
        System.out.println("\n=== ALL USERS ===");
        System.out.printf("%-10s %-15s %-25s %-15s %-30s %-8s%n", 
                         "User ID", "Username", "Full Name", "Role", "Email", "Active");
        System.out.println("-".repeat(100));
        
        for (User user : userManager.getAllUsers()) {
            System.out.printf("%-10s %-15s %-25s %-15s %-30s %-8s%n",
                             user.getUserId(),
                             user.getUsername(),
                             user.getFullName(),
                             user.getRole().getDisplayName(),
                             user.getEmail(),
                             user.isActive() ? "Yes" : "No");
        }
        
        System.out.println("\nTotal users: " + userManager.getAllUsers().size());
    }

    private void createNewUser() {
        System.out.println("\n=== CREATE NEW USER ===");
        
        System.out.print("User ID: ");
        String userId = scanner.nextLine();
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        
        System.out.println("Role:");
        System.out.println("1. Student");
        System.out.println("2. Administrator");
        System.out.println("3. Academic Officer");
        System.out.print("Choose role (1-3): ");
        
        int roleChoice = getChoice();
        Role role;
        
        switch (roleChoice) {
            case 1: role = Role.STUDENT; break;
            case 2: role = Role.ADMINISTRATOR; break;
            case 3: role = Role.ACADEMIC_OFFICER; break;
            default:
                System.out.println("Invalid role choice.");
                return;
        }
        
        System.out.print("Additional ID (Student ID/Employee ID): ");
        String additionalId = scanner.nextLine();
        
        System.out.print("Department/Program: ");
        String department = scanner.nextLine();
        
        try {
            User newUser = userManager.createUser(userId, username, password, email, fullName, role, additionalId, department);
            System.out.println("User created successfully: " + newUser.getFullName());
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    private void editUser() {
        System.out.println("\n=== EDIT USER ===");
        System.out.print("Enter User ID to edit: ");
        String userId = scanner.nextLine();
        
        User user = userManager.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        
        System.out.println("Current user: " + user.getFullName());
        System.out.print("New email (current: " + user.getEmail() + "): ");
        String newEmail = scanner.nextLine();
        
        System.out.print("New full name (current: " + user.getFullName() + "): ");
        String newFullName = scanner.nextLine();
        
        if (!newEmail.trim().isEmpty()) {
            user.setEmail(newEmail.trim());
        }
        if (!newFullName.trim().isEmpty()) {
            user.setFullName(newFullName.trim());
        }
        
        System.out.println("User updated successfully.");
    }

    private void deactivateUser() {
        System.out.println("\n=== DEACTIVATE USER ===");
        System.out.print("Enter User ID to deactivate: ");
        String userId = scanner.nextLine();
        
        if (userId.equals(admin.getUserId())) {
            System.out.println("You cannot deactivate your own account.");
            return;
        }
        
        boolean success = userManager.deactivateUser(userId);
        if (success) {
            System.out.println("User deactivated successfully.");
        } else {
            System.out.println("User not found or deactivation failed.");
        }
    }

    private void activateUser() {
        System.out.println("\n=== ACTIVATE USER ===");
        System.out.print("Enter User ID to activate: ");
        String userId = scanner.nextLine();
        
        boolean success = userManager.activateUser(userId);
        if (success) {
            System.out.println("User activated successfully.");
        } else {
            System.out.println("User not found or activation failed.");
        }
    }

    private void resetUserPassword() {
        System.out.println("\n=== RESET USER PASSWORD ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        
        User user = userManager.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        
        user.resetPassword(newPassword);
        System.out.println("Password reset successfully for: " + user.getFullName());
    }

    private void viewStudentReports() {
        System.out.println("\n=== STUDENT ACADEMIC REPORTS ===");
        
        for (Student student : userManager.getAllStudents()) {
            System.out.println(student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024));
            System.out.println("=".repeat(80));
        }
    }

    private void checkStudentEligibility() {
        System.out.println("\n=== STUDENT ELIGIBILITY CHECK ===");
        
        int eligible = 0, notEligible = 0;
        
        for (Student student : userManager.getAllStudents()) {
            student.checkEligibility();
            if (student.isEligibleForProgression()) {
                eligible++;
            } else {
                notEligible++;
            }
            System.out.println();
        }
        
        System.out.println("=== SUMMARY ===");
        System.out.println("Total Students: " + userManager.getAllStudents().size());
        System.out.println("Eligible for Progression: " + eligible);
        System.out.println("Not Eligible: " + notEligible);
    }

    private void changeMyPassword() {
        System.out.println("\n=== CHANGE PASSWORD ===");
        System.out.print("Current password: ");
        String currentPassword = scanner.nextLine();
        
        System.out.print("New password: ");
        String newPassword = scanner.nextLine();
        
        boolean success = admin.changePassword(currentPassword, newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Current password is incorrect.");
        }
    }

    private void saveData() {
        System.out.println("\n=== SAVE DATA ===");
        boolean success = persistence.save(userManager);
        if (success) {
            System.out.println("Data saved successfully.");
        } else {
            System.out.println("Failed to save data.");
        }
    }

    private void logout() {
        System.out.println("\n=== LOGOUT ===");
        System.out.print("Are you sure you want to logout? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().startsWith("y")) {
            authService.logout(admin);
            System.out.println("Logged out successfully. Goodbye!");
        }
    }
}