package CRS;

import java.util.*;

public class TestUserManagement {
    public static void adminMenu(Admin admin, ArrayList<User> users, AuthService auth){
    Scanner sc = new Scanner(System.in);
    
    while(true){
        System.out.println("\n===== USER MANAGEMENT MENU =====");
        System.out.println("1. Add User");
        System.out.println("2. Update User");
        System.out.println("3. Deactivate User");
        System.out.println("4. View All Users");
        System.out.println("5. Reset User Password");
        System.out.println("6. Unlock User Account");
        System.out.println("7. Change My Password");
        System.out.println("8. Logout");
        System.out.println("Enter choice: ");
        
        int choice = sc.nextInt();
        sc.nextLine(); //consume newline
        
        switch (choice){
            case 1:
                if (auth.authorize(admin, "manage_users")){
                    UI_Wrappers.addUserUI(admin, users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 2:
                if (auth.authorize(admin, "manage_users")){
                    UI_Wrappers.updateUserUI(admin, users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 3:
                if (auth.authorize(admin, "deactivate_user")){
                    UI_Wrappers.deactivateUserUI(admin, users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 4:
                if (auth.authorize(admin, "view_all_users")){
                    UI_Wrappers.displayUsers(users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 5:
                if (auth.authorize(admin, "reset_password")){
                    UI_Wrappers.resetPasswordUI(admin, users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 6:
                if (auth.authorize(admin, "unlock_account")){
                    UI_Wrappers.unlockAccountUI(admin, users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 7:
                if (auth.authorize(admin, "change_password")){
                    UI_Wrappers.changePasswordUI(admin, users);
                } else {
                    System.out.println("Access denied: Insufficient permissions.");
                }
                break;
            case 8:
                System.out.println("Logging out....");
                auth.logout(admin);
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
 
    //Rename main() to testMain() b4 merging with UI
    public static void main(String[]args){
        ArrayList<User> users = UserManager.loadUsers();
        
        //Create a default admin if no users exists
        if (users.isEmpty()){
            users.add(new Admin("A001", "admin", "12345", "admin@crs.com", true));
            UserManager.saveUsers(users);
        }
        
        AuthService auth = new AuthService(users);
        Scanner sc = new Scanner(System.in);
        
        User logged = null;
        
        while (logged == null){        
            System.out.println("\n===== LOGIN MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Password Recovery");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            
            int loginChoice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch(loginChoice){
                case 1:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    logged = auth.authenticate(username, password);
                    if (logged != null){
                        // Authentication successful
                    }
                    break;
                    
                case 2:
                    UI_Wrappers.passwordRecoveryUI(users);
                    break;
                    
                case 3:
                    System.out.println("Goodbye!");
                    return;
                    
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        System.out.println("Welcome " + logged.getName() + " (" + logged.getRole() + ")");
                   
        //Only show Admin if logged-in user is Admin
         switch (logged.getRole()){
             case "Admin":
                 adminMenu((Admin) logged, users, auth);
                 break;
             case "Officer":
                 officerMenu(logged); // Waiting for other's code
                 break;
             case "Student":
                 studentMenu(logged); // Waiting for other's code
                 break;
             default:
                 System.out.print("Role not recognized.");

         } 
        
    }
    
    public static void officerMenu(User officer){
        System.out.println("Officer menu not available yet.");
        //to recovery plan, eligibility, reports
    }
    
    public static void studentMenu(User student){
        System.out.println("Student menu not available yet.");
        //to view recovery progress, view reports
    }
}
