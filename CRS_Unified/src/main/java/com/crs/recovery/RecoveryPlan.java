package com.crs.recovery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a course recovery plan for a student
 */
public class RecoveryPlan implements Serializable {
    private final String planId;
    private final String studentId;
    private String courseCode;
    private String courseName;
    private RecoveryStatus status;
    private final List<Milestone> milestones;
    private Date startDate;
    private Date endDate;
    private final String createdBy; // Administrator ID
    private final Date createdDate;
    private String recommendations;
    
    public RecoveryPlan(String studentId, String courseCode, String courseName, String createdBy) {
        this.planId = generatePlanId();
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.status = RecoveryStatus.PLANNING;
        this.milestones = new ArrayList<>();
        this.createdBy = createdBy;
        this.createdDate = new Date();
        this.startDate = new Date();
    }
    
    private String generatePlanId() {
        return "RP" + System.currentTimeMillis();
    }
    
    public void addMilestone(Milestone milestone) {
        milestones.add(milestone);
    }
    
    public void removeMilestone(int index) {
        if (index >= 0 && index < milestones.size()) {
            milestones.remove(index);
        }
    }
    
    public void updateMilestone(int index, Milestone milestone) {
        if (index >= 0 && index < milestones.size()) {
            milestones.set(index, milestone);
        }
    }
    
    public int calculateProgress() {
        if (milestones.isEmpty()) return 0;
        int completed = 0;
        for (Milestone m : milestones) {
            if (m.isCompleted()) completed++;
        }
        return (completed * 100) / milestones.size();
    }
    
    public void updateStatus() {
        if (milestones.isEmpty()) {
            status = RecoveryStatus.PLANNING;
        } else if (calculateProgress() == 100) {
            status = RecoveryStatus.COMPLETED;
        } else {
            status = RecoveryStatus.IN_PROGRESS;
        }
    }
    
    // Getters and Setters
    public String getPlanId() {
        return planId;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public RecoveryStatus getStatus() {
        return status;
    }
    
    public void setStatus(RecoveryStatus status) {
        this.status = status;
    }
    
    public List<Milestone> getMilestones() {
        return milestones;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    public String getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
    
    @Override
    public String toString() {
        return courseCode + " - " + courseName + " [" + status + "] - Progress: " + calculateProgress() + "%";
    }
}