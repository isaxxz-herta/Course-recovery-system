package academicengine.course;

public interface CourseCatalog {
    void addAssessmentComponent(Course course, AssessmentComponent component);
    void addPrerequisite(Course course, String courseCode);
}
