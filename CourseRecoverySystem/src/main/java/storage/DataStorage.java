package storage;

import recovery.RecoveryPlan;
import com.crs.model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file-based storage for recovery plans and integration with Person 2's Student data
 */
public class DataStorage {
    private static final String RECOVERY_FILE = "recovery_plans.dat";
    private static final String STUDENTS_FILE = "students.dat"; // Person 2's file
    
    // Save recovery plans
    public void saveRecoveryPlans(List<RecoveryPlan> plans) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(RECOVERY_FILE))) {
            oos.writeObject(plans);
            System.out.println("Recovery plans saved successfully: " + plans.size() + " plans");
        } catch (IOException e) {
            System.err.println("Error saving recovery plans: " + e.getMessage());
        }
    }
    
    // Load recovery plans
    @SuppressWarnings("unchecked")
    public List<RecoveryPlan> loadRecoveryPlans() {
        File file = new File(RECOVERY_FILE);
        if (!file.exists()) {
            System.out.println("No existing recovery plans file. Starting fresh.");
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            List<RecoveryPlan> plans = (List<RecoveryPlan>) ois.readObject();
            System.out.println("Recovery plans loaded successfully: " + plans.size() + " plans");
            return plans;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading recovery plans: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Load students from Person 2's data file
     * This should connect to Person 2's UserManager or Student storage
     * @return 
     */
    @SuppressWarnings("unchecked")
    public List<Student> loadStudentsFromPerson2() {
        File file = new File(STUDENTS_FILE);
        
        // If Person 2's file doesn't exist, create sample data
        if (!file.exists()) {
            System.out.println("Person 2's student file not found. Creating sample data...");
            return createSampleStudentsForTesting();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            // Try to load from Person 2's format
            Object obj = ois.readObject();
            
            if (obj instanceof List<?> list) {
                if (!list.isEmpty() && list.get(0) instanceof Student) {
                    System.out.println("Students loaded from Person 2's file: " + list.size() + " students");
                    return (List<Student>) list;
                }
            }
            
            System.out.println("Unknown format in student file. Using sample data.");
            return createSampleStudentsForTesting();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading students: " + e.getMessage());
            return createSampleStudentsForTesting();
        }
    }
    
    /**
     * Create sample students for testing (compatible with Person 2's Student class)
     */
    private List<Student> createSampleStudentsForTesting() {
        List<Student> students = new ArrayList<>();
        
        // Student 1: Not eligible (Low CGPA + Too many failed courses)
        Student s1 = new Student(
            "U001", "alex.tan", "password123", "alex.tan@student.apu.edu",
            "Alex Tan Wei Jie", "TP001234", "Bachelor of Computer Science"
        );
        s1.setYearOfStudy(2);
        s1.setCurrentSemester(1);
        
        // Add course grades for s1
        s1.addCourseGrade("CS101", "Programming Fundamentals", 3, "B", 3.0);
        s1.addCourseGrade("CS102", "Data Structures", 3, "C+", 2.3);
        s1.addCourseGrade("MA101", "Mathematics", 4, "C", 2.0);
        
        // Add failed courses for s1
        s1.addFailedCourse("CT038", "Object Oriented Development with Java", 3, "Failed Assignment 2");
        s1.addFailedCourse("CS201", "Database Systems", 3, "Failed Final Exam");
        s1.addFailedCourse("CS205", "Software Engineering", 3, "Low attendance");
        s1.addFailedCourse("MA202", "Discrete Mathematics", 4, "Failed both assignments");
        
        students.add(s1);
        
        // Student 2: Not eligible (Low CGPA but acceptable failed courses)
        Student s2 = new Student(
            "U002", "sarah.wong", "password123", "sarah.wong@student.apu.edu",
            "Sarah Wong Li Ying", "TP002345", "Bachelor of Information Technology"
        );
        s2.setYearOfStudy(2);
        s2.setCurrentSemester(2);
        
        s2.addCourseGrade("IT101", "IT Fundamentals", 3, "C+", 2.3);
        s2.addCourseGrade("IT102", "Networking", 3, "C", 2.0);
        s2.addCourseGrade("CS101", "Programming", 3, "B-", 2.7);
        
        s2.addFailedCourse("CS201", "Advanced Programming", 3, "Failed midterm");
        s2.addFailedCourse("IT201", "System Analysis", 3, "Incomplete project");
        
        students.add(s2);
        
        // Student 3: Not eligible (Good CGPA but too many failed courses)
        Student s3 = new Student(
            "U003", "kumar.raj", "password123", "kumar.raj@student.apu.edu",
            "Kumar Raj A/L Suresh", "TP003456", "Bachelor of Software Engineering"
        );
        s3.setYearOfStudy(3);
        s3.setCurrentSemester(1);
        
        s3.addCourseGrade("SE101", "Software Engineering I", 3, "A-", 3.7);
        s3.addCourseGrade("SE102", "Requirements Engineering", 3, "A", 4.0);
        s3.addCourseGrade("CS201", "Algorithms", 4, "B+", 3.3);
        s3.addCourseGrade("CS202", "Operating Systems", 3, "A-", 3.7);
        
        s3.addFailedCourse("MA301", "Advanced Mathematics", 4, "Failed final");
        s3.addFailedCourse("CS301", "Artificial Intelligence", 3, "Low assignment scores");
        s3.addFailedCourse("CS302", "Machine Learning", 3, "Failed project");
        s3.addFailedCourse("SE301", "Software Testing", 3, "Incomplete coursework");
        
        students.add(s3);
        
        // Student 4: Eligible (Good CGPA and acceptable failed courses)
        Student s4 = new Student(
            "U004", "emily.chen", "password123", "emily.chen@student.apu.edu",
            "Emily Chen Xiao Ling", "TP004567", "Bachelor of Cybersecurity"
        );
        s4.setYearOfStudy(2);
        s4.setCurrentSemester(2);
        
        s4.addCourseGrade("CS101", "Programming", 3, "A", 4.0);
        s4.addCourseGrade("CS102", "Data Structures", 3, "A-", 3.7);
        s4.addCourseGrade("CY101", "Network Security", 3, "A", 4.0);
        s4.addCourseGrade("CY102", "Cryptography", 4, "B+", 3.3);
        s4.addCourseGrade("MA101", "Mathematics", 3, "A-", 3.7);
        
        s4.addFailedCourse("CY201", "Ethical Hacking", 3, "Failed practical exam");
        
        students.add(s4);
        
        // Student 5: Eligible (Excellent student)
        Student s5 = new Student(
            "U005", "david.lim", "password123", "david.lim@student.apu.edu",
            "David Lim Choon Huat", "TP005678", "Bachelor of Computer Science"
        );
        s5.setYearOfStudy(3);
        s5.setCurrentSemester(1);
        
        s5.addCourseGrade("CS101", "Programming", 3, "A", 4.0);
        s5.addCourseGrade("CS102", "Data Structures", 3, "A", 4.0);
        s5.addCourseGrade("CS201", "Algorithms", 4, "A", 4.0);
        s5.addCourseGrade("CS202", "Database", 3, "A-", 3.7);
        s5.addCourseGrade("CS301", "Software Engineering", 3, "A", 4.0);
        
        students.add(s5);
        
        System.out.println("Created " + students.size() + " sample students for testing");
        
        // Print eligibility status
        for (Student s : students) {
            System.out.println(s.getStudentId() + " - " + s.getFullName() + 
                             " | CGPA: " + String.format("%.2f", s.getCgpa()) + 
                             " | Failed: " + s.getFailedCourses().size() +
                             " | Eligible: " + s.isEligibleForProgression());
        }
        
        return students;
    }
    
    /**
     * Save students (for Person 2 integration)
     * @param students
     */
    public void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(STUDENTS_FILE))) {
            oos.writeObject(students);
            System.out.println("Students saved successfully: " + students.size() + " students");
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }
}