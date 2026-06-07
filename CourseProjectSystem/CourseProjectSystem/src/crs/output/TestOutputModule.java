package crs.output;

import com.crs.model.Student;
import academicengine.enrollment.Enrollment;
import academicengine.course.Course;

import java.util.ArrayList;
import java.util.List;

public class TestOutputModule {
    public static void main(String[] args) {

        
        Student s = new Student(
                "U001",
                "jdoe",
                "password123",
                "john@example.com",
                "John Doe",
                "TP123",
                "Computer Science"
        );

       
        Course oopd = new Course("OOPD101", "OOPD", "Object Oriented Programming");
        Course db = new Course("DB101", "Databases", "Database Systems");
        Course net = new Course("NET101", "Networking", "Computer Networking");


List<Enrollment> enrollments = new ArrayList<>();
enrollments.add(new Enrollment("OOPD", 3.7));
enrollments.add(new Enrollment("Databases", 3.3));
enrollments.add(new Enrollment("Networking", 3.0));


        
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
    }
}
