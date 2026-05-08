package org.example.service.impl;
import org.example.model.Course;
import org.example.service.ICourseService;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceImpl implements ICourseService {
    private List<Course> courses = new ArrayList<>();

    @Override
    public void addCourse(Course c) {
        courses.add(c);
        System.out.println("Course added: " + c.getCourseName());
    }

    @Override
    public void updateCourse(Course c) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId() == c.getCourseId()) {
                courses.set(i, c);
                System.out.println("Course updated.");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    @Override
    public void removeCourse(int id) {
        courses.removeIf(c -> c.getCourseId() == id);
        System.out.println("Course removed.");
    }

    @Override
    public List<Course> getAllCourses() { return courses; }

    @Override
    public Course findById(int id) {
        return courses.stream().filter(c -> c.getCourseId() == id).findFirst().orElse(null);
    }
}