package com.crs.service;

import com.crs.model.User;

public class EmailService {
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

        System.out.println("Sending email to: " + user.getEmail());
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("--- Email sent successfully ---\n");
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

        System.out.println("Sending password reset email to: " + user.getEmail());
        System.out.println("Subject: " + subject);
        System.out.println("--- Email sent successfully ---\n");
    }
}
