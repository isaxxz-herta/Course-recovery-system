package com.crs.output;

/**
 * Simplified Enrollment class for output module compatibility
 */
public class SimpleEnrollment {
    private String courseName;
    private double gpa;

    public SimpleEnrollment(String courseName, double gpa) {
        this.courseName = courseName;
        this.gpa = gpa;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}