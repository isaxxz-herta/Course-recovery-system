package com.crs.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import com.crs.model.AcademicOfficer;
import com.crs.model.Student;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;

public class OfficerGUI extends JFrame {
    private AcademicOfficer officer;
    private UserManager userManager;
    private AuthService authService;
    private PersistenceService persistence;
    
    private JTabbedPane tabbedPane;
    private JTable studentsTable;
    private DefaultTableModel studentsTableModel;
    private JTextArea reportArea;
    private JTextArea eligibilityArea;

    public OfficerGUI(AcademicOfficer officer, UserManager userManager, AuthService authService, PersistenceService persistence) {
        this.officer = officer;
        this.userManager = userManager;
        this.authService = authService;
        this.persistence = persistence;
        
        initializeGUI();
        loadStudentData();
    }

    private void initializeGUI() {
        setTitle("CRS - Academic Officer Dashboard - " + officer.getFullName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu reportsMenu = new JMenu("Reports");
        
        JMenuItem saveItem = new JMenuItem("Save Data");
        JMenuItem returnToLoginItem = new JMenuItem("Return to Login");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem exportAllReportsItem = new JMenuItem("Export All Student Reports");
        JMenuItem eligibilityReportItem = new JMenuItem("Generate Eligibility Report");
        
        saveItem.addActionListener(e -> saveData());
        returnToLoginItem.addActionListener(e -> returnToLogin());
        logoutItem.addActionListener(e -> logout());
        exportAllReportsItem.addActionListener(e -> exportAllReports());
        eligibilityReportItem.addActionListener(e -> generateEligibilityReport());
        
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(returnToLoginItem);
        fileMenu.add(logoutItem);
        
        reportsMenu.add(exportAllReportsItem);
        reportsMenu.add(eligibilityReportItem);
        
        menuBar.add(fileMenu);
        menuBar.add(reportsMenu);
        setJMenuBar(menuBar);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Student Overview Tab
        tabbedPane.addTab("Student Overview", createStudentOverviewPanel());
        
        // Eligibility Management Tab
        tabbedPane.addTab("Eligibility Management", createEligibilityPanel());
        
        // Academic Reports Tab
        tabbedPane.addTab("Academic Reports", createReportsPanel());
        
        // Recovery Plans Tab
        tabbedPane.addTab("Recovery Plans", createRecoveryPlansPanel());

        add(tabbedPane);
    }

    private JPanel createStudentOverviewPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Students table
        String[] columns = {"Student ID", "Full Name", "Program", "CGPA", "Credit Hours", "Failed Courses", "Eligibility"};
        studentsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentsTable = new JTable(studentsTableModel);
        studentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane tableScrollPane = new JScrollPane(studentsTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("Refresh Data");
        JButton viewDetailsBtn = new JButton("View Student Details");
        JButton checkEligibilityBtn = new JButton("Check Selected Student");
        JButton checkAllEligibilityBtn = new JButton("Check All Students");
        JButton generateReportBtn = new JButton("Generate Report for Selected");

        refreshBtn.addActionListener(e -> loadStudentData());
        viewDetailsBtn.addActionListener(e -> viewStudentDetails());
        checkEligibilityBtn.addActionListener(e -> checkSelectedStudentEligibility());
        checkAllEligibilityBtn.addActionListener(e -> checkAllStudentsEligibility());
        generateReportBtn.addActionListener(e -> generateSelectedStudentReport());

        buttonPanel.add(refreshBtn);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(checkEligibilityBtn);
        buttonPanel.add(checkAllEligibilityBtn);
        buttonPanel.add(generateReportBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createEligibilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        eligibilityArea = new JTextArea();
        eligibilityArea.setEditable(false);
        eligibilityArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(eligibilityArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton checkAllBtn = new JButton("Check All Students Eligibility");
        JButton exportEligibilityBtn = new JButton("Export Eligibility Report");
        JButton clearBtn = new JButton("Clear");
        
        checkAllBtn.addActionListener(e -> performFullEligibilityCheck());
        exportEligibilityBtn.addActionListener(e -> exportEligibilityReport());
        clearBtn.addActionListener(e -> eligibilityArea.setText(""));
        
        buttonPanel.add(checkAllBtn);
        buttonPanel.add(exportEligibilityBtn);
        buttonPanel.add(clearBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton generateAllReportsBtn = new JButton("Generate All Student Reports");
        JButton exportReportsBtn = new JButton("Export All Reports");
        JButton clearBtn = new JButton("Clear");
        
        generateAllReportsBtn.addActionListener(e -> generateAllStudentReports());
        exportReportsBtn.addActionListener(e -> exportAllReports());
        clearBtn.addActionListener(e -> reportArea.setText(""));
        
        buttonPanel.add(generateAllReportsBtn);
        buttonPanel.add(exportReportsBtn);
        buttonPanel.add(clearBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createRecoveryPlansPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JTextArea recoveryArea = new JTextArea();
        recoveryArea.setEditable(false);
        recoveryArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        recoveryArea.setText("Recovery Plans Management\n" +
                           "========================\n\n" +
                           "This section will help manage recovery plans for students who are not eligible for progression.\n\n" +
                           "Features coming soon:\n" +
                           "- Create recovery plans for failing students\n" +
                           "- Track recovery progress\n" +
                           "- Set milestones and deadlines\n" +
                           "- Monitor improvement\n");
        
        JScrollPane scrollPane = new JScrollPane(recoveryArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton identifyAtRiskBtn = new JButton("Identify At-Risk Students");
        
        identifyAtRiskBtn.addActionListener(e -> identifyAtRiskStudents(recoveryArea));
        
        buttonPanel.add(identifyAtRiskBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void loadStudentData() {
        studentsTableModel.setRowCount(0);
        
        for (Student student : userManager.getAllStudents()) {
            student.calculateCGPA(); // Ensure CGPA is up to date
            student.checkEligibility(); // Update eligibility status
            
            Object[] row = {
                student.getStudentId(),
                student.getFullName(),
                student.getProgram(),
                String.format("%.2f", student.getCgpa()),
                student.getTotalCreditHours(),
                student.getFailedCourses().size(),
                student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE"
            };
            studentsTableModel.addRow(row);
        }
    }

    private void viewStudentDetails() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to view details.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = (String) studentsTableModel.getValueAt(selectedRow, 0);
        Student student = null;
        
        for (Student s : userManager.getAllStudents()) {
            if (s.getStudentId().equals(studentId)) {
                student = s;
                break;
            }
        }
        
        if (student != null) {
            String details = student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024);
            
            JDialog detailsDialog = new JDialog(this, "Student Details - " + student.getFullName(), true);
            detailsDialog.setSize(600, 500);
            detailsDialog.setLocationRelativeTo(this);
            
            JTextArea detailsArea = new JTextArea(details);
            detailsArea.setEditable(false);
            detailsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(detailsArea);
            detailsDialog.add(scrollPane);
            
            detailsDialog.setVisible(true);
        }
    }

    private void checkSelectedStudentEligibility() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to check eligibility.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = (String) studentsTableModel.getValueAt(selectedRow, 0);
        Student student = null;
        
        for (Student s : userManager.getAllStudents()) {
            if (s.getStudentId().equals(studentId)) {
                student = s;
                break;
            }
        }
        
        if (student != null) {
            student.checkEligibility();
            loadStudentData(); // Refresh table
            
            String status = student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE";
            JOptionPane.showMessageDialog(this, 
                "Eligibility check completed for " + student.getFullName() + "\n" +
                "Status: " + status + "\n" +
                "CGPA: " + String.format("%.2f", student.getCgpa()) + "\n" +
                "Failed Courses: " + student.getFailedCourses().size(), 
                "Eligibility Check Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void checkAllStudentsEligibility() {
        List<Student> students = userManager.getAllStudents();
        officer.checkStudentEligibility(students);
        loadStudentData(); // Refresh table
        
        JOptionPane.showMessageDialog(this, 
            "Eligibility check completed for all students.\nCheck console for detailed results.", 
            "Eligibility Check Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateSelectedStudentReport() {
        int selectedRow = studentsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to generate report.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String studentId = (String) studentsTableModel.getValueAt(selectedRow, 0);
        Student student = null;
        
        for (Student s : userManager.getAllStudents()) {
            if (s.getStudentId().equals(studentId)) {
                student = s;
                break;
            }
        }
        
        if (student != null) {
            String report = student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024);
            reportArea.setText(report);
            tabbedPane.setSelectedIndex(2); // Switch to reports tab
        }
    }

    private void performFullEligibilityCheck() {
        StringBuilder eligibilityReport = new StringBuilder();
        eligibilityReport.append("=== COMPREHENSIVE ELIGIBILITY CHECK ===\n");
        eligibilityReport.append("Generated by: ").append(officer.getFullName()).append("\n");
        eligibilityReport.append("Date: ").append(java.time.LocalDate.now()).append("\n");
        eligibilityReport.append("Faculty: ").append(officer.getFaculty()).append("\n\n");
        
        int eligible = 0, notEligible = 0;
        
        for (Student student : userManager.getAllStudents()) {
            student.checkEligibility();
            
            eligibilityReport.append("Student: ").append(student.getFullName()).append("\n");
            eligibilityReport.append("ID: ").append(student.getStudentId()).append("\n");
            eligibilityReport.append("Program: ").append(student.getProgram()).append("\n");
            eligibilityReport.append("CGPA: ").append(String.format("%.2f", student.getCgpa())).append(" (Required: >= 2.0)\n");
            eligibilityReport.append("Failed Courses: ").append(student.getFailedCourses().size()).append(" (Max: 3)\n");
            eligibilityReport.append("Status: ").append(student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE").append("\n");
            
            if (!student.isEligibleForProgression()) {
                eligibilityReport.append("Issues: ");
                if (student.getCgpa() < 2.0) eligibilityReport.append("Low CGPA ");
                if (student.getFailedCourses().size() > 3) eligibilityReport.append("Too many failed courses ");
                eligibilityReport.append("\n");
            }
            
            eligibilityReport.append("\n");
            
            if (student.isEligibleForProgression()) eligible++;
            else notEligible++;
        }
        
        eligibilityReport.append("=== SUMMARY ===\n");
        eligibilityReport.append("Total Students: ").append(userManager.getAllStudents().size()).append("\n");
        eligibilityReport.append("Eligible for Progression: ").append(eligible).append("\n");
        eligibilityReport.append("Not Eligible: ").append(notEligible).append("\n");
        eligibilityReport.append("Eligibility Rate: ").append(String.format("%.1f%%", (double)eligible / userManager.getAllStudents().size() * 100)).append("\n");
        
        eligibilityArea.setText(eligibilityReport.toString());
        loadStudentData(); // Refresh table
    }

    private void generateAllStudentReports() {
        StringBuilder allReports = new StringBuilder();
        allReports.append("=== ALL STUDENT ACADEMIC REPORTS ===\n");
        allReports.append("Generated by: ").append(officer.getFullName()).append("\n");
        allReports.append("Date: ").append(java.time.LocalDate.now()).append("\n\n");
        
        for (Student student : userManager.getAllStudents()) {
            allReports.append(student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024));
            allReports.append("\n").append("=".repeat(80)).append("\n\n");
        }
        
        reportArea.setText(allReports.toString());
    }

    private void exportAllReports() {
        try {
            StringBuilder allReports = new StringBuilder();
            allReports.append("=== ALL STUDENT ACADEMIC REPORTS ===\n");
            allReports.append("Generated by: ").append(officer.getFullName()).append("\n");
            allReports.append("Date: ").append(java.time.LocalDate.now()).append("\n\n");
            
            for (Student student : userManager.getAllStudents()) {
                allReports.append(student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024));
                allReports.append("\n").append("=".repeat(80)).append("\n\n");
            }
            
            String filename = "all_student_reports_" + java.time.LocalDate.now() + ".txt";
            java.nio.file.Files.write(java.nio.file.Paths.get(filename), allReports.toString().getBytes());
            
            JOptionPane.showMessageDialog(this, 
                "All student reports exported to: " + filename, 
                "Export Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting reports: " + e.getMessage(), 
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportEligibilityReport() {
        try {
            String filename = "eligibility_report_" + java.time.LocalDate.now() + ".txt";
            java.nio.file.Files.write(java.nio.file.Paths.get(filename), eligibilityArea.getText().getBytes());
            
            JOptionPane.showMessageDialog(this, 
                "Eligibility report exported to: " + filename, 
                "Export Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting eligibility report: " + e.getMessage(), 
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateEligibilityReport() {
        performFullEligibilityCheck();
        tabbedPane.setSelectedIndex(1); // Switch to eligibility tab
    }

    private void identifyAtRiskStudents(JTextArea recoveryArea) {
        StringBuilder atRiskReport = new StringBuilder();
        atRiskReport.append("=== AT-RISK STUDENTS & RECOVERY PLANS ===\n");
        atRiskReport.append("Generated by: ").append(officer.getFullName()).append("\n");
        atRiskReport.append("Date: ").append(java.time.LocalDate.now()).append("\n\n");
        
        // Use the recovery system
        com.crs.recovery.RecoveryManager recoveryManager = new com.crs.recovery.RecoveryManager();
        com.crs.recovery.EligibilityChecker eligibilityChecker = recoveryManager.getEligibilityChecker();
        
        List<Student> atRiskStudents = new ArrayList<>();
        List<Student> ineligibleStudents = new ArrayList<>();
        
        for (Student student : userManager.getAllStudents()) {
            student.checkEligibility();
            
            // Students needing recovery plans (not eligible)
            if (!student.isEligibleForProgression()) {
                ineligibleStudents.add(student);
                atRiskStudents.add(student);
            }
            // Students at risk but still eligible
            else if (student.getCgpa() < 2.5 || student.getFailedCourses().size() > 0) {
                atRiskStudents.add(student);
            }
        }
        
        if (atRiskStudents.isEmpty()) {
            atRiskReport.append("✅ No at-risk students identified. All students are performing well.\n");
        } else {
            atRiskReport.append("📊 SUMMARY:\n");
            atRiskReport.append("Total At-Risk Students: ").append(atRiskStudents.size()).append("\n");
            atRiskReport.append("Requiring Recovery Plans: ").append(ineligibleStudents.size()).append("\n\n");
            
            // Generate recovery plans for ineligible students
            if (!ineligibleStudents.isEmpty()) {
                atRiskReport.append("🚨 STUDENTS REQUIRING RECOVERY PLANS:\n");
                atRiskReport.append("=" .repeat(50)).append("\n");
                
                List<com.crs.recovery.RecoveryPlan> generatedPlans = 
                    recoveryManager.generateRecoveryPlansForIneligibleStudents(ineligibleStudents, officer.getUserId());
                
                for (Student student : ineligibleStudents) {
                    atRiskReport.append("\n📋 STUDENT: ").append(student.getFullName()).append("\n");
                    atRiskReport.append("ID: ").append(student.getStudentId()).append("\n");
                    atRiskReport.append("CGPA: ").append(String.format("%.2f", student.getCgpa())).append("\n");
                    atRiskReport.append("Failed Courses: ").append(student.getFailedCourses().size()).append("\n");
                    
                    // Show detailed eligibility report
                    String eligibilityReport = eligibilityChecker.getEligibilityReport(student);
                    String[] reportLines = eligibilityReport.split("\n");
                    for (int i = 8; i < Math.min(reportLines.length, 15); i++) { // Show key parts
                        atRiskReport.append(reportLines[i]).append("\n");
                    }
                    
                    // Show generated recovery plans
                    List<com.crs.recovery.RecoveryPlan> studentPlans = 
                        recoveryManager.getRecoveryPlansForStudent(student.getStudentId());
                    
                    if (!studentPlans.isEmpty()) {
                        atRiskReport.append("\n🔧 RECOVERY PLANS CREATED:\n");
                        for (com.crs.recovery.RecoveryPlan plan : studentPlans) {
                            atRiskReport.append("• ").append(plan.toString()).append("\n");
                            atRiskReport.append("  Milestones: ").append(plan.getMilestones().size()).append(" tasks\n");
                        }
                    }
                    
                    atRiskReport.append("\n" + "-".repeat(40) + "\n");
                }
            }
            
            // Show other at-risk students
            List<Student> otherAtRisk = atRiskStudents.stream()
                .filter(s -> s.isEligibleForProgression())
                .toList();
                
            if (!otherAtRisk.isEmpty()) {
                atRiskReport.append("\n⚠️ OTHER AT-RISK STUDENTS (Still Eligible):\n");
                atRiskReport.append("=" .repeat(50)).append("\n");
                
                for (Student student : otherAtRisk) {
                    atRiskReport.append("\n📝 ").append(student.getFullName()).append(" (").append(student.getStudentId()).append(")\n");
                    atRiskReport.append("CGPA: ").append(String.format("%.2f", student.getCgpa()));
                    atRiskReport.append(" | Failed Courses: ").append(student.getFailedCourses().size()).append("\n");
                    
                    atRiskReport.append("Risk Factors: ");
                    if (student.getCgpa() < 2.5) atRiskReport.append("Borderline CGPA ");
                    if (student.getFailedCourses().size() > 0) atRiskReport.append("Has failed courses ");
                    
                    atRiskReport.append("\nRecommendations: ");
                    if (student.getCgpa() < 2.5) atRiskReport.append("Academic support, ");
                    if (student.getFailedCourses().size() > 0) atRiskReport.append("Monitor closely, ");
                    atRiskReport.append("Preventive intervention\n");
                }
            }
            
            // Show recovery statistics
            atRiskReport.append("\n").append(recoveryManager.getRecoveryStatistics());
        }
        
        recoveryArea.setText(atRiskReport.toString());
    }

    private void saveData() {
        boolean success = persistence.save(userManager);
        if (success) {
            JOptionPane.showMessageDialog(this, "Data saved successfully.", "Save Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save data.", "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToLogin() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Return to login page? This will allow you to login as a different user.", 
            "Return to Login", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            authService.logout(officer);
            
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
            authService.logout(officer);
            
            // Save data before exiting
            saveData();
            
            // Exit application
            System.exit(0);
        }
    }
}