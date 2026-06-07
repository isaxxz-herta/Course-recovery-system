package com.crs.academic.attempt;

public interface AttemptController {
    CourseAttempt startAttempt(int number);
    void advance(CourseAttempt attempt);
    void markPassed(CourseAttempt attempt);
    void markFailed(CourseAttempt attempt);
}