package com.crs.model;

public class Administrator extends User {
    private static final long serialVersionUID = 5L;
    private String department;
    private String employeeId;

    public Administrator(String userId, String username, String password, 
                        String email, String fullName, String employeeId, String department) {
        super(userId, username, password, email, fullName, Role.ADMINISTRATOR);
        this.employeeId = employeeId;
        this.department = department;
    }

    @Override
    public String getUserDetails() {
        return String.format("Administrator - Employee ID: %s, Department: %s, Email: %s",
                employeeId, department, getEmail());
    }

    public void manageUserAccount(User user, String action) {
        System.out.println("Administrator " + getFullName() + " performing action: " + 
                          action + " on user: " + user.getUsername());

        switch (action.toLowerCase()) {
            case "activate":
                user.setActive(true);
                System.out.println("User account activated.");
                break;
            case "deactivate":
                user.setActive(false);
                System.out.println("User account deactivated.");
                break;
            case "reset":
                user.resetPassword("default123");
                System.out.println("Password reset to default.");
                break;
            default:
                System.out.println("Unknown action.");
        }
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}