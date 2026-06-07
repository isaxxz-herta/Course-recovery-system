package com.crs.recovery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crs.model.Student;

/**
 * Manages recovery plans for students who are not eligible for progression
 */
public class RecoveryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<RecoveryPlan> recoveryPlans;
    private EligibilityChecker eligibilityChecker;
    
    public RecoveryManager() {
        this.recoveryPlans = new ArrayList<>();
        this.eligibilityChecker = new EligibilityChecker();
    }
    
    /**
     * Create a new recovery plan for a student
     */
    public RecoveryPlan createRecoveryPlan(String studentId, String courseCode, 
                                          String courseName, String createdBy) {
        RecoveryPlan plan = new RecoveryPlan(studentId, courseCode, courseName, createdBy);
        recoveryPlans.add(plan);
        return plan;
    }
    
    /**
     * Get all recovery plans for a specific student
     */
    public List<RecoveryPlan> getRecoveryPlansForStudent(String studentId) {
        return recoveryPlans.stream()
                           .filter(plan -> plan.getStudentId().equals(studentId))
                           .collect(Collectors.toList());
    }
    
    /**
     * Get all recovery plans
     */
    public List<RecoveryPlan> getAllRecoveryPlans() {
        return new ArrayList<>(recoveryPlans);
    }
    
    /**
     * Get recovery plans by status
     */
    public List<RecoveryPlan> getRecoveryPlansByStatus(RecoveryStatus status) {
        return recoveryPlans.stream()
                           .filter(plan -> plan.getStatus() == status)
                           .collect(Collectors.toList());
    }
    
    /**
     * Update a recovery plan
     */
    public boolean updateRecoveryPlan(String planId, RecoveryPlan updatedPlan) {
        for (int i = 0; i < recoveryPlans.size(); i++) {
            if (recoveryPlans.get(i).getPlanId().equals(planId)) {
                recoveryPlans.set(i, updatedPlan);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete a recovery plan
     */
    public boolean deleteRecoveryPlan(String planId) {
        return recoveryPlans.removeIf(plan -> plan.getPlanId().equals(planId));
    }
    
    /**
     * Find recovery plan by ID
     */
    public RecoveryPlan findRecoveryPlan(String planId) {
        return recoveryPlans.stream()
                           .filter(plan -> plan.getPlanId().equals(planId))
                           .findFirst()
                           .orElse(null);
    }
    
    /**
     * Create default recovery plan for a failed course
     */
    public RecoveryPlan createDefaultRecoveryPlan(Student student, String courseCode, 
                                                 String courseName, String createdBy) {
        RecoveryPlan plan = createRecoveryPlan(student.getStudentId(), courseCode, courseName, createdBy);
        
        // Add default milestones
        plan.addMilestone(new Milestone("Review course materials", 1, 2, 
                                       "Review all lecture notes and textbook chapters"));
        plan.addMilestone(new Milestone("Complete practice exercises", 3, 4, 
                                       "Complete all tutorial exercises and assignments"));
        plan.addMilestone(new Milestone("Attend consultation sessions", 5, 6, 
                                       "Meet with instructor for clarification on difficult topics"));
        plan.addMilestone(new Milestone("Take practice tests", 7, 8, 
                                       "Complete practice exams and assess understanding"));
        plan.addMilestone(new Milestone("Final preparation", 9, 10, 
                                       "Final review and preparation for retake exam"));
        
        // Set recommendations
        plan.setRecommendations(
            "1. Focus on understanding fundamental concepts\n" +
            "2. Practice regularly with exercises and past papers\n" +
            "3. Seek help from instructors when needed\n" +
            "4. Form study groups with classmates\n" +
            "5. Maintain consistent study schedule"
        );
        
        return plan;
    }
    
    /**
     * Generate recovery plans for all ineligible students
     */
    public List<RecoveryPlan> generateRecoveryPlansForIneligibleStudents(List<Student> students, String createdBy) {
        List<RecoveryPlan> newPlans = new ArrayList<>();
        
        for (Student student : students) {
            if (!student.isEligibleForProgression()) {
                // Create recovery plans for each failed course
                student.getFailedCourses().forEach(failedCourse -> {
                    // Check if plan already exists for this student and course
                    boolean planExists = recoveryPlans.stream()
                                                     .anyMatch(plan -> 
                                                         plan.getStudentId().equals(student.getStudentId()) &&
                                                         plan.getCourseCode().equals(failedCourse.getCourseCode()));
                    
                    if (!planExists) {
                        RecoveryPlan plan = createDefaultRecoveryPlan(student, 
                                                                     failedCourse.getCourseCode(),
                                                                     failedCourse.getCourseTitle(),
                                                                     createdBy);
                        newPlans.add(plan);
                    }
                });
            }
        }
        
        return newPlans;
    }
    
    /**
     * Get recovery statistics
     */
    public String getRecoveryStatistics() {
        int totalPlans = recoveryPlans.size();
        int planningPlans = (int) recoveryPlans.stream().filter(p -> p.getStatus() == RecoveryStatus.PLANNING).count();
        int inProgressPlans = (int) recoveryPlans.stream().filter(p -> p.getStatus() == RecoveryStatus.IN_PROGRESS).count();
        int completedPlans = (int) recoveryPlans.stream().filter(p -> p.getStatus() == RecoveryStatus.COMPLETED).count();
        
        StringBuilder stats = new StringBuilder();
        stats.append("=== RECOVERY PLAN STATISTICS ===\n");
        stats.append("Total Recovery Plans: ").append(totalPlans).append("\n");
        stats.append("Planning Phase: ").append(planningPlans).append("\n");
        stats.append("In Progress: ").append(inProgressPlans).append("\n");
        stats.append("Completed: ").append(completedPlans).append("\n");
        
        if (totalPlans > 0) {
            double completionRate = (double) completedPlans / totalPlans * 100;
            stats.append("Completion Rate: ").append(String.format("%.1f%%", completionRate)).append("\n");
        }
        
        return stats.toString();
    }
    
    /**
     * Get eligibility checker
     */
    public EligibilityChecker getEligibilityChecker() {
        return eligibilityChecker;
    }
}