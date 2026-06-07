package com.crs.academic.test;

import com.crs.academic.attempt.*;
import com.crs.academic.course.*;
import com.crs.academic.enrollment.*;
import com.crs.academic.grade.*;
import com.crs.academic.submission.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicEngineDemo {

    public static void runDemo() {
        System.out.println("=== ACADEMIC ENGINE DEMO ===");
        
        // Course setup
        Course oopCourse = new Course("CS101", "Object-Oriented Programming", "Java basics");
        oopCourse.addAssessmentComponent(new AssessmentComponent("Assignment 1", 30, 100));
        oopCourse.addAssessmentComponent(new AssessmentComponent("Final Exam", 70, 100));

        // Enrollment
        Enrollment enrollment = new Enrollment("S12345", oopCourse);
        enrollment.registerStudent();

        // Attempt
        CourseAttempt attempt = new CourseAttempt(1, AttemptStatus.IN_PROGRESS);
        enrollment.setAttempt(attempt);

        // Submissions
        AssessmentSubmission submission1 = new AssessmentSubmission("S12345", oopCourse.getAssessmentComponents().get(0), 80, "2025-12-10");
        submission1.recordSubmission(80, "2025-12-10");

        AssessmentSubmission submission2 = new AssessmentSubmission("S12345", oopCourse.getAssessmentComponents().get(1), 75, "2025-12-12");
        submission2.recordSubmission(75, "2025-12-12");

        // Grade via interface
        List<AssessmentSubmission> subs = new ArrayList<>();
        subs.add(submission1);
        subs.add(submission2);

        Grade grade = new Grade(enrollment, 0);
        double finalScore = grade.calculateFinalScore(subs);
        grade.setFinalScore(finalScore);
        grade.computeFinalGrade();
        grade.showAcademicResults();

        // Complete enrollment
        enrollment.completeEnrollment();
        
        System.out.println("=== ACADEMIC ENGINE DEMO COMPLETED ===");
    }

    public static void main(String[] args) {
        runDemo();
    }
}