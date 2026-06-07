package com.crs.ui;

import com.crs.auth.AuthService;
import com.crs.model.*;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;
import java.util.Scanner;

public class OfficerConsoleInterface {
    private AcademicOfficer officer;
    private UserManager userManager;
    private AuthService authService;
    private PersistenceService persistence;
    private Scanner scanner;

    public OfficerConsoleInterface(AcademicOfficer officer, UserManager userManager, AuthService authService, PersistenceService persistence) {
        this.officer = officer;
        this.userManager = userManager;
        this.authService = authService;
        this.persistence = persistence;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n=== ACADEMIC OFFICER CONSOLE ===");
        System.out.println("Welcome, " + officer.getFullName());
        System.out.println("Faculty: " + officer.getFaculty());
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    viewAllStudents();
                    break;
                case 2:
                    viewStudentDetails();
                    break;
                case 3:
                    checkStudentEligibility();
                    break;
                case 4:
                    checkAllStudentsEligibility();
                    break;
                case 5:
                    generateStudentReports();
                    break;
                case 6:
                    generateAllStudentReports();
                    break;
                case 7:
                    identifyAtRiskStudents();
                    break;
                case 8:
                    changeMyPassword();
                    break;
                case 9:
                    saveData();
                    break;
                case 10:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== ACADEMIC OFFICER MENU ===");
        System.out.println("1. View All Students");
        System.out.println("2. View Student Details");
        System.out.println("3. Check Student Eligibility");
        System.out.println("4. Check All Students Eligibility");
        System.out.println("5. Generate Student Report");
        System.out.println("6. Generate All Student Reports");
        System.out.println("7. Identify At-Risk Students");
        System.out.println("8. Change My Password");
        System.out.println("9. Save Data");
        System.out.println("10. Logout");
        System.out.print("Enter choice (1-10): ");
    }

    private int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        } finally {
            scanner.nextLine(); // Consume newline
        }
    }

    private void viewAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        
        if (userManager.getAllStudents().isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.printf("%-12s %-25s %-30s %-8s %-8s %-12s%n", 
                         "Student ID", "Full Name", "Program", "CGPA", "Credits", "Eligibility");
        System.out.println("-".repeat(100));
        
        for (Student student : userManager.getAllStudents()) {
            student.calculateCGPA();
            student.checkEligibility();
            
            System.out.printf("%-12s %-25s %-30s %-8.2f %-8d %-12s%n",
                             student.getStudentId(),
                             student.getFullName(),
                             student.getProgram(),
                             student.getCgpa(),
                             student.getTotalCreditHours(),
                             student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE");
        }
        
        System.out.println("\nTotal students: " + userManager.getAllStudents().size());
    }

    private void viewStudentDetails() {
        System.out.println("\n=== VIEW STUDENT DETAILS ===");
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println(student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024));
    }

    private void checkStudentEligibility() {
        System.out.println("\n=== CHECK STUDENT ELIGIBILITY ===");
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Checking eligibility for: " + student.getFullName());
        student.checkEligibility();
        
        System.out.println("\nSummary:");
        System.out.println("CGPA: " + String.format("%.2f", student.getCgpa()));
        System.out.println("Failed Courses: " + student.getFailedCourses().size());
        System.out.println("Status: " + (student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE"));
    }

    private void checkAllStudentsEligibility() {
        System.out.println("\n=== CHECK ALL STUDENTS ELIGIBILITY ===");
        
        officer.checkStudentEligibility(userManager.getAllStudents());
        
        // Additional summary
        int eligible = 0, notEligible = 0;
        for (Student student : userManager.getAllStudents()) {
            if (student.isEligibleForProgression()) {
                eligible++;
            } else {
                notEligible++;
            }
        }
        
        System.out.println("\n=== DETAILED SUMMARY ===");
        System.out.println("Eligible Students: " + eligible);
        System.out.println("Not Eligible Students: " + notEligible);
        System.out.println("Eligibility Rate: " + String.format("%.1f%%", (double)eligible / userManager.getAllStudents().size() * 100));
    }

    private void generateStudentReports() {
        System.out.println("\n=== GENERATE STUDENT REPORT ===");
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        
        System.out.println("Generating report for: " + student.getFullName());
        System.out.println(student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024));
    }

    private void generateAllStudentReports() {
        System.out.println("\n=== GENERATE ALL STUDENT REPORTS ===");
        
        officer.generateBulkAcademicReports(userManager.getAllStudents(), 1, 2025);
    }

    private void identifyAtRiskStudents() {
        System.out.println("\n=== IDENTIFY AT-RISK STUDENTS ===");
        
        System.out.println("Analyzing student performance...\n");
        
        int atRiskCount = 0;
        
        for (Student student : userManager.getAllStudents()) {
            student.calculateCGPA();
            student.checkEligibility();
            
            boolean isAtRisk = false;
            StringBuilder riskFactors = new StringBuilder();
            
            if (student.getCgpa() < 2.5) {
                isAtRisk = true;
                if (student.getCgpa() < 2.0) {
                    riskFactors.append("Critical CGPA (").append(String.format("%.2f", student.getCgpa())).append(") ");
                } else {
                    riskFactors.append("Low CGPA (").append(String.format("%.2f", student.getCgpa())).append(") ");
                }
            }
            
            if (student.getFailedCourses().size() > 0) {
                isAtRisk = true;
                riskFactors.append("Failed courses (").append(student.getFailedCourses().size()).append(") ");
            }
            
            if (!student.isEligibleForProgression()) {
                isAtRisk = true;
                riskFactors.append("Not eligible for progression ");
            }
            
            if (isAtRisk) {
                atRiskCount++;
                System.out.println("AT-RISK STUDENT:");
                System.out.println("Name: " + student.getFullName());
                System.out.println("ID: " + student.getStudentId());
                System.out.println("Program: " + student.getProgram());
                System.out.println("CGPA: " + String.format("%.2f", student.getCgpa()));
                System.out.println("Failed Courses: " + student.getFailedCourses().size());
                System.out.println("Risk Factors: " + riskFactors.toString());
                
                System.out.println("Recommended Actions:");
                if (student.getCgpa() < 2.0) {
                    System.out.println("- Immediate academic intervention required");
                    System.out.println("- Academic probation consideration");
                }
                if (student.getCgpa() >= 2.0 && student.getCgpa() < 2.5) {
                    System.out.println("- Academic counseling recommended");
                    System.out.println("- Study skills workshop");
                }
                if (student.getFailedCourses().size() > 0) {
                    System.out.println("- Course retake planning");
                    System.out.println("- Prerequisites review");
                }
                System.out.println("- Regular progress monitoring");
                System.out.println("- Faculty advisor consultation");
                
                System.out.println("-".repeat(60));
            }
        }
        
        System.out.println("\n=== AT-RISK ANALYSIS SUMMARY ===");
        System.out.println("Total Students: " + userManager.getAllStudents().size());
        System.out.println("At-Risk Students: " + atRiskCount);
        System.out.println("At-Risk Percentage: " + String.format("%.1f%%", (double)atRiskCount / userManager.getAllStudents().size() * 100));
        
        if (atRiskCount == 0) {
            System.out.println("Great news! No students are currently identified as at-risk.");
        } else {
            System.out.println("Recommendation: Schedule individual meetings with at-risk students.");
        }
    }

    private void changeMyPassword() {
        System.out.println("\n=== CHANGE PASSWORD ===");
        System.out.print("Current password: ");
        String currentPassword = scanner.nextLine();
        
        System.out.print("New password: ");
        String newPassword = scanner.nextLine();
        
        boolean success = officer.changePassword(currentPassword, newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Current password is incorrect.");
        }
    }

    private void saveData() {
        System.out.println("\n=== SAVE DATA ===");
        boolean success = persistence.save(userManager);
        if (success) {
            System.out.println("Data saved successfully.");
        } else {
            System.out.println("Failed to save data.");
        }
    }

    private void logout() {
        System.out.println("\n=== LOGOUT ===");
        System.out.print("Are you sure you want to logout? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().startsWith("y")) {
            authService.logout(officer);
            System.out.println("Logged out successfully. Goodbye!");
        }
    }

    private Student findStudentById(String studentId) {
        for (Student student : userManager.getAllStudents()) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }
}