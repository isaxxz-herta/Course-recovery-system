package recovery;

import com.crs.model.Student;
import java.util.List;

/**
 * Checks if a student is eligible to progress to next level
 * Updated to work with Person 2's Student class
 * Criteria:
 * 1. CGPA >= 2.0
 * 2. No more than 3 failed courses
 */
public class EligibilityChecker {
    private static final double MIN_CGPA = 2.0;
    private static final int MAX_FAILED_COURSES = 3;
    
    /**
     * Check if student meets CGPA requirement
     * @param cgpa
     * @return 
     */
    public boolean checkCGPAEligibility(double cgpa) {
        return cgpa >= MIN_CGPA;
    }
    
    /**
     * Check if student meets failed courses requirement
     * @param failedCount
     * @return 
     */
    public boolean checkFailedCoursesEligibility(int failedCount) {
        return failedCount <= MAX_FAILED_COURSES;
    }
    
    /**
     * Overall eligibility check using Student object
     * @param student
     * @return 
     */
    public boolean isEligibleForProgression(Student student) {
        return student.isEligibleForProgression();
    }
    
    /**
     * Overall eligibility check with manual values
     * @param cgpa
     * @param failedCount
     * @return 
     */
    public boolean isEligibleForProgression(double cgpa, int failedCount) {
        return checkCGPAEligibility(cgpa) && checkFailedCoursesEligibility(failedCount);
    }
    
    /**
     * Get detailed eligibility report using Student object
     * @param student
     * @return 
     */
    public String getEligibilityReport(Student student) {
        StringBuilder report = new StringBuilder();
        report.append("=== ELIGIBILITY REPORT ===\n");
        report.append("Student ID: ").append(student.getStudentId()).append("\n");
        report.append("Student Name: ").append(student.getFullName()).append("\n");
        report.append("Program: ").append(student.getProgram()).append("\n");
        report.append("Year of Study: ").append(student.getYearOfStudy()).append("\n");
        report.append("CGPA: ").append(String.format("%.2f", student.getCgpa())).append("\n");
        report.append("Failed Courses: ").append(student.getFailedCourses().size()).append("\n\n");
        
        boolean cgpaOk = checkCGPAEligibility(student.getCgpa());
        boolean failedOk = checkFailedCoursesEligibility(student.getFailedCourses().size());
        
        report.append("CGPA Requirement (>= 2.0): ")
              .append(cgpaOk ? "✓ PASSED" : "✗ FAILED").append("\n");
        report.append("Failed Courses Requirement (<= 3): ")
              .append(failedOk ? "✓ PASSED" : "✗ FAILED").append("\n\n");
        
        if (student.isEligibleForProgression()) {
            report.append("STATUS: ELIGIBLE FOR PROGRESSION\n");
        } else {
            report.append("STATUS: NOT ELIGIBLE - REQUIRES COURSE RECOVERY\n");
            report.append("\nREASONS FOR INELIGIBILITY:\n");
            report.append(getIneligibilityReasons(student.getCgpa(), student.getFailedCourses().size()));
        }
        
        // Show failed courses if any
        if (!student.getFailedCourses().isEmpty()) {
            report.append("\nFAILED COURSES LIST:\n");
            report.append("--------------------\n");
            student.getFailedCourses().forEach(fc -> {
                report.append(String.format("• %s - %s (%d credits) - %s\n",
                    fc.getCourseCode(),
                    fc.getCourseTitle(),
                    fc.getCreditHours(),
                    fc.getReason()));
            });
        }
        
        return report.toString();
    }
    
    /**
     * Get detailed eligibility report with manual values
     * @param studentId
     * @param studentName
     * @param cgpa
     * @param failedCount
     * @return 
     */
    public String getEligibilityReport(String studentId, String studentName, 
                                       double cgpa, int failedCount) {
        StringBuilder report = new StringBuilder();
        report.append("=== ELIGIBILITY REPORT ===\n");
        report.append("Student ID: ").append(studentId).append("\n");
        report.append("Student Name: ").append(studentName).append("\n");
        report.append("CGPA: ").append(String.format("%.2f", cgpa)).append("\n");
        report.append("Failed Courses: ").append(failedCount).append("\n\n");
        
        boolean cgpaOk = checkCGPAEligibility(cgpa);
        boolean failedOk = checkFailedCoursesEligibility(failedCount);
        
        report.append("CGPA Requirement (>= 2.0): ")
              .append(cgpaOk ? "✓ PASSED" : "✗ FAILED").append("\n");
        report.append("Failed Courses Requirement (<= 3): ")
              .append(failedOk ? "✓ PASSED" : "✗ FAILED").append("\n\n");
        
        if (isEligibleForProgression(cgpa, failedCount)) {
            report.append("STATUS: ELIGIBLE FOR PROGRESSION\n");
        } else {
            report.append("STATUS: NOT ELIGIBLE - REQUIRES COURSE RECOVERY\n");
        }
        
        return report.toString();
    }
    
    /**
     * Get list of reasons for ineligibility
     * @param cgpa
     * @param failedCount
     * @return 
     */
    public String getIneligibilityReasons(double cgpa, int failedCount) {
        StringBuilder reasons = new StringBuilder();
        
        if (!checkCGPAEligibility(cgpa)) {
            reasons.append("• CGPA below minimum requirement (")
                   .append(String.format("%.2f", cgpa))
                   .append(" < 2.0)\n");
        }
        
        if (!checkFailedCoursesEligibility(failedCount)) {
            reasons.append("• Too many failed courses (")
                   .append(failedCount)
                   .append(" > 3)\n");
        }
        
        return reasons.length() > 0 ? reasons.toString() : "Student is eligible";
    }
    
    /**
     * Check and update student eligibility status
     * @param student
     */
    public void checkAndUpdateEligibility(Student student) {
        student.checkEligibility();
    }
    
    /**
     * Get list of all ineligible students from a collection
     * @param students
     * @return 
     */
    public List<Student> getIneligibleStudents(List<Student> students) {
        return students.stream()
                      .filter(s -> !s.isEligibleForProgression())
                      .toList();
    }
}