package com.crs.auth;

import com.crs.model.*;
import com.crs.service.UserManager;
import java.util.*;

public class TestUserManagement {
    
    public static void adminMenu(Administrator admin, UserManager userManager, AuthService auth) {
        Scanner sc = new Scanner(System.in);
        
        while(true) {
            System.out.println("\n===== USER MANAGEMENT MENU =====");
            System.out.println("1. View All Users");
            System.out.println("2. Create New User");
            System.out.println("3. Deactivate User");
            System.out.println("4. Change My Password");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    if (auth.authorize(admin, "view_all_users")) {
                        displayUsers(userManager.getAllUsers());
                    } else {
                        System.out.println("Access denied: Insufficient permissions.");
                    }
                    break;
                case 2:
                    if (auth.authorize(admin, "manage_users")) {
                        createUserUI(userManager);
                    } else {
                        System.out.println("Access denied: Insufficient permissions.");
                    }
                    break;
                case 3:
                    if (auth.authorize(admin, "deactivate_user")) {
                        deactivateUserUI(userManager);
                    } else {
                        System.out.println("Access denied: Insufficient permissions.");
                    }
                    break;
                case 4:
                    if (auth.authorize(admin, "change_password")) {
                        changePasswordUI(admin);
                    } else {
                        System.out.println("Access denied: Insufficient permissions.");
                    }
                    break;
                case 5:
                    System.out.println("Logging out....");
                    auth.logout(admin);
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void displayUsers(List<User> users) {
        System.out.println("\n===== ALL USERS =====");
        for (User user : users) {
            System.out.println(user.toString());
        }
    }
    
    private static void createUserUI(UserManager userManager) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Full Name: ");
        String fullName = sc.nextLine();
        System.out.print("Enter Role (STUDENT/ADMINISTRATOR/ACADEMIC_OFFICER): ");
        String roleStr = sc.nextLine();
        System.out.print("Enter Additional ID (Student ID/Employee ID): ");
        String additionalId = sc.nextLine();
        System.out.print("Enter Department/Program: ");
        String department = sc.nextLine();
        
        try {
            Role role = Role.valueOf(roleStr.toUpperCase());
            User newUser = userManager.createUser(userId, username, password, email, fullName, role, additionalId, department);
            System.out.println("User created successfully: " + newUser.getFullName());
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }
    
    private static void deactivateUserUI(UserManager userManager) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID to deactivate: ");
        String userId = sc.nextLine();
        
        boolean success = userManager.deactivateUser(userId);
        if (success) {
            System.out.println("User deactivated successfully.");
        } else {
            System.out.println("User not found or deactivation failed.");
        }
    }
    
    private static void changePasswordUI(User user) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter current password: ");
        String oldPassword = sc.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        
        boolean success = user.changePassword(oldPassword, newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Password change failed.");
        }
    }
 
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        
        // Create a default admin if no users exist
        if (userManager.getAllUsers().isEmpty()) {
            userManager.createUser("A001", "admin", "12345", "admin@crs.com", "System Administrator", Role.ADMINISTRATOR, "EMP001", "IT Department");
        }
        
        AuthService auth = new AuthService(userManager.getAllUsers());
        Scanner sc = new Scanner(System.in);
        
        User logged = null;
        
        while (logged == null) {        
            System.out.println("\n===== CRS LOGIN MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            
            int loginChoice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch(loginChoice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    logged = auth.authenticate(username, password);
                    break;
                    
                case 2:
                    System.out.println("Goodbye!");
                    return;
                    
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        System.out.println("Welcome " + logged.getFullName() + " (" + logged.getRole() + ")");
                   
        // Show menu based on role
        switch (logged.getRole()) {
            case ADMINISTRATOR:
                adminMenu((Administrator) logged, userManager, auth);
                break;
            case ACADEMIC_OFFICER:
                System.out.println("Academic Officer menu - Feature coming soon!");
                break;
            case STUDENT:
                System.out.println("Student menu - Feature coming soon!");
                break;
            default:
                System.out.println("Role not recognized.");
        } 
    }
}