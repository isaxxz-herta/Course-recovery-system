package com.crs.output;

import com.crs.model.User;

public class EmailService {

    private String smtpHost;
    private int smtpPort;
    private String username;

    public EmailService(String smtpHost, int smtpPort, String username) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
    }

    public boolean sendEmail(String to, String subject, String body) {
        // Placeholder logic (for console output)
        System.out.println("=== Sending Email ===");
        System.out.println("SMTP Host: " + smtpHost);
        System.out.println("SMTP Port: " + smtpPort);
        System.out.println("From: " + username);
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("=====================");
        return true; 
    }

    public boolean sendPasswordReset(String email, String token) {
        String subject = "Password Reset Request";
        String body = "Use this token to reset your password: " + token;

        return sendEmail(email, subject, body);
    }

    // Enhanced functionality from mavenprojec1 2
    public void sendAccountCreationEmail(User user) {
        String subject = "Welcome to Course Recovery System";
        String body = String.format(
            "Dear %s,\n\n" +
            "Your account has been created successfully.\n" +
            "Username: %s\n" +
            "Role: %s\n\n" +
            "Please login and change your password.\n\n" +
            "Best regards,\nCRS Team",
            user.getFullName(), user.getUsername(), user.getRole().getDisplayName()
        );

        sendEmail(user.getEmail(), subject, body);
    }

    public void sendPasswordResetEmail(User user, String tempPassword) {
        String subject = "Password Reset Request";
        String body = String.format(
            "Dear %s,\n\n" +
            "Your password has been reset.\n" +
            "Temporary password: %s\n\n" +
            "Please login and change your password immediately.\n\n" +
            "Best regards,\nCRS Team",
            user.getFullName(), tempPassword
        );

        sendEmail(user.getEmail(), subject, body);
    }

    // Additional utility methods
    public void sendWelcomeEmail(User user) {
        sendAccountCreationEmail(user);
    }

    public void sendEligibilityNotification(User user, boolean eligible) {
        String subject = eligible ? "Congratulations - Eligible for Progression" : "Academic Support Required";
        String body = String.format(
            "Dear %s,\n\n" +
            "Your academic eligibility status has been updated.\n" +
            "Status: %s\n\n" +
            "%s\n\n" +
            "Best regards,\nCRS Academic Team",
            user.getFullName(),
            eligible ? "ELIGIBLE FOR PROGRESSION" : "REQUIRES ACADEMIC SUPPORT",
            eligible ? "You have met all requirements for progression to the next level." 
                     : "Please contact your academic advisor to discuss recovery options."
        );

        sendEmail(user.getEmail(), subject, body);
    }
}