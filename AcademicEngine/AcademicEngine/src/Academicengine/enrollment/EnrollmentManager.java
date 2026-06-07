package academicengine.enrollment;

import academicengine.course.Course;

public interface EnrollmentManager {
    Enrollment enrollStudent(String studentId, Course course);
    void completeEnrollment(Enrollment enrollment);
    void failEnrollment(Enrollment enrollment);
}
