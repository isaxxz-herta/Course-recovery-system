package CRS;

import java.util.*;

public class UI_Wrappers {
    public static void addUserUI(Admin admin, ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        
        String password;
        do {
            System.out.print("Enter Password: ");
            password = sc.nextLine();
        } while (!admin.validatePassword(password));
        
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Role(Admin/Officer/Student): ");
        String role = sc.nextLine();
        
        User newUser = new User(id, name, password, email, role, true);
        admin.addUser(users, newUser);
        UserManager.saveUsers(users);
        System.out.println("User added successfully");
    }
    
    public static void updateUserUI(Admin admin, ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID to update: ");
        String id = sc.nextLine();
        
        for (User u : users){
            if (u.getUserID().equals(id)){
                System.out.print("Enter new name: ");
                String name = sc.nextLine();
                System.out.print("Enter new email: ");
                String email = sc.nextLine();
                System.out.print("Enter new password: ");
                String password = sc.nextLine();
                System.out.print("Active? (true/false): ");
                boolean status = sc.nextBoolean();
                
                admin.updateUser(u, name, email, password, status);
                UserManager.saveUsers(users);
                System.out.println("User updated successfully.");
                return;
            }
        }
    }
    
    public static void deactivateUserUI(Admin admin, ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID to deactivate: ");
        String id = sc.nextLine();
        
        for (User u : users){
            if(u.getUserID().equals(id)){
                admin.deactivateUser(u);
                UserManager.saveUsers(users);
                System.out.println("User deactivated successfully!");
                return;
            }
        }
        
        System.out.println("User not found.");
    }
    
    public static void displayUsers(ArrayList<User> users){
        System.out.println("\n===== ALL USERS =====");
        for (User u : users){
            System.out.println(u.getUserID() + " | " + u.getName() + " | " 
                    + u.getRole() + " | Active: " + u.isActive() + " | Last Login: " + 
                    (u.getLastLogin() != null ? u.getLastLogin() : "Never"));
        }
    }
    
    public static void resetPasswordUI(Admin admin, ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID to reset password: ");
        String id = sc.nextLine();
        
        for (User u : users){
            if (u.getUserID().equals(id)){
                String newPassword;
                do {
                    System.out.print("Enter NEW password: ");
                    newPassword = sc.nextLine();
                } while (!admin.validatePassword(newPassword));
                
                admin.resetUserPassword(u, newPassword);
                UserManager.saveUsers(users);
                System.out.println("Password reset successfully.");
                return;
            }
        }
        System.out.println("User not found.");
    }
    
    public static void unlockAccountUI(Admin admin, ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter User ID to unlock: ");
        String id = sc.nextLine();
        
        for (User u : users){
            if (u.getUserID().equals(id)){
                admin.unlockUserAccount(u);
                UserManager.saveUsers(users);
                return;
            }
        }
        System.out.println("User not found.");
    }
    
    public static void passwordRecoveryUI(ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        
        PasswordRecovery.initiatePasswordRecovery(username, email, users);
    }
    
    public static void changePasswordUI(User user, ArrayList<User> users){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter current password: ");
        String currentPassword = sc.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        
        if (PasswordRecovery.changePassword(user, currentPassword, newPassword)){
            UserManager.saveUsers(users);
        }
    }
    
}
