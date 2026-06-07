package com.crs.ui;

import com.crs.model.*;
import com.crs.service.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserDialog extends JDialog {
    private User user;
    private UserManager userManager;
    private boolean userUpdated = false;
    
    private JTextField emailField;
    private JTextField fullNameField;
    private JCheckBox activeCheckBox;

    public EditUserDialog(Frame parent, User user, UserManager userManager) {
        super(parent, "Edit User - " + user.getFullName(), true);
        this.user = user;
        this.userManager = userManager;
        
        initializeDialog();
        loadUserData();
    }

    private void initializeDialog() {
        setSize(350, 200);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // User info (read-only)
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JLabel(user.getUserId()), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JLabel(user.getUsername()), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JLabel(user.getRole().getDisplayName()), gbc);

        // Editable fields
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        fullNameField = new JTextField(20);
        mainPanel.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Active:"), gbc);
        gbc.gridx = 1;
        activeCheckBox = new JCheckBox();
        mainPanel.add(activeCheckBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void loadUserData() {
        emailField.setText(user.getEmail());
        fullNameField.setText(user.getFullName());
        activeCheckBox.setSelected(user.isActive());
    }

    private void updateUser() {
        String email = emailField.getText().trim();
        String fullName = fullNameField.getText().trim();
        boolean active = activeCheckBox.isSelected();

        if (email.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            user.setEmail(email);
            user.setFullName(fullName);
            user.setActive(active);
            
            userUpdated = true;
            JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isUserUpdated() {
        return userUpdated;
    }
}