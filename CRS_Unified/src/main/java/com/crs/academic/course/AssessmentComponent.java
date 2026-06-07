package com.crs.academic.course;

public class AssessmentComponent {

    private String componentName;
    private double weightage; // percentage contribution to final grade
    private int maxMarks;

    public AssessmentComponent(String componentName, double weightage, int maxMarks) {
        
        if (componentName == null || componentName.isBlank()) {
            throw new IllegalArgumentException("componentName must not be blank");
        }
        if (weightage < 0 || weightage > 100) {
            throw new IllegalArgumentException("weightage must be between 0 and 100");
        }
        if (maxMarks <= 0) {
            throw new IllegalArgumentException("maxMarks must be >= 1");
        }
 
        this.componentName = componentName;
        this.weightage = weightage;
        this.maxMarks = maxMarks;
    }

    public String getComponentName() {
        return componentName;
    }

    public double getWeightage() {
        return weightage;
    }

    public void setWeightage(double weightage) {
        if (weightage < 0 || weightage > 100) {
            throw new IllegalArgumentException("weightage must be between 0 and 100");
        }
        this.weightage = weightage;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        if (maxMarks <= 0) {
            throw new IllegalArgumentException("maxMarks must be >= 1");
        }
        this.maxMarks = maxMarks;
    }
}