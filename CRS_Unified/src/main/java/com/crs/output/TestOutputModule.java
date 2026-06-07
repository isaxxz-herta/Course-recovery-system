package com.crs.output;

import com.crs.model.Student;
import java.util.ArrayList;
import java.util.List;

public class TestOutputModule {
    public static void main(String[] args) {
        System.out.println("=== OUTPUT MODULE DEMO ===");

        Student s = new Student(
                "U001",
                "jdoe",
                "password123",
                "john@example.com",
                "John Doe",
                "TP123",
                "Computer Science"
        );

        // Add some grades to the student
        s.addCourseGrade("OOPD101", "Object Oriented Programming", 3, "A", 4.0);
        s.addCourseGrade("DB101", "Database Systems", 3, "B+", 3.3);
        s.addCourseGrade("NET101", "Computer Networking", 3, "B", 3.0);

        List<SimpleEnrollment> enrollments = new ArrayList<>();
        enrollments.add(new SimpleEnrollment("Object Oriented Programming", 4.0));
        enrollments.add(new SimpleEnrollment("Database Systems", 3.3));
        enrollments.add(new SimpleEnrollment("Computer Networking", 3.0));

        ReportGenerator generator = new ReportGenerator();
        AcademicReport report = generator.generateReport(s, "Semester 2", enrollments);

        boolean saved = generator.exportToFile(report, "student_report.txt");
        System.out.println("Report saved: " + saved);

        EmailService email = new EmailService("smtp.gmail.com", 587, "noreply@crs.com");
        email.sendEmail(
                s.getEmail(),
                "Your Academic Report is Ready",
                "Hello " + s.getFullName() + ",\nYour report has been generated."
        );

        email.sendPasswordReset(s.getEmail(), "ABC123TOKEN");
        
        System.out.println("=== OUTPUT MODULE DEMO COMPLETED ===");
    }
}