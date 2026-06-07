package crs.output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import academicengine.enrollment.Enrollment;
import academicengine.course.Course;
import com.crs.model.Student;

public class ReportGenerator {

    public AcademicReport generateReport(Student student, String semester, List<Enrollment> enrollments) {
        String reportId = "RPT-" + System.currentTimeMillis();
        Date generatedDate = new Date();

        double cgpa = student.getCgpa();
        double gpa = 0.0; 
        List<String> recommendations = generateRecommendations(cgpa);

        return new AcademicReport(reportId, student, semester, generatedDate, enrollments, cgpa, gpa, recommendations);
    }

    private List<String> generateRecommendations(double cgpa) {
        List<String> rec = new ArrayList<>();
        if (cgpa >= 3.5) rec.add("Excellent performance. Keep it up!");
        else if (cgpa >= 3.0) rec.add("Good standing. Consider advanced courses.");
        else if (cgpa >= 2.0) rec.add("Satisfactory. Improvement recommended.");
        else rec.add("At risk. Please consult academic advisor.");
        return rec;
    }

    public boolean exportToFile(AcademicReport report, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("=== Academic Report ===\n");
            writer.write("Report ID: " + report.getReportId() + "\n");
            writer.write("Student: " + report.getStudent().getFullName() + "\n");
            writer.write("Semester: " + report.getSemester() + "\n");
            writer.write("Generated On: " + report.getGeneratedDate() + "\n\n");

            writer.write("--- Enrolled Courses ---\n");
for (Enrollment e : report.getEnrollments()) {
    writer.write("Course: " + e.getCourseName()
            + " | GPA: " + e.getGpa() + "\n");
}

            writer.write("\nSemester GPA: " + report.getGpa() + "\n");
            writer.write("Cumulative GPA: " + report.getCgpa() + "\n\n");

            writer.write("--- Recommendations ---\n");
            for (String r : report.getRecommendations()) writer.write("- " + r + "\n");

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
