package com.crs.ui;

import com.crs.service.UserManager;
import com.crs.persistence.PersistenceService;
import com.crs.model.Student;
import com.crs.model.User;
import com.crs.model.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainGUI extends JFrame {
    private UserManager manager;
    private PersistenceService persistence;
    private JTextArea outputArea;

    public MainGUI(UserManager manager, PersistenceService persistence) {
        this.manager = manager;
        this.persistence = persistence;
        initUI();
    }

    private void initUI() {
        setTitle("CRS - User & Student Module (GUI)"); 
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDemo = new JButton("Run Demo Scenario");
        JButton btnShowReports = new JButton("Show All Student Reports");
        JButton btnSave = new JButton("Save Data");
        JButton btnLoad = new JButton("Load Data");

        top.add(btnDemo); top.add(btnShowReports); top.add(btnSave); top.add(btnLoad);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnDemo.addActionListener((ActionEvent e) -> runDemo());
        btnShowReports.addActionListener((ActionEvent e) -> showReports());
        btnSave.addActionListener((ActionEvent e) -> saveData());
        btnLoad.addActionListener((ActionEvent e) -> loadData());
    }

    private void append(String s) {
        outputArea.append(s + "\n");
    }

    private void runDemo() {
        append("Running demo scenario...");
        // create sample users if none exist
        if (manager.getAllUsers().isEmpty()) {
            manager.createUser("U001", "alex123", "password123", "alex.tan@student.edu", "Alex Tan", Role.STUDENT, "2025A1234", "Bachelor of Computer Science");
            manager.createUser("U002", "sarah456", "password456", "sarah.lee@student.edu", "Sarah Lee", Role.STUDENT, "2025B5678", "Bachelor of Information Technology");
            manager.createUser("U003", "admin01", "admin123", "admin@crs.edu", "John Smith", Role.ADMINISTRATOR, "EMP001", "IT Department");
            manager.createUser("U004", "officer01", "officer123", "officer@crs.edu", "Dr. Emily Wong", Role.ACADEMIC_OFFICER, "EMP002", "Faculty of Computing");
        } else {
            append("Data already present. Use 'Load Data' to refresh from disk.");
        }
        append("Demo users created. Add grades via run.sh/demo or call manager methods."); 
    }

    private void showReports() {
        append("\n=== STUDENT REPORTS ===");
        List<Student> students = manager.getAllStudents();
        if (students.isEmpty()) {
            append("No students found. Run demo to create sample students."); return;
        }
        for (Student s : students) {
            append(s.generateAcademicReport(1, 2025));
            append("---------------------------------------------");
        }
    }

    private void saveData() {
        boolean ok = persistence.save(manager);
        JOptionPane.showMessageDialog(this, ok ? "Data saved." : "Save failed."); 
        append("Save operation: " + (ok ? "success" : "failed"));
    }

    private void loadData() {
        UserManager loaded = persistence.load();
        if (loaded != null) {
            this.manager = loaded;
            append("Data loaded from disk. Users: " + manager.getAllUsers().size());
        } else {
            append("No saved data found or load failed.");
        }
    }
}
