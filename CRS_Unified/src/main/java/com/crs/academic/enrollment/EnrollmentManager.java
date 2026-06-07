package com.crs.academic.enrollment;

import com.crs.academic.course.Course;

public interface EnrollmentManager {
    Enrollment enrollStudent(String studentId, Course course);
    void completeEnrollment(Enrollment enrollment);
    void failEnrollment(Enrollment enrollment);
}