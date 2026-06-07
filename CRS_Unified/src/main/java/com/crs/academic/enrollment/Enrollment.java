package com.crs.academic.enrollment;

import com.crs.academic.course.Course;
import com.crs.academic.attempt.CourseAttempt;

public class Enrollment implements EnrollmentManager {
    private String studentId; // link to Student from teammate's module
    private Course course;
    private EnrollmentStatus status;
    private CourseAttempt attempt;

    public Enrollment(String studentId, Course course) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("studentId must not be blank");
        }
        if (course == null) {
            throw new IllegalArgumentException("course must not be null");
        }
        this.studentId = studentId;
        this.course = course;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public String getStudentId() {
        return studentId;
    }

    public Course getCourse() {
        return course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.status = status;
    }

    public CourseAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(CourseAttempt attempt) {
        this.attempt = attempt;
    }

    public void registerStudent() {
        this.status = EnrollmentStatus.ACTIVE;
        System.out.println("Student " + studentId + " registered for " + course.getCourseName());
    }

    public void completeEnrollment() {
        this.status = EnrollmentStatus.COMPLETED;
        System.out.println("Enrollment completed for " + studentId);
    }
    
    @Override
    public Enrollment enrollStudent(String studentId, Course course) {
        return new Enrollment(studentId, course);
    }

    @Override
    public void completeEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("enrollment must not be null");
        }
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
    }

    @Override
    public void failEnrollment(Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("enrollment must not be null");
        }
        enrollment.setStatus(EnrollmentStatus.FAILED);
    } 
}