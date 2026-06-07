package com.crs.model;

import java.io.Serializable;

public class FailedCourse implements Serializable {
    private static final long serialVersionUID = 4L;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private String reason;

    public FailedCourse(String courseCode, String courseTitle, int creditHours, String reason) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.reason = reason;
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
