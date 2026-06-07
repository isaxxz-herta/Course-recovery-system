package com.crs.output;

import com.crs.model.Student;
import java.util.Date;
import java.util.List;

public class AcademicReport {

    private String reportId;
    private Student student;
    private String semester;
    private Date generatedDate;
    private List<SimpleEnrollment> enrollments;
    private double cgpa;
    private double gpa;
    private List<String> recommendations;

    // Constructor
    public AcademicReport(String reportId,
                          Student student,
                          String semester,
                          Date generatedDate,
                          List<SimpleEnrollment> enrollments,
                          double cgpa,
                          double gpa,
                          List<String> recommendations) {
        this.reportId = reportId;
        this.student = student;
        this.semester = semester;
        this.generatedDate = generatedDate;
        this.enrollments = enrollments;
        this.cgpa = cgpa;
        this.gpa = gpa;
        this.recommendations = recommendations;
    }

    // Getters and Setters
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public List<SimpleEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<SimpleEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}