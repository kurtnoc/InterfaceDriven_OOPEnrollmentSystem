package org.example.service;
import org.example.model.Student;
import java.util.List;

public interface IStudentService {
    void addStudent(Student student);
    void updateStudent(Student student);
    void removeStudent(int studentId);
//Passed course feature
    void markCoursePassed(int studentId, int courseId);
    boolean hasPassedCourse(int studentId, int courseId);
    List<Student> getAllStudents();
    Student findById(int studentId);
}