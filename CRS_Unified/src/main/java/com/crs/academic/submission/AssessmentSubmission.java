package com.crs.academic.submission;

import com.crs.academic.course.AssessmentComponent;

public class AssessmentSubmission implements SubmissionRecorder {
    private String studentId;
    private AssessmentComponent component;
    private double marksObtained;
    private String submissionDate;

    public AssessmentSubmission(String studentId, AssessmentComponent component, double marksObtained, String submissionDate) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("studentId must not be blank");
        }
        if (component == null) {
            throw new IllegalArgumentException("component must not be null");
        }
        if (marksObtained < 0 || marksObtained > component.getMaxMarks()) {
            throw new IllegalArgumentException("marks must be between 0 and " + component.getMaxMarks());
        }
        this.studentId = studentId;
        this.component = component;
        this.marksObtained = marksObtained;
        this.submissionDate = submissionDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public AssessmentComponent getComponent() {
        return component;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        if (component == null) {
            throw new IllegalStateException("component not set");
        }
        if (marksObtained < 0 || marksObtained > component.getMaxMarks()) {
            throw new IllegalArgumentException("marks must be between 0 and " + component.getMaxMarks());
        }
        this.marksObtained = marksObtained;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void recordSubmission(double marks, String date) {
        this.marksObtained = marks;
        this.submissionDate = date;
        System.out.println("Submission recorded for " + studentId + " on " + date);
    }
    
    @Override
    public AssessmentSubmission record(String studentId, AssessmentComponent component, double marks, String date) {
        return new AssessmentSubmission(studentId, component, marks, date);
    }

    @Override
    public void update(AssessmentSubmission submission, double marks, String date) {
        if (submission == null) {
            throw new IllegalArgumentException("submission must not be null");
        }
        submission.recordSubmission(marks, date);
    }
}