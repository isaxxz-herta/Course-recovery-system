package com.crs.academic.course;

import java.util.List;
import java.util.ArrayList;

public class Course {
    private String courseCode;
    private String courseName;
    private String description;
    private List<AssessmentComponent> assessmentComponents;
    private List<String> prerequisites;

    public Course(String courseCode, String courseName, String description) {
        
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("courseCode must not be blank");
        }
        if (courseName == null || courseName.isBlank()) {
            throw new IllegalArgumentException("courseName must not be blank");
        }

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.assessmentComponents = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AssessmentComponent> getAssessmentComponents() {
        return assessmentComponents;
    }

    public void setAssessmentComponents(List<AssessmentComponent> assessmentComponents) {
        if (assessmentComponents == null) {
            throw new IllegalArgumentException("assessmentComponents must not be null");
        }
        this.assessmentComponents = assessmentComponents;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        if (prerequisites == null) throw new IllegalArgumentException("prerequisites must not be null");
        this.prerequisites = prerequisites;
    }
    
    public void addAssessmentComponent(AssessmentComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("component must not be null");
        }
        this.assessmentComponents.add(component);
    }

    public void addPrerequisite(String courseCode) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("prerequisite courseCode must not be blank");
        }
        this.prerequisites.add(courseCode);
    }
}