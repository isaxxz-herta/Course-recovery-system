package com.crs.ui;

import com.crs.auth.AuthService;
import com.crs.model.*;
import com.crs.output.*;
import com.crs.persistence.PersistenceService;
import com.crs.service.UserManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentConsoleInterface {
    private Student student;
    private UserManager userManager;
    private AuthService authService;
    private PersistenceService persistence;
    private Scanner scanner;

    public StudentConsoleInterface(Student student, UserManager userManager, AuthService authService, PersistenceService persistence) {
        this.student = student;
        this.userManager = userManager;
        this.authService = authService;
        this.persistence = persistence;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("\n=== STUDENT PORTAL ===");
        System.out.println("Welcome, " + student.getFullName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Program: " + student.getProgram());
        
        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    viewAcademicOverview();
                    break;
                case 3:
                    viewCourseGrades();
                    break;
                case 4:
                    viewFailedCourses();
                    break;
                case 5:
                    checkEligibility();
                    break;
                case 6:
                    generateAcademicReport();
                    break;
                case 7:
                    exportAcademicReport();
                    break;
                case 8:
                    changePassword();
                    break;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== STUDENT MENU ===");
        System.out.println("1. View Profile");
        System.out.println("2. Academic Overview");
        System.out.println("3. View Course Grades");
        System.out.println("4. View Failed Courses");
        System.out.println("5. Check Eligibility");
        System.out.println("6. Generate Academic Report");
        System.out.println("7. Export Academic Report");
        System.out.println("8. Change Password");
        System.out.println("9. Logout");
        System.out.print("Enter choice (1-9): ");
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

    private void viewProfile() {
        System.out.println("\n=== STUDENT PROFILE ===");
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Full Name: " + student.getFullName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Program: " + student.getProgram());
        System.out.println("Year of Study: " + student.getYearOfStudy());
        System.out.println("Current Semester: " + student.getCurrentSemester());
        System.out.println("Account Status: " + (student.isActive() ? "Active" : "Inactive"));
        System.out.println("Created: " + student.getCreatedAt());
    }

    private void viewAcademicOverview() {
        System.out.println("\n=== ACADEMIC OVERVIEW ===");
        
        // Update CGPA
        student.calculateCGPA();
        
        System.out.println("Current CGPA: " + String.format("%.2f", student.getCgpa()));
        System.out.println("Total Credit Hours: " + student.getTotalCreditHours());
        System.out.println("Courses Completed: " + student.getCourseGrades().size());
        System.out.println("Failed Courses: " + student.getFailedCourses().size());
        
        // Check eligibility
        student.checkEligibility();
        System.out.println("Eligibility Status: " + (student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE"));
        
        if (!student.isEligibleForProgression()) {
            System.out.println("\nNote: You are not currently eligible for progression.");
            System.out.println("Please consult with your academic advisor for guidance.");
        }
    }

    private void viewCourseGrades() {
        System.out.println("\n=== COURSE GRADES ===");
        
        if (student.getCourseGrades().isEmpty()) {
            System.out.println("No course grades recorded.");
            return;
        }
        
        System.out.printf("%-12s %-30s %-8s %-8s %-12s%n", 
                         "Course Code", "Course Title", "Credits", "Grade", "Grade Point");
        System.out.println("-".repeat(75));
        
        for (CourseGrade grade : student.getCourseGrades()) {
            System.out.printf("%-12s %-30s %-8d %-8s %-12.2f%n",
                             grade.getCourseCode(),
                             grade.getCourseTitle(),
                             grade.getCreditHours(),
                             grade.getGrade(),
                             grade.getGradePoint());
        }
        
        System.out.println("\nCurrent CGPA: " + String.format("%.2f", student.getCgpa()));
    }

    private void viewFailedCourses() {
        System.out.println("\n=== FAILED COURSES ===");
        
        if (student.getFailedCourses().isEmpty()) {
            System.out.println("No failed courses recorded. Great job!");
            return;
        }
        
        System.out.printf("%-12s %-30s %-8s %-40s%n", 
                         "Course Code", "Course Title", "Credits", "Reason");
        System.out.println("-".repeat(95));
        
        for (FailedCourse failed : student.getFailedCourses()) {
            System.out.printf("%-12s %-30s %-8d %-40s%n",
                             failed.getCourseCode(),
                             failed.getCourseTitle(),
                             failed.getCreditHours(),
                             failed.getReason());
        }
        
        System.out.println("\nTotal Failed Courses: " + student.getFailedCourses().size());
        System.out.println("Note: You can retake failed courses to improve your academic standing.");
    }

    private void checkEligibility() {
        System.out.println("\n=== ELIGIBILITY CHECK ===");
        
        student.checkEligibility();
        
        System.out.println("\nEligibility Criteria:");
        System.out.println("- Minimum CGPA: 2.0 (Current: " + String.format("%.2f", student.getCgpa()) + ")");
        System.out.println("- Maximum Failed Courses: 3 (Current: " + student.getFailedCourses().size() + ")");
        
        System.out.println("\nOverall Status: " + (student.isEligibleForProgression() ? "ELIGIBLE" : "NOT ELIGIBLE"));
        
        if (!student.isEligibleForProgression()) {
            System.out.println("\nRecommendations:");
            if (student.getCgpa() < 2.0) {
                System.out.println("- Focus on improving your grades in current courses");
                System.out.println("- Consider seeking academic support or tutoring");
            }
            if (student.getFailedCourses().size() > 3) {
                System.out.println("- Plan to retake some failed courses");
                System.out.println("- Consult with your academic advisor for a recovery plan");
            }
        }
    }

    private void generateAcademicReport() {
        System.out.println("\n=== ACADEMIC REPORT ===");
        
        String report = student.generateAcademicReport(student.getCurrentSemester(), student.getYearOfStudy() + 2024);
        System.out.println(report);
    }

    private void exportAcademicReport() {
        System.out.println("\n=== EXPORT ACADEMIC REPORT ===");
        
        try {
            // Create enrollments for report generator
            List<SimpleEnrollment> enrollments = new ArrayList<>();
            for (CourseGrade grade : student.getCourseGrades()) {
                enrollments.add(new SimpleEnrollment(grade.getCourseTitle(), grade.getGradePoint()));
            }
            
            ReportGenerator generator = new ReportGenerator();
            AcademicReport report = generator.generateReport(student, "Current Semester", enrollments);
            
            String filename = "academic_report_" + student.getStudentId() + "_" + java.time.LocalDate.now() + ".txt";
            boolean success = generator.exportToFile(report, filename);
            
            if (success) {
                System.out.println("Report exported successfully to: " + filename);
                
                // Ask if they want to email the report
                System.out.print("Would you like to email this report to yourself? (y/n): ");
                String emailChoice = scanner.nextLine();
                
                if (emailChoice.toLowerCase().startsWith("y")) {
                    emailReport();
                }
            } else {
                System.out.println("Failed to export report.");
            }
        } catch (Exception e) {
            System.out.println("Error exporting report: " + e.getMessage());
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
                System.out.println("Report sent to your email: " + student.getEmail());
            }
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    private void changePassword() {
        System.out.println("\n=== CHANGE PASSWORD ===");
        
        System.out.print("Current password: ");
        String currentPassword = scanner.nextLine();
        
        System.out.print("New password: ");
        String newPassword = scanner.nextLine();
        
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("New passwords do not match.");
            return;
        }
        
        boolean success = student.changePassword(currentPassword, newPassword);
        if (success) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Current password is incorrect.");
        }
    }

    private void logout() {
        System.out.println("\n=== LOGOUT ===");
        System.out.print("Are you sure you want to logout? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.toLowerCase().startsWith("y")) {
            authService.logout(student);
            System.out.println("Logged out successfully. Goodbye!");
        }
    }
}