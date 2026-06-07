package com.crs.service;

import com.crs.model.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements Serializable {
    private static final long serialVersionUID = 10L;

    private List<User> users;
    private List<Student> students;
    private List<Administrator> administrators;
    private List<AcademicOfficer> academicOfficers;

    public UserManager() {
        users = new ArrayList<>();
        students = new ArrayList<>();
        administrators = new ArrayList<>();
        academicOfficers = new ArrayList<>();
    }

    public User createUser(String userId, String username, String password, 
                          String email, String fullName, Role role, 
                          String additionalId, String departmentOrFaculty) {

        User user;

        switch (role) {
            case STUDENT:
                Student student = new Student(userId, username, password, email, 
                                             fullName, additionalId, departmentOrFaculty);
                users.add(student);
                students.add(student);
                user = student;
                break;

            case ADMINISTRATOR:
                Administrator admin = new Administrator(userId, username, password, 
                                                       email, fullName, additionalId, departmentOrFaculty);
                users.add(admin);
                administrators.add(admin);
                user = admin;
                break;

            case ACADEMIC_OFFICER:
                AcademicOfficer officer = new AcademicOfficer(userId, username, password, 
                                                             email, fullName, additionalId, departmentOrFaculty);
                users.add(officer);
                academicOfficers.add(officer);
                user = officer;
                break;

            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }

        System.out.println("User created successfully: " + user);
        return user;
    }

    public boolean updateUserProfile(String userId, String email, String fullName) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setEmail(email);
                user.setFullName(fullName);
                System.out.println("Profile updated for user: " + userId);
                return true;
            }
        }
        System.out.println("User not found: " + userId);
        return false;
    }

    public boolean deactivateUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setActive(false);
                System.out.println("User account deactivated: " + userId);
                return true;
            }
        }
        return false;
    }

    public boolean activateUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setActive(true);
                System.out.println("User account activated: " + userId);
                return true;
            }
        }
        return false;
    }

    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && 
                user.getPassword().equals(password) && 
                user.isActive()) {
                user.login();
                System.out.println("Authentication successful for: " + user.getFullName());
                return user;
            }
        }
        System.out.println("Authentication failed for username: " + username);
        return null;
    }

    public List<Student> getNonEligibleStudents() {
        List<Student> nonEligible = new ArrayList<>();
        for (Student student : students) {
            student.checkEligibility();
            if (!student.isEligibleForProgression()) {
                nonEligible.add(student);
            }
        }
        return nonEligible;
    }

    public List<User> getAllUsers() { return users; }
    public List<Student> getAllStudents() { return students; }
    public List<Administrator> getAllAdministrators() { return administrators; }
    public List<AcademicOfficer> getAllAcademicOfficers() { return academicOfficers; }

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}