package org.example;

import org.example.model.Student;
import org.example.service.IStudentService;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    private IStudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();
    }

    @Test
    void addStudent_shouldIncreaseListSize() {
        studentService.addStudent(new Student(1, "John", "BSIT"));
        assertEquals(1, studentService.getAllStudents().size());
    }

    @Test
    void addStudent_duplicateId_shouldReject() {
        studentService.addStudent(new Student(1, "John", "BSIT"));
        studentService.addStudent(new Student(1, "Jane", "BSCS")); // duplicate
        assertEquals(1, studentService.getAllStudents().size()); // still 1
    }

    @Test
    void removeStudent_shouldDecreaseListSize() {
        studentService.addStudent(new Student(1, "John", "BSIT"));
        studentService.removeStudent(1);
        assertEquals(0, studentService.getAllStudents().size());
    }

    @Test
    void updateStudent_shouldChangeDetails() {
        studentService.addStudent(new Student(1, "John", "BSIT"));
        studentService.updateStudent(new Student(1, "John Updated", "BSCS"));
        assertEquals("John Updated", studentService.findById(1).getName());
    }

    @Test
    void findById_nonExistent_shouldReturnNull() {
        assertNull(studentService.findById(999));
    }
}