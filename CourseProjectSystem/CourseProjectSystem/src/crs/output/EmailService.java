package crs.output;

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
}
