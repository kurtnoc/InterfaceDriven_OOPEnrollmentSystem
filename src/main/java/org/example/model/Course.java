package org.example.model;
public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private String program;
    private int units;

    public Course() {}
    public Course(int courseId, String courseCode, String courseName, String program, int units) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.program = program;
        this.units = units;
    }

    public int getCourseId() { return courseId; }
    public void setCourseId(int id) { this.courseId = id; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String c) { this.courseCode = c; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String n) { this.courseName = n; }
    public String getProgram() { return program; }
    public void setProgram(String p) { this.program = p; }
    public int getUnits() { return units; }
    public void setUnits(int u) { this.units = u; }

    @Override
    public String toString() {
        return String.format("Course{id=%d, code='%s', name='%s', program='%s', units=%d}",
                courseId, courseCode, courseName, program, units);
    }
}