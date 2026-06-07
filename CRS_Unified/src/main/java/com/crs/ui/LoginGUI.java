package com.crs.ui;

import com.crs.model.*;
import com.crs.service.UserManager;
import com.crs.persistence.PersistenceService;
import com.crs.auth.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private UserManager userManager;
    private PersistenceService persistence;
    private AuthService authService;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginGUI(UserManager userManager, PersistenceService persistence, AuthService authService) {
        this.userManager = userManager;
        this.persistence = persistence;
        this.authService = authService;
        
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("CRS - Course Recovery System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Course Recovery System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        // Login form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        formPanel.add(loginButton, gbc);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);

        // Default accounts info
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Available Accounts"));
        infoPanel.add(new JLabel("Admin: admin / admin123"));
        infoPanel.add(new JLabel("Officer: officer / officer123"));
        infoPanel.add(new JLabel("Student: student1 / student123"));
        infoPanel.add(new JLabel("Student: student2 / student123"));
        
        // Quick login buttons
        JPanel quickLoginPanel = new JPanel(new FlowLayout());
        quickLoginPanel.setBorder(BorderFactory.createTitledBorder("Quick Login"));
        
        JButton adminQuickBtn = new JButton("Login as Admin");
        JButton officerQuickBtn = new JButton("Login as Officer");
        JButton studentQuickBtn = new JButton("Login as Student1");
        
        adminQuickBtn.addActionListener(e -> quickLogin("admin", "admin123"));
        officerQuickBtn.addActionListener(e -> quickLogin("officer", "officer123"));
        studentQuickBtn.addActionListener(e -> quickLogin("student1", "student123"));
        
        quickLoginPanel.add(adminQuickBtn);
        quickLoginPanel.add(officerQuickBtn);
        quickLoginPanel.add(studentQuickBtn);

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(infoPanel, BorderLayout.CENTER);
        bottomPanel.add(quickLoginPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add action listeners
        loginButton.addActionListener(new LoginActionListener());
        passwordField.addActionListener(new LoginActionListener()); // Enter key in password field
        
        // Set focus to username field
        SwingUtilities.invokeLater(() -> usernameField.requestFocus());
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please enter both username and password");
                return;
            }

            User user = authService.authenticate(username, password);
            
            if (user != null) {
                statusLabel.setText("Login successful! Opening " + user.getRole() + " interface...");
                statusLabel.setForeground(Color.GREEN);
                
                // Delay to show success message
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openRoleInterface(user);
                        dispose(); // Close login window
                    }
                });
                timer.setRepeats(false);
                timer.start();
                
            } else {
                statusLabel.setText("Invalid username or password");
                statusLabel.setForeground(Color.RED);
                passwordField.setText("");
                passwordField.requestFocus();
            }
        }
    }

    private void quickLogin(String username, String password) {
        usernameField.setText(username);
        passwordField.setText(password);
        
        User user = authService.authenticate(username, password);
        
        if (user != null) {
            statusLabel.setText("Quick login successful! Opening " + user.getRole() + " interface...");
            statusLabel.setForeground(Color.GREEN);
            
            // Delay to show success message
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openRoleInterface(user);
                    dispose(); // Close login window
                }
            });
            timer.setRepeats(false);
            timer.start();
            
        } else {
            statusLabel.setText("Quick login failed - please check system setup");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void openRoleInterface(User user) {
        switch (user.getRole()) {
            case ADMINISTRATOR:
                AdminGUI adminGUI = new AdminGUI((Administrator) user, userManager, authService, persistence);
                adminGUI.setVisible(true);
                break;
                
            case ACADEMIC_OFFICER:
                OfficerGUI officerGUI = new OfficerGUI((AcademicOfficer) user, userManager, authService, persistence);
                officerGUI.setVisible(true);
                break;
                
            case STUDENT:
                StudentGUI studentGUI = new StudentGUI((Student) user, userManager, authService, persistence);
                studentGUI.setVisible(true);
                break;
                
            default:
                JOptionPane.showMessageDialog(this, "Unknown role: " + user.getRole(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}