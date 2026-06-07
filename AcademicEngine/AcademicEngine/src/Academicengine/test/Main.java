package academicengine.test;

import academicengine.course.*;
import academicengine.enrollment.*;
import academicengine.attempt.*;
import academicengine.submission.*;
import academicengine.grade.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
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
    }
}
