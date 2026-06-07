package com.crs.ui;

import com.crs.auth.AuthService;
import com.crs.model.*;
import com.crs.output.*;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI extends JFrame {
    private Student student;
    private UserManager userManager;
    private AuthService authService;
    private PersistenceService persistence;
    
    private JTabbedPane tabbedPane;
    private JTable gradesTable;
    private DefaultTableModel gradesTableModel;
    private JTable failedCoursesTable;
    private DefaultTableModel failedCoursesTableModel;
    private JTextArea reportArea;
    private JLabel cgpaLabel;
    private JLabel eligibilityLabel;

    public StudentGUI(Student student, UserManager userManager, AuthService authService, PersistenceService persistence) {
        this.student = student;
        this.userManager = userManager;
        this.authService = authService;
        this.persistence = persistence;
        
        initializeGUI();
        loadStudentData();
    }

    private void initializeGUI() {
        setTitle("CRS - Student Portal - " + student.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu profileMenu = new JMenu("Profile");
        
        JMenuItem exportReportItem = new JMenuItem("Export Academic Report");
        JMenuItem returnToLoginItem = new JMenuItem("Return to Login");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem changePasswordItem = new JMenuItem("Change Password");
        JMenuItem viewProfileItem = new JMenuItem("View Profile");
        
        exportReportItem.addActionListener(e -> exportAcademicReport());
        returnToLoginItem.addActionListener(e -> returnToLogin());
        logoutItem.addActionListener(e -> logout());
        changePasswordItem.addActionListener(e -> changePassword());
        viewProfileItem.addActionListener(e -> viewProfile());
        
        fileMenu.add(exportReportItem);
        fileMenu.addSeparator();
        fileMenu.add(returnToLoginItem);
        fileMenu.add(logoutItem);
        
        profileMenu.add(viewProfileItem);
        profileMenu.add(changePasswordItem);
        
        menuBar.add(fileMenu);
        menuBar.add(profileMenu);
        setJMenuBar(menuBar);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Academic Overview Tab
        tabbedPane.addTab("Academic Overview", createAcademicOverviewPanel());
        
        // Course Grades Tab
        tabbedPane.addTab("Course Grades", createGradesPanel());
        
        // Failed Courses Tab
        tabbedPane.addTab("Failed Courses", createFailedCoursesPanel());
        
        // Academic Report Tab
        tabbedPane.addTab("Academic Report", createReportPanel());

        add(tabbedPane);
    }

    private JPanel createAcademicOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Student info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(student.getStudentId()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(student.getFullName()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        infoPanel.add(new JLabel("Program:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(student.getProgram()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        infoPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        infoPanel.add(new JLabel(student.getEmail()), gbc);
        
        // Academic status panel
        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("Academic Status"));
        
        gbc.gridx = 0; gbc.gridy = 0;
        statusPanel.add(new JLabel("CGPA:"), gbc);
        gbc.gridx = 1;
        cgpaLabel = new JLabel();
        statusPanel.add(cgpaLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        statusPanel.add(new JLabel("Total Credit Hours:"), gbc);
        gbc.gridx = 1;
        statusPanel.add(new JLabel(String.valueOf(student.getTotalCreditHours())), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        statusPanel.add(new JLabel("Year of Study:"), gbc);
        gbc.gridx = 1;
        statusPanel.add(new JLabel(String.valueOf(student.getYearOfStudy())), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        statusPanel.add(new JLabel("Current Semester:"), gbc);
        gbc.gridx = 1;
        statusPanel.add(new JLabel(String.valueOf(student.getCurrentSemester())), gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        statusPanel.add(new JLabel("Eligibility Status:"), gbc);
        gbc.gridx = 1;
        eligibilityLabel = new JLabel();
        statusPanel.add(eligibilityLabel, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshBtn = new JButton("Refresh Data");
        JButton checkEligibilityBtn = new JButton("Check Eligibility");
        
        refreshBtn.addActionListener(e -> loadStudentData());
        checkEligibilityBtn.addActionListener(e -> {
            student.checkEligibility();
            updateEligibilityStatus();
            JOptionPane.showMessageDialog(this, 
                "Eligibility check completed. Check console for details.", 
                "Eligibility Check", JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(checkEligibilityBtn);
        
        // Layout
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        topPanel.add(infoPanel);
        topPanel.add(statusPanel);
        
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createGradesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Grades table
        String[] columns = {"Course Code", "Course Title", "Credit Hours", "Grade", "Grade Point"};
        gradesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gradesTable = new JTable(gradesTableModel);
        
        JScrollPane scrollPane = new JScrollPane(gradesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createFailedCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Failed courses table
        String[] columns = {"Course Code", "Course Title", "Credit Hours", "Reason"};
        failedCoursesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        failedCoursesTable = new JTable(failedCoursesTableModel);
        
        JScrollPane scrollPane = new JScrollPane(failedCoursesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Failed courses may affect your eligibility for progression."));
        panel.add(infoPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton generateReportBtn = new JButton("Generate Report");
        JButton exportReportBtn = new JButton("Export to File");
        JButton emailReportBtn = new JButton("Email Report");
        
        generateReportBtn.addActionListener(e -> generateReport());
        exportReportBtn.addActionListener(e -> exportAcademicReport());
        emailReportBtn.addActionListener(e -> emailReport());
        
        buttonPanel.add(generateReportBtn);
        buttonPanel.add(exportReportBtn);
        buttonPanel.add(emailReportBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void loadStudentData() {
        // Update CGPA and eligibility
        student.calculateCGPA();
        updateEligibilityStatus();
        
        // Load grades table
        gradesTableModel.setRowCount(0);
        for (CourseGrade grade : student.getCourseGrades()) {
            Object[] row = {
                grade.getCourseCode(),
                grade.getCourseTitle(),
                grade.getCreditHours(),
                grade.getGrade(),
                String.format("%.2f", grade.getGradePoint())
            };
            gradesTableModel.addRow(row);
        }
        
        // Load failed courses table
        failedCoursesTableModel.setRowCount(0);
        for (FailedCourse failed : student.getFailedCourses()) {
            Object[] row = {
                failed.getCourseCode(),
                failed.getCourseTitle(),
                failed.getCreditHours(),
                failed.getReason()
            };
            failedCoursesTableModel.addRow(row);
        }
        
        // Update CGPA label
        cgpaLabel.setText(String.format("%.2f", student.getCgpa()));
        
        // Generate initial report
        generateReport();
    }

    private void updateEligibilityStatus() {
        if (student.isEligibleForProgression()) {
            eligibilityLabel.setText("ELIGIBLE");
            eligibilityLabel.setForeground(Color.GREEN);
        } else {
            eligibilityLabel.setText("NOT ELIGIBLE");
            eligibilityLabel.setForeground(Color.RED);
        }
    }

    private void generateReport() {
        String report = student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024);
        reportArea.setText(report);
    }

    private void exportAcademicReport() {
        try {
            // Create enrollments for report generator
            List<SimpleEnrollment> enrollments = new ArrayList<>();
            for (CourseGrade grade : student.getCourseGrades()) {
                enrollments.add(new SimpleEnrollment(grade.getCourseTitle(), grade.getGradePoint()));
            }
            
            ReportGenerator generator = new ReportGenerator();
            AcademicReport report = generator.generateReport(student, "Current Semester", enrollments);
            
            String filename = "academic_report_" + student.getStudentId() + ".txt";
            boolean success = generator.exportToFile(report, filename);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Report exported successfully to: " + filename, 
                    "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to export report.", 
                    "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting report: " + e.getMessage(), 
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void emailReport() {
        try {
            EmailService emailService = new EmailService("smtp.crs.edu", 587, "noreply@crs.edu");
            
            String subject = "Academic Report - " + student.getFullName();
            String body = "Dear " + student.getFullName() + ",\n\n" +
                         "Please find your academic report below:\n\n" +
                         student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024) +
                         "\n\nBest regards,\nCourse Recovery System";
            
            boolean success = emailService.sendEmail(student.getEmail(), subject, body);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Report sent to your email: " + student.getEmail(), 
                    "Email Sent", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error sending email: " + e.getMessage(), 
                "Email Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewProfile() {
        String profile = "Student Profile\n" +
                        "================\n" +
                        "Student ID: " + student.getStudentId() + "\n" +
                        "Full Name: " + student.getFullName() + "\n" +
                        "Email: " + student.getEmail() + "\n" +
                        "Program: " + student.getProgram() + "\n" +
                        "Year of Study: " + student.getYearOfStudy() + "\n" +
                        "Current Semester: " + student.getCurrentSemester() + "\n" +
                        "CGPA: " + String.format("%.2f", student.getCgpa()) + "\n" +
                        "Total Credit Hours: " + student.getTotalCreditHours() + "\n" +
                        "Eligibility Status: " + (student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE");
        
        JOptionPane.showMessageDialog(this, profile, "Student Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void changePassword() {
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        
        Object[] message = {
            "Current Password:", oldPasswordField,
            "New Password:", newPasswordField,
            "Confirm New Password:", confirmPasswordField
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean success = student.changePassword(oldPassword, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnToLogin() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Return to login page? This will allow you to login as a different user.", 
            "Return to Login", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            authService.logout(student);
            
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
            authService.logout(student);
            
            // Exit application
            System.exit(0);
        }
    }
}