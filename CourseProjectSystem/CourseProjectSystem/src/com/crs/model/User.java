package com.crs.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private LocalDateTime lastLogout;

    public User(String userId, String username, String password, String email, String fullName, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public abstract String getUserDetails();

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public LocalDateTime getLastLogout() { return lastLogout; }
    public void setLastLogout(LocalDateTime lastLogout) { this.lastLogout = lastLogout; }

    public void login() {
        this.lastLogin = LocalDateTime.now();
        this.isActive = true;
        System.out.println("Login timestamp (binary): " + 
                          Long.toBinaryString(System.currentTimeMillis()));
    }

    public void logout() {
        this.lastLogout = LocalDateTime.now();
        this.isActive = false;
        System.out.println("Logout timestamp (binary): " + 
                          Long.toBinaryString(System.currentTimeMillis()));
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Password changed successfully.");
            return true;
        }
        System.out.println("Old password is incorrect.");
        return false;
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password has been reset.");
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Role: %s, Email: %s, Active: %s",
                userId, fullName, role, email, isActive ? "Yes" : "No");
    }
}
