package com.crs.recovery;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a milestone in a recovery plan
 */
public class Milestone implements Serializable {
    private String description;
    private int weekStart;
    private int weekEnd;
    private String task;
    private boolean completed;
    private Date completionDate;
    
    public Milestone(String description, int weekStart, int weekEnd, String task) {
        this.description = description;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.task = task;
        this.completed = false;
    }
    
    // Getters and Setters
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getWeekStart() {
        return weekStart;
    }
    
    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }
    
    public int getWeekEnd() {
        return weekEnd;
    }
    
    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }
    
    public String getTask() {
        return task;
    }
    
    public void setTask(String task) {
        this.task = task;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (completed) {
            this.completionDate = new Date();
        }
    }
    
    public Date getCompletionDate() {
        return completionDate;
    }
    
    @Override
    public String toString() {
        String weeks = weekStart == weekEnd ? "Week " + weekStart : "Week " + weekStart + "-" + weekEnd;
        return weeks + ": " + task + (completed ? " [COMPLETED]" : " [PENDING]");
    }
}