package academicengine.grade;

import academicengine.enrollment.Enrollment;
import academicengine.submission.AssessmentSubmission;
import java.util.List;

public class Grade implements GradeCalculator {
    private Enrollment enrollment;
    private double finalScore;
    private double gradePoint;

    public Grade(Enrollment enrollment, double finalScore) {
        if (enrollment == null) {
            throw new IllegalArgumentException("enrollment must not be null");
        }
        this.enrollment = enrollment;
        this.finalScore = finalScore;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }
    
    
    
    public void computeFinalGrade() {
        // Example: finalScore already set, convert to gradePoint
        if (finalScore >= 85) {
            gradePoint = 4.0;
        } else if (finalScore >= 70) {
            gradePoint = 3.0;
        } else if (finalScore >= 50) {
            gradePoint = 2.0;
        } else {
            gradePoint = 0.0;
        }

        System.out.println("Final grade computed: " + gradePoint);
    }

    public void showAcademicResults() {
        System.out.println("Results for student " + enrollment.getStudentId()
                + ": Final Score = " + finalScore
                + ", Grade Point = " + gradePoint);
    }
    
    // Reporting hook
    public String toReportRow() {
        return enrollment.getStudentId() + "," + enrollment.getCourse().getCourseCode() + "," + finalScore + "," + gradePoint;
    }
    
    @Override
    public double calculateFinalScore(List<AssessmentSubmission> submissions) {
        double total = 0;
        for (AssessmentSubmission sub : submissions) {
            total += sub.getMarksObtained() * (sub.getComponent().getWeightage() / 100);
        }
        this.finalScore = total;
        return finalScore;
    }

    @Override
    public double convertToGradePoint(double finalScore) {
        if (finalScore >= 85) gradePoint = 4.0;
        else if (finalScore >= 70) gradePoint = 3.0;
        else if (finalScore >= 50) gradePoint = 2.0;
        else gradePoint = 0.0;
        return gradePoint;
    }
    
}
