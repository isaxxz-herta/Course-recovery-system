package com.crs.academic.submission;

import com.crs.academic.course.AssessmentComponent;

public interface SubmissionRecorder {
    AssessmentSubmission record(String studentId, AssessmentComponent component, double marks, String date);
    void update(AssessmentSubmission submission, double marks, String date);
}