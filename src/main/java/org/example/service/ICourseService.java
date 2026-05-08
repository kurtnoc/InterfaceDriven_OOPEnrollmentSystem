package org.example.service;
import org.example.model.Course;
import java.util.List;

public interface ICourseService {
    void addCourse(Course course);
    void updateCourse(Course course);
    void removeCourse(int courseId);
    List<Course> getAllCourses();
    Course findById(int courseId);
}