package academicengine.enrollment;

public class Enrollment {

    private String courseName;
    private double gpa;

    public Enrollment(String courseName, double gpa) {
        this.courseName = courseName;
        this.gpa = gpa;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getGpa() {
        return gpa;
    }
}