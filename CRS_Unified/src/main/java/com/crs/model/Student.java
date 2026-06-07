package com.crs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 2L;

    private String studentId;
    private String program;
    private int currentSemester;
    private int yearOfStudy;
    private double cgpa;
    private int totalCreditHours;
    private final List<CourseGrade> courseGrades;
    private final List<FailedCourse> failedCourses;
    private boolean eligibleForProgression;

    public Student(String userId, String username, String password, String email, 
                   String fullName, String studentId, String program) {
        super(userId, username, password, email, fullName, Role.STUDENT);
        this.studentId = studentId;
        this.program = program;
        this.currentSemester = 1;
        this.yearOfStudy = 1;
        this.cgpa = 0.0;
        this.totalCreditHours = 0;
        this.courseGrades = new ArrayList<>();
        this.failedCourses = new ArrayList<>();
        this.eligibleForProgression = false;
    }

    public void calculateCGPA() {
        if (courseGrades.isEmpty()) {
            this.cgpa = 0.0;
            this.totalCreditHours = 0;
            return;
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (CourseGrade grade : courseGrades) {
            totalGradePoints += grade.getGradePoint() * grade.getCreditHours();
            totalCredits += grade.getCreditHours();
        }

        this.cgpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
        this.totalCreditHours = totalCredits;

        checkEligibility();
    }

    public void checkEligibility() {
        boolean cgpaCriteria = this.cgpa >= 2.0;
        boolean failedCoursesCriteria = failedCourses.size() <= 3;

        this.eligibleForProgression = cgpaCriteria && failedCoursesCriteria;

        System.out.println("Eligibility Check for " + getFullName() + ":");
        System.out.println("  CGPA: " + cgpa + " (Required: >= 2.0) - " + 
                          (cgpaCriteria ? "PASS" : "FAIL"));
        System.out.println("  Failed Courses: " + failedCourses.size() + 
                          " (Max: 3) - " + (failedCoursesCriteria ? "PASS" : "FAIL"));
        System.out.println("  Overall Eligibility: " + 
                          (eligibleForProgression ? "ELIGIBLE" : "NOT ELIGIBLE"));
    }

    public void addCourseGrade(String courseCode, String courseTitle, 
                               int creditHours, String grade, double gradePoint) {
        CourseGrade courseGrade = new CourseGrade(courseCode, courseTitle, 
                                                 creditHours, grade, gradePoint);
        courseGrades.add(courseGrade);
        calculateCGPA();
    }

    public void addFailedCourse(String courseCode, String courseTitle, 
                                int creditHours, String reason) {
        FailedCourse failedCourse = new FailedCourse(courseCode, courseTitle, 
                                                    creditHours, reason);
        failedCourses.add(failedCourse);
        checkEligibility();
    }

    public String generateAcademicReport(int semester, int year) {
        StringBuilder report = new StringBuilder();

        report.append("=============================================\n");
        report.append("ACADEMIC PERFORMANCE REPORT\n");
        report.append("=============================================\n");
        report.append("Student Name: ").append(getFullName()).append("\n");
        report.append("Student ID: ").append(studentId).append("\n");
        report.append("Program: ").append(program).append("\n");
        report.append("Year of Study: ").append(yearOfStudy).append("\n");
        report.append("Semester: ").append(currentSemester).append("\n\n");

        report.append("COURSE GRADES:\n");
        report.append("-----------------------------------------------------------------\n");
        report.append(String.format("%-10s %-25s %-12s %-10s %-15s\n", 
            "Code", "Title", "Credits", "Grade", "Grade Point"));
        report.append("-----------------------------------------------------------------\n");

        for (CourseGrade grade : courseGrades) {
            report.append(String.format("%-10s %-25s %-12d %-10s %-15.2f\n",
                grade.getCourseCode(),
                grade.getCourseTitle(),
                grade.getCreditHours(),
                grade.getGrade(),
                grade.getGradePoint()));
        }

        report.append("\n");
        report.append("Cumulative GPA (CGPA): ").append(String.format("%.2f", cgpa)).append("\n");
        report.append("Total Credit Hours: ").append(totalCreditHours).append("\n");
        report.append("Eligible for Progression: ").append(eligibleForProgression ? "YES" : "NO").append("\n");

        if (!failedCourses.isEmpty()) {
            report.append("\nFAILED COURSES:\n");
            report.append("-----------------------------------------------------------------\n");
            for (FailedCourse failed : failedCourses) {
                report.append(String.format("%s - %s (Credits: %d) - Reason: %s\n",
                    failed.getCourseCode(),
                    failed.getCourseTitle(),
                    failed.getCreditHours(),
                    failed.getReason()));
            }
        }

        return report.toString();
    }

    @Override
    public String getUserDetails() {
        return String.format("Student ID: %s, Program: %s, Year: %d, Semester: %d, CGPA: %.2f",
                studentId, program, yearOfStudy, currentSemester, cgpa);
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public int getCurrentSemester() { return currentSemester; }
    public void setCurrentSemester(int currentSemester) { 
        this.currentSemester = currentSemester; 
    }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public double getCgpa() { return cgpa; }

    public int getTotalCreditHours() { return totalCreditHours; }

    public List<CourseGrade> getCourseGrades() { return courseGrades; }

    public List<FailedCourse> getFailedCourses() { return failedCourses; }

    public boolean isEligibleForProgression() { return eligibleForProgression; }
    public void setEligibleForProgression(boolean eligibleForProgression) { 
        this.eligibleForProgression = eligibleForProgression; 
    }
}