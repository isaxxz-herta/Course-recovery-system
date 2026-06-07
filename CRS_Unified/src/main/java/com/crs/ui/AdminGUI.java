package com.crs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.crs.auth.AuthService;
import com.crs.model.Administrator;
import com.crs.model.Student;
import com.crs.model.User;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;

public class AdminGUI extends JFrame {
    private Administrator admin;
    private UserManager userManager;
    private AuthService authService;
    private PersistenceService persistence;
    
    private JTabbedPane tabbedPane;
    private JTable userTable;
    private DefaultTableModel userTableModel;
    private JTextArea logArea;

    public AdminGUI(Administrator admin, UserManager userManager, AuthService authService, PersistenceService persistence) {
        this.admin = admin;
        this.userManager = userManager;
        this.authService = authService;
        this.persistence = persistence;
        
        initializeGUI();
        loadUserData();
    }

    private void initializeGUI() {
        setTitle("CRS - Administrator Dashboard - " + admin.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save Data");
        JMenuItem returnToLoginItem = new JMenuItem("Return to Login");
        JMenuItem logoutItem = new JMenuItem("Logout");
        
        saveItem.addActionListener(e -> saveData());
        returnToLoginItem.addActionListener(e -> returnToLogin());
        logoutItem.addActionListener(e -> logout());
        
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(returnToLoginItem);
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // User Management Tab
        tabbedPane.addTab("User Management", createUserManagementPanel());
        
        // Student Reports Tab
        tabbedPane.addTab("Student Reports", createStudentReportsPanel());
        
        // System Logs Tab
        tabbedPane.addTab("System Logs", createSystemLogsPanel());

        add(tabbedPane);
        
        log("Administrator " + admin.getFullName() + " logged in successfully.");
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // User table
        String[] columns = {"User ID", "Username", "Full Name", "Role", "Email", "Active"};
        userTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        userTable = new JTable(userTableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton createUserBtn = new JButton("Create User");
        JButton editUserBtn = new JButton("Edit User");
        JButton deactivateBtn = new JButton("Deactivate User");
        JButton activateBtn = new JButton("Activate User");
        JButton resetPasswordBtn = new JButton("Reset Password");
        JButton refreshBtn = new JButton("Refresh");

        createUserBtn.addActionListener(e -> createUser());
        editUserBtn.addActionListener(e -> editUser());
        deactivateBtn.addActionListener(e -> deactivateUser());
        activateBtn.addActionListener(e -> activateUser());
        resetPasswordBtn.addActionListener(e -> resetPassword());
        refreshBtn.addActionListener(e -> loadUserData());

        buttonPanel.add(createUserBtn);
        buttonPanel.add(editUserBtn);
        buttonPanel.add(deactivateBtn);
        buttonPanel.add(activateBtn);
        buttonPanel.add(resetPasswordBtn);
        buttonPanel.add(refreshBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createStudentReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton generateReportsBtn = new JButton("Generate All Student Reports");
        JButton eligibilityCheckBtn = new JButton("Check Student Eligibility");
        
        generateReportsBtn.addActionListener(e -> {
            StringBuilder reports = new StringBuilder();
            reports.append("=== ALL STUDENT ACADEMIC REPORTS ===\n\n");
            
            for (Student student : userManager.getAllStudents()) {
                reports.append(student.generateAcademicReport(1, 2025));
                reports.append("\n" + "=".repeat(50) + "\n\n");
            }
            
            reportArea.setText(reports.toString());
            log("Generated academic reports for all students.");
        });
        
        eligibilityCheckBtn.addActionListener(e -> {
            StringBuilder eligibility = new StringBuilder();
            eligibility.append("=== STUDENT ELIGIBILITY CHECK ===\n\n");
            
            int eligible = 0, notEligible = 0;
            
            for (Student student : userManager.getAllStudents()) {
                student.checkEligibility(); // This prints to console
                eligibility.append("Student: ").append(student.getFullName()).append("\n");
                eligibility.append("CGPA: ").append(String.format("%.2f", student.getCgpa())).append("\n");
                eligibility.append("Failed Courses: ").append(student.getFailedCourses().size()).append("\n");
                eligibility.append("Status: ").append(student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE").append("\n\n");
                
                if (student.isEligibleForProgression()) eligible++;
                else notEligible++;
            }
            
            eligibility.append("=== SUMMARY ===\n");
            eligibility.append("Eligible Students: ").append(eligible).append("\n");
            eligibility.append("Not Eligible Students: ").append(notEligible).append("\n");
            
            reportArea.setText(eligibility.toString());
            log("Performed eligibility check for all students.");
        });
        
        buttonPanel.add(generateReportsBtn);
        buttonPanel.add(eligibilityCheckBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createSystemLogsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearLogsBtn = new JButton("Clear Logs");
        clearLogsBtn.addActionListener(e -> logArea.setText(""));
        buttonPanel.add(clearLogsBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void loadUserData() {
        userTableModel.setRowCount(0); // Clear existing data
        
        for (User user : userManager.getAllUsers()) {
            Object[] row = {
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole().getDisplayName(),
                user.getEmail(),
                user.isActive() ? "Yes" : "No"
            };
            userTableModel.addRow(row);
        }
        
        log("User data refreshed. Total users: " + userManager.getAllUsers().size());
    }

    private void createUser() {
        CreateUserDialog dialog = new CreateUserDialog(this, userManager);
        dialog.setVisible(true);
        
        if (dialog.isUserCreated()) {
            loadUserData();
            User newUser = dialog.getCreatedUser();
            log("New user created: " + newUser.getFullName());
            
            // Send welcome email
            try {
                com.crs.output.EmailService emailService = new com.crs.output.EmailService("smtp.crs.edu", 587, "admin@crs.edu");
                emailService.sendAccountCreationEmail(newUser);
                log("Welcome email sent to: " + newUser.getEmail());
            } catch (Exception e) {
                log("Failed to send welcome email: " + e.getMessage());
            }
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String userId = (String) userTableModel.getValueAt(selectedRow, 0);
        User user = userManager.findUserById(userId);
        
        if (user != null) {
            EditUserDialog dialog = new EditUserDialog(this, user, userManager);
            dialog.setVisible(true);
            
            if (dialog.isUserUpdated()) {
                loadUserData();
                log("User updated: " + user.getFullName());
            }
        }
    }

    private void deactivateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to deactivate.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String userId = (String) userTableModel.getValueAt(selectedRow, 0);
        String username = (String) userTableModel.getValueAt(selectedRow, 1);
        
        if (userId.equals(admin.getUserId())) {
            JOptionPane.showMessageDialog(this, "You cannot deactivate your own account.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to deactivate user: " + username + "?", 
            "Confirm Deactivation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = userManager.deactivateUser(userId);
            if (success) {
                loadUserData();
                log("User deactivated: " + username);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to deactivate user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void activateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to activate.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String userId = (String) userTableModel.getValueAt(selectedRow, 0);
        String username = (String) userTableModel.getValueAt(selectedRow, 1);
        
        boolean success = userManager.activateUser(userId);
        if (success) {
            loadUserData();
            log("User activated: " + username);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to activate user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetPassword() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to reset password.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String userId = (String) userTableModel.getValueAt(selectedRow, 0);
        String username = (String) userTableModel.getValueAt(selectedRow, 1);
        User user = userManager.findUserById(userId);
        
        if (user != null) {
            String newPassword = JOptionPane.showInputDialog(this, 
                "Enter new password for " + username + ":", 
                "Reset Password", JOptionPane.QUESTION_MESSAGE);
            
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                user.resetPassword(newPassword.trim());
                log("Password reset for user: " + username);
                
                // Send password reset email
                try {
                    com.crs.output.EmailService emailService = new com.crs.output.EmailService("smtp.crs.edu", 587, "admin@crs.edu");
                    emailService.sendPasswordResetEmail(user, newPassword.trim());
                    log("Password reset email sent to: " + user.getEmail());
                } catch (Exception e) {
                    log("Failed to send password reset email: " + e.getMessage());
                }
                
                JOptionPane.showMessageDialog(this, "Password reset successfully and email sent.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void saveData() {
        boolean success = persistence.save(userManager);
        if (success) {
            log("Data saved successfully.");
            JOptionPane.showMessageDialog(this, "Data saved successfully.", "Save Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            log("Failed to save data.");
            JOptionPane.showMessageDialog(this, "Failed to save data.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToLogin() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Return to login page? This will allow you to login as a different user.", 
            "Return to Login", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            authService.logout(admin);
            log("Administrator " + admin.getFullName() + " returned to login page.");
            
            // Return to login screen
            SwingUtilities.invokeLater(() -> {
                LoginGUI loginGUI = new LoginGUI(userManager, persistence, authService);
                loginGUI.setVisible(true);
            });
            
            dispose();
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout and exit the application?", 
            "Confirm Logout", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            authService.logout(admin);
            log("Administrator " + admin.getFullName() + " logged out.");
            
            // Save data before exiting
            saveData();
            
            // Exit application
            System.exit(0);
        }
    }

    private void log(String message) {
        if (logArea != null) {
            String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
        System.out.println("[ADMIN] " + message);
    }
}