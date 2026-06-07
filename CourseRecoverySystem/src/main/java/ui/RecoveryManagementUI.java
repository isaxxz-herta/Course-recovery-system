package ui;

import recovery.*;
import com.crs.model.Student;
import com.crs.model.FailedCourse;
import storage.DataStorage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Main UI for Course Recovery Management System
 * Person 4 - Recovery & Eligibility Module
 * Updated to work with Person 2's Student class
 */
public class RecoveryManagementUI extends JFrame {
    private final DataStorage storage;
    private List<RecoveryPlan> recoveryPlans;
    private List<Student> students;
    private final EligibilityChecker eligibilityChecker;
    
    // UI Components
    private JTabbedPane tabbedPane;
    private JTable eligibilityTable;
    private JTable recoveryTable;
    private DefaultTableModel eligibilityModel;
    private DefaultTableModel recoveryModel;
    
    public RecoveryManagementUI() {
        storage = new DataStorage();
        eligibilityChecker = new EligibilityChecker();
        loadData();
        initComponents();
    }
    
    private void loadData() {
        recoveryPlans = storage.loadRecoveryPlans();
        students = storage.loadStudentsFromPerson2();
    }
    
    private void saveData() {
        storage.saveRecoveryPlans(recoveryPlans);
    }
    
    private void initComponents() {
        setTitle("Course Recovery Management System - Person 4 Module");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Eligibility Check", createEligibilityPanel());
        tabbedPane.addTab("Recovery Plans", createRecoveryPanel());
        
        add(tabbedPane);
    }
    
    // ========== ELIGIBILITY CHECK TAB ==========
    private JPanel createEligibilityPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel title = new JLabel("Student Eligibility Check", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Student ID", "Name", "Program", "Year", "CGPA", 
                           "Failed Courses", "Status"};
        eligibilityModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        eligibilityTable = new JTable(eligibilityModel);
        eligibilityTable.setRowHeight(30);
        eligibilityTable.setFont(new Font("Arial", Font.PLAIN, 12));
        eligibilityTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        loadEligibilityData();
        
        JScrollPane scrollPane = new JScrollPane(eligibilityTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnCheck = new JButton("View Detailed Report");
        btnCheck.setFont(new Font("Arial", Font.BOLD, 12));
        btnCheck.addActionListener(e -> checkSelectedStudent());
        
        JButton btnCreatePlan = new JButton("Create Recovery Plan");
        btnCreatePlan.setFont(new Font("Arial", Font.BOLD, 12));
        btnCreatePlan.addActionListener(e -> createRecoveryPlan());
        
        JButton btnShowFailed = new JButton("Show Failed Courses");
        btnShowFailed.setFont(new Font("Arial", Font.BOLD, 12));
        btnShowFailed.addActionListener(e -> showFailedCourses());
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> {
            loadData();
            loadEligibilityData();
        });
        
        btnPanel.add(btnCheck);
        btnPanel.add(btnCreatePlan);
        btnPanel.add(btnShowFailed);
        btnPanel.add(btnRefresh);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadEligibilityData() {
        eligibilityModel.setRowCount(0);
        for (Student student : students) {
            String status = student.isEligibleForProgression() ? "✓ ELIGIBLE" : "✗ NOT ELIGIBLE";
            
            eligibilityModel.addRow(new Object[]{
                student.getStudentId(),
                student.getFullName(),
                student.getProgram(),
                student.getYearOfStudy(),
                String.format("%.2f", student.getCgpa()),
                student.getFailedCourses().size(),
                status
            });
        }
    }
    
    private void checkSelectedStudent() {
        int row = eligibilityTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a student first!",
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Student student = students.get(row);
        String report = eligibilityChecker.getEligibilityReport(student);
        
        JTextArea textArea = new JTextArea(report);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setCaretPosition(0);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Eligibility Report - " + student.getFullName(), 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showFailedCourses() {
        int row = eligibilityTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a student first!",
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Student student = students.get(row);
        List<FailedCourse> failedCourses = student.getFailedCourses();
        
        if (failedCourses.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "This student has no failed courses!",
                "No Failed Courses", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Failed Courses for ").append(student.getFullName()).append("\n");
        sb.append("Student ID: ").append(student.getStudentId()).append("\n\n");
        sb.append(String.format("%-15s %-30s %-10s %s\n", 
            "Course Code", "Course Title", "Credits", "Reason"));
        sb.append("─".repeat(80)).append("\n");
        
        for (FailedCourse fc : failedCourses) {
            sb.append(String.format("%-15s %-30s %-10d %s\n",
                fc.getCourseCode(),
                fc.getCourseTitle(),
                fc.getCreditHours(),
                fc.getReason()));
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Failed Courses", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ========== RECOVERY PLANS TAB ==========
    private JPanel createRecoveryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel title = new JLabel("Recovery Plans Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Plan ID", "Student ID", "Student Name", 
                           "Course", "Status", "Progress", "Milestones"};
        recoveryModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        recoveryTable = new JTable(recoveryModel);
        recoveryTable.setRowHeight(30);
        recoveryTable.setFont(new Font("Arial", Font.PLAIN, 12));
        recoveryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        loadRecoveryData();
        
        JScrollPane scrollPane = new JScrollPane(recoveryTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnView = new JButton("View Details");
        btnView.setFont(new Font("Arial", Font.BOLD, 12));
        btnView.addActionListener(e -> viewPlanDetails());
        
        JButton btnAdd = new JButton("Add Milestone");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdd.addActionListener(e -> addMilestone());
        
        JButton btnUpdate = new JButton("Update Progress");
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 12));
        btnUpdate.addActionListener(e -> updateProgress());
        
        JButton btnDelete = new JButton("Delete Plan");
        btnDelete.addActionListener(e -> deletePlan());
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadRecoveryData());
        
        btnPanel.add(btnView);
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadRecoveryData() {
        recoveryModel.setRowCount(0);
        for (RecoveryPlan plan : recoveryPlans) {
            String studentName = getStudentName(plan.getStudentId());
            recoveryModel.addRow(new Object[]{
                plan.getPlanId(),
                plan.getStudentId(),
                studentName,
                plan.getCourseCode() + " - " + plan.getCourseName(),
                plan.getStatus(),
                plan.calculateProgress() + "%",
                plan.getMilestones().size()
            });
        }
    }
    
    private String getStudentName(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return s.getFullName();
            }
        }
        return "Unknown";
    }
    
    private void createRecoveryPlan() {
        int row = eligibilityTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an ineligible student from Eligibility Check tab first!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Student student = students.get(row);
        
        if (student.isEligibleForProgression()) {
            JOptionPane.showMessageDialog(this, 
                "Student is already eligible. No recovery plan needed!",
                "Already Eligible",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (student.getFailedCourses().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Student has no failed courses!",
                "No Failed Courses",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Select failed course
        FailedCourse selectedCourse = (FailedCourse) JOptionPane.showInputDialog(
            this,
            "Select failed course for recovery plan:",
            "Create Recovery Plan",
            JOptionPane.QUESTION_MESSAGE,
            null,
            student.getFailedCourses().toArray(),
            student.getFailedCourses().get(0)
        );
        
        if (selectedCourse == null) return;
        
        // Create plan
        RecoveryPlan plan = new RecoveryPlan(
            student.getStudentId(),
            selectedCourse.getCourseCode(),
            selectedCourse.getCourseTitle(),
            "ADMIN001"
        );
        
        // Get recommendations
        String recommendations = JOptionPane.showInputDialog(
            this,
            """
            Enter recommendations for the student:
            (Failed Reason: """ + selectedCourse.getReason() + ")",
            "Recommendations",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (recommendations != null && !recommendations.trim().isEmpty()) {
            plan.setRecommendations(recommendations);
        }
        
        recoveryPlans.add(plan);
        saveData();
        loadRecoveryData();
        
        JOptionPane.showMessageDialog(this, 
            """
            Recovery plan created successfully!
            Plan ID: """ + plan.getPlanId() + "\n" +
            "Student: " + student.getFullName() + "\n" +
            "Course: " + selectedCourse.getCourseCode(),
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Switch to Recovery Plans tab
        tabbedPane.setSelectedIndex(1);
    }
    
    private void viewPlanDetails() {
        int row = recoveryTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a recovery plan first!");
            return;
        }
        
        RecoveryPlan plan = recoveryPlans.get(row);
        Student student = findStudentById(plan.getStudentId());
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        infoPanel.add(new JLabel("Plan ID:"));
        infoPanel.add(new JLabel(plan.getPlanId()));
        infoPanel.add(new JLabel("Student ID:"));
        infoPanel.add(new JLabel(plan.getStudentId()));
        infoPanel.add(new JLabel("Student Name:"));
        infoPanel.add(new JLabel(student != null ? student.getFullName() : "Unknown"));
        infoPanel.add(new JLabel("Program:"));
        infoPanel.add(new JLabel(student != null ? student.getProgram() : "Unknown"));
        infoPanel.add(new JLabel("Course:"));
        infoPanel.add(new JLabel(plan.getCourseCode() + " - " + plan.getCourseName()));
        infoPanel.add(new JLabel("Status:"));
        infoPanel.add(new JLabel(plan.getStatus().toString()));
        infoPanel.add(new JLabel("Progress:"));
        infoPanel.add(new JLabel(plan.calculateProgress() + "%"));
        infoPanel.add(new JLabel("Created Date:"));
        infoPanel.add(new JLabel(plan.getCreatedDate().toString()));
        
        if (plan.getRecommendations() != null) {
            infoPanel.add(new JLabel("Recommendations:"));
            JTextArea recArea = new JTextArea(plan.getRecommendations(), 2, 20);
            recArea.setEditable(false);
            recArea.setLineWrap(true);
            recArea.setWrapStyleWord(true);
            infoPanel.add(new JScrollPane(recArea));
        }
        
        panel.add(infoPanel, BorderLayout.NORTH);
        
        // Milestones
        JTextArea milestonesArea = new JTextArea(12, 50);
        milestonesArea.setEditable(false);
        milestonesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("MILESTONES:\n");
        sb.append("═".repeat(80)).append("\n\n");
        
        if (plan.getMilestones().isEmpty()) {
            sb.append("No milestones added yet.\n");
            sb.append("\nTip: Click 'Add Milestone' to create a recovery schedule.");
        } else {
            for (int i = 0; i < plan.getMilestones().size(); i++) {
                Milestone m = plan.getMilestones().get(i);
                sb.append(String.format("%d. %s\n", i + 1, m.toString()));
                sb.append(String.format("   Description: %s\n", m.getDescription()));
                if (m.isCompleted() && m.getCompletionDate() != null) {
                    sb.append(String.format("   Completed: %s\n", m.getCompletionDate()));
                }
                sb.append("\n");
            }
        }
        
        milestonesArea.setText(sb.toString());
        milestonesArea.setCaretPosition(0);
        panel.add(new JScrollPane(milestonesArea), BorderLayout.CENTER);
        
        JOptionPane.showMessageDialog(this, panel, "Recovery Plan Details", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void addMilestone() {
        int row = recoveryTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a recovery plan first!");
            return;
        }
        
        RecoveryPlan plan = recoveryPlans.get(row);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField descField = new JTextField(20);
        JSpinner weekStartSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        JSpinner weekEndSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        JTextField taskField = new JTextField(20);
        
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Week Start:"));
        panel.add(weekStartSpinner);
        panel.add(new JLabel("Week End:"));
        panel.add(weekEndSpinner);
        panel.add(new JLabel("Task:"));
        panel.add(taskField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Add Milestone", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String desc = descField.getText().trim();
            int weekStart = (int) weekStartSpinner.getValue();
            int weekEnd = (int) weekEndSpinner.getValue();
            String task = taskField.getText().trim();
            
            if (desc.isEmpty() || task.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all fields!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (weekStart > weekEnd) {
                JOptionPane.showMessageDialog(this, 
                    "Week start cannot be after week end!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Milestone milestone = new Milestone(desc, weekStart, weekEnd, task);
            plan.addMilestone(milestone);
            plan.updateStatus();
            saveData();
            loadRecoveryData();
            
            JOptionPane.showMessageDialog(this, 
                """
                Milestone added successfully!
                Total milestones: """ + plan.getMilestones().size(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateProgress() {
        int row = recoveryTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a recovery plan first!");
            return;
        }
        
        RecoveryPlan plan = recoveryPlans.get(row);
        
        if (plan.getMilestones().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No milestones to update! Please add milestones first.",
                "No Milestones",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String[] milestoneOptions = new String[plan.getMilestones().size()];
        for (int i = 0; i < plan.getMilestones().size(); i++) {
            Milestone m = plan.getMilestones().get(i);
            String status = m.isCompleted() ? "[✓]" : "[ ]";
            milestoneOptions[i] = String.format("%s %d. %s", status, i + 1, m.getTask());
        }
        
        String selected = (String) JOptionPane.showInputDialog(
            this,
            "Select milestone to mark as completed:",
            "Update Progress",
            JOptionPane.QUESTION_MESSAGE,
            null,
            milestoneOptions,
            milestoneOptions[0]
        );
        
        if (selected != null) {
            int index = Integer.parseInt(selected.split("\\.")[0].replaceAll("[^0-9]", "")) - 1;
            Milestone milestone = plan.getMilestones().get(index);
            
            if (milestone.isCompleted()) {
                JOptionPane.showMessageDialog(this, 
                    "This milestone is already completed!",
                    "Already Completed",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            milestone.setCompleted(true);
            plan.updateStatus();
            saveData();
            loadRecoveryData();
            
            JOptionPane.showMessageDialog(this, 
                """
                Milestone marked as completed!
                Progress: """ + plan.calculateProgress() + "%\n" +
                "Status: " + plan.getStatus(),
                "Progress Updated",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void deletePlan() {
        int row = recoveryTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a recovery plan first!");
            return;
        }
        
        RecoveryPlan plan = recoveryPlans.get(row);
        
        int confirm;
        confirm = JOptionPane.showConfirmDialog(this,
                """
                            Are you sure you want to delete this recovery plan?
                                             Plan ID: """ + plan.getPlanId() + "\n" +
                                    "Student: " + getStudentName(plan.getStudentId()) + "\n" +
                                            "Course: " + plan.getCourseCode(),
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            recoveryPlans.remove(row);
            saveData();
            loadRecoveryData();
            JOptionPane.showMessageDialog(this, 
                "Recovery plan deleted successfully!",
                "Deleted",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private Student findStudentById(String studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            }
            new RecoveryManagementUI().setVisible(true);
        });
    }
}