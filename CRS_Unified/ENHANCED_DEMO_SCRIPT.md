# Course Registration System - Course, Enrollment & Grading Demo
## 5-Minute Focused Demonstration with OOP Concepts

---

## **PART 1: Course Structure & Object Design (0:00 - 1:30)**

### **Your Script:**
"Thank you. Now I'll demonstrate our course, enrollment, and grading systems, highlighting the object-oriented programming principles that make them robust and maintainable.

Let me start by showing you how courses are structured in our system. Behind the scenes, each course is represented as a well-designed Java object with clear encapsulation principles."

### **Presenter Actions:**
**[SCREEN 1]** - Stay in current view, point to course data visible in student reports
**[SCREEN 2]** - Open `Course.java`, highlight the class structure

### **Your Script Continues:**
"Here's our Course class implementation. Notice how we use private fields to protect data integrity - the course code, name, and description are encapsulated with proper validation in our constructor. This ensures every course object maintains consistent state throughout the system."

### **Code to Show:**
```java
public class Course {
    private String courseCode;
    private String courseName; 
    private String description;
    private List<AssessmentComponent> assessmentComponents;
    
    public Course(String courseCode, String courseName, String description) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("courseCode must not be blank");
        }
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.assessmentComponents = new ArrayList<>();
    }
}
```

### **Your Script Continues:**
"The beauty of this design is the composition pattern - each course contains a collection of AssessmentComponent objects, allowing us to dynamically build complex grading structures. For example, CS101 has Assignment 1 at 30% weight and Final Exam at 70% weight, all managed through clean method interfaces."

---

## **PART 2: Student Data & Enrollment Relationships (1:30 - 2:45)**

### **Your Script:**
"Moving to our enrollment system, let me show you how object-oriented design creates powerful relationships between our core entities. As you can see here, we have students like John Smith and Jane Doe with their enrollment data."

### **Presenter Actions:**
**[SCREEN 1]** - Navigate to "Student Overview" tab, show student list and click on students
**[SCREEN 2]** - Open `Enrollment.java` class

### **Your Script Continues:**
"Our enrollment system uses composition beautifully - each Enrollment object contains a Student reference and a Course reference, creating the many-to-many relationship between students and courses. The enrollment status is managed through a clean enum pattern for type safety."

### **Code to Show:**
```java
public class Enrollment implements EnrollmentManager {
    private String studentId;
    private Course course;
    private EnrollmentStatus status;
    
    public Enrollment(String studentId, Course course) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("studentId must not be blank");
        }
        this.studentId = studentId;
        this.course = course;
        this.status = EnrollmentStatus.ACTIVE;
    }
    
    public void registerStudent() {
        this.status = EnrollmentStatus.ACTIVE;
        System.out.println("Student " + studentId + " registered for " + course.getCourseName());
    }
}
```

### **Presenter Actions:**
**[SCREEN 1]** - Point to enrollment status in the interface
**[SCREEN 2]** - Show `EnrollmentStatus.java` enum

### **Your Script Continues:**
"The enrollment process is streamlined through object methods. We create an enrollment linking a student to a course, register the student, and track status through our enum: ACTIVE for current enrollments, COMPLETED for successful completion, and FAILED for unsuccessful attempts. This provides clear state management and type safety."

---

## **PART 3: Grading System & Automatic Calculations (2:45 - 4:15)**

### **Your Script:**
"Now for our grading system - this is where object-oriented design really demonstrates its power through encapsulation and automatic behavior. Let me show you John Smith's academic performance."

### **Presenter Actions:**
**[SCREEN 1]** - Click on John Smith to show his grades and CGPA (3.39)
**[SCREEN 2]** - Open `Student.java`, focus on grade methods

### **Your Script Continues:**
"The Student class demonstrates excellent encapsulation - all grade data is private, but we provide controlled access through public methods. When we add a course grade, notice how the method automatically triggers CGPA recalculation, maintaining data consistency without external intervention."

### **Code to Show:**
```java
public void addCourseGrade(String courseCode, String courseTitle, 
                          int creditHours, String grade, double gradePoint) {
    CourseGrade courseGrade = new CourseGrade(courseCode, courseTitle, 
                                            creditHours, grade, gradePoint);
    courseGrades.add(courseGrade);
    calculateCGPA();  // Automatic recalculation
}

public void calculateCGPA() {
    double totalGradePoints = 0.0;
    int totalCredits = 0;
    
    for (CourseGrade grade : courseGrades) {
        totalGradePoints += grade.getGradePoint() * grade.getCreditHours();
        totalCredits += grade.getCreditHours();
    }
    
    this.cgpa = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    checkEligibility();  // Cascading business logic
}
```

### **Your Script Continues:**
"This is a perfect example of the Single Responsibility Principle - the calculateCGPA method has one job, does it well, and automatically triggers eligibility checking. The method uses enhanced for-loops and demonstrates how object-oriented design makes complex calculations both readable and maintainable."

### **Presenter Actions:**
**[SCREEN 1]** - Point to John's CGPA of 3.39 and eligibility status
**[SCREEN 2]** - Show `CourseGrade.java` class structure

### **Your Script Continues:**
"Our grade management showcases object composition. The Student contains CourseGrade objects, each encapsulating course code, title, credit hours, grade, and grade points. This loose coupling means we can easily extend the system with new grade types without changing the core Student class."

---

## **PART 4: Eligibility System & Business Logic (4:15 - 5:00)**

### **Your Script:**
"Finally, let me demonstrate our eligibility system using Jane Doe, a student with academic challenges. This shows how good object-oriented design handles complex business rules elegantly."

### **Presenter Actions:**
**[SCREEN 1]** - Click on Jane Doe to show her lower grades and failed courses
**[SCREEN 2]** - Show `checkEligibility` method in Student.java

### **Your Script Continues:**
"The eligibility checking method is a beautiful example of clean code principles. It evaluates multiple criteria - CGPA threshold and failed course limits - using clear boolean logic. The method is self-documenting and provides detailed output for transparency."

### **Code to Show:**
```java
public void checkEligibility() {
    boolean cgpaCriteria = this.cgpa >= 2.0;
    boolean failedCoursesCriteria = failedCourses.size() <= 3;
    
    this.eligibleForProgression = cgpaCriteria && failedCoursesCriteria;
    
    System.out.println("Eligibility Check for " + getFullName() + ":");
    System.out.println("  CGPA: " + cgpa + " (Required: >= 2.0) - " + 
                      (cgpaCriteria ? "PASS" : "FAIL"));
    System.out.println("  Failed Courses: " + failedCourses.size() + 
                      " (Max: 3) - " + (failedCoursesCriteria ? "PASS" : "FAIL"));
}
```

### **Your Script Conclusion:**
"What makes this system powerful is how object-oriented principles create maintainable, extensible architecture. We use encapsulation for data protection, composition for complex relationships, and method chaining for automatic behavior. Every grade entry triggers automatic CGPA recalculation and eligibility assessment, demonstrating how good OOP design creates reliable, real-time academic management.

This concludes my demonstration of our course, enrollment, and grading systems - built on solid object-oriented foundations that ensure both functionality and maintainability."

---

## **Key OOP Concepts Highlighted:**

1. **Encapsulation**: Private fields with controlled public access in Course and Student classes
2. **Composition**: Course contains AssessmentComponents, Student contains CourseGrades
3. **Method Chaining**: addCourseGrade() → calculateCGPA() → checkEligibility()
4. **Design Patterns**: Enum for type safety, Exception handling for validation
5. **Single Responsibility**: Each method has one clear purpose
6. **Object Relationships**: Enrollment links Students and Courses

---

## **Timing Breakdown:**
- **0:00-1:30**: Course Structure + Encapsulation/Composition (90 seconds)
- **1:30-2:45**: Enrollment System + Object Relationships (75 seconds)  
- **2:45-4:15**: Grading System + Method Chaining (90 seconds)
- **4:15-5:00**: Eligibility System + Business Logic (45 seconds)

**Total: 5 minutes exactly**

---

## **Files to Have Ready:**
1. `Course.java` - Encapsulation and composition
2. `Enrollment.java` - Object relationships  
3. `EnrollmentStatus.java` - Enum pattern
4. `Student.java` - Grade management and CGPA calculation
5. `CourseGrade.java` - Data objects
6. `FailedCourse.java` - Failed course tracking

---

## **Presenter Requirements:**
- **Login State**: Should be logged in as Academic Officer (officer/officer123)
- **Navigation**: Use "Student Overview" tab to show student data and enrollments
- **Focus**: Course structure, enrollment relationships, grading calculations only
- **Avoid**: User management, authentication, admin-only features
- **Smooth Flow**: Work within the Officer interface without role switching