package com.crs.academic.grade;

import com.crs.academic.submission.AssessmentSubmission;
import java.util.List;

public interface GradeCalculator {
    double calculateFinalScore(List<AssessmentSubmission> submissions);
    double convertToGradePoint(double finalScore);
}