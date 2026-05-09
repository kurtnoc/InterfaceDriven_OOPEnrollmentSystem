
package org.example.service.impl;
import org.example.model.Student;
import org.example.service.IStudentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServiceImpl implements IStudentService {
    private List<Student> students = new ArrayList<>();

    @Override
    public void addStudent(Student s) {
        boolean exists = students.stream().anyMatch(x -> x.getId() == s.getId());
        if (exists) {
            System.out.println("ERROR: Student ID " + s.getId() + " already exists. Rejected.");
            return;
        }
        students.add(s);
        System.out.println("Student added: " + s.getName());
    }

    private Map<Integer, List<Integer>> passedCourses = new HashMap<>();

    public void markCoursePassed(int studentId, int courseId) {
        passedCourses.computeIfAbsent(studentId, k -> new ArrayList<>()).add(courseId);
        System.out.printf("Student %d marked as passed for course %d.%n", studentId, courseId);
    }

    public boolean hasPassedCourse(int studentId, int courseId) {
        return passedCourses.getOrDefault(studentId, List.of()).contains(courseId);
    }

    @Override
    public void updateStudent(Student s) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == s.getId()) {
                students.set(i, s);
                System.out.println("Student updated.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    @Override
    public void removeStudent(int id) {
        students.removeIf(s -> s.getId() == id);
        System.out.println("Student removed.");
    }

    @Override
    public List<Student> getAllStudents() { return students; }

    @Override
    public Student findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }
}