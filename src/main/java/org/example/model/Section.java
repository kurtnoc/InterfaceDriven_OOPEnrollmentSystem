package org.example.model;
import java.util.ArrayList;
import java.util.List;

public class Section {
    private int sectionId;
    private String sectionName;
    private int maxCapacity;
    private Instructor instructor;
    private List<Student> enrolledStudents = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public Section() {}
    public Section(int sectionId, String sectionName, int maxCapacity) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.maxCapacity = maxCapacity;
    }

    public int getSectionId() { return sectionId; }
    public void setSectionId(int id) { this.sectionId = id; }
    public String getSectionName() { return sectionName; }
    public void setSectionName(String n) { this.sectionName = n; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int c) { this.maxCapacity = c; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor i) { this.instructor = i; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public List<Course> getCourses() { return courses; }

    public boolean isFull() { return enrolledStudents.size() >= maxCapacity; }

    @Override
    public String toString() {
        return String.format("Section{id=%d, name='%s', capacity=%d/%d}",
                sectionId, sectionName, enrolledStudents.size(), maxCapacity);
    }
}