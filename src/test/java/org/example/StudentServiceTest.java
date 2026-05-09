package org.example;

import org.example.model.Student;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    // Use the concrete class to access implementation-specific methods
    private StudentServiceImpl studentService;

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
        studentService.addStudent(new Student(1, "Jane", "BSCS")); // duplicate ID

        // This confirms your fix in StudentServiceImpl works!
        assertEquals(1, studentService.getAllStudents().size());
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

        Student updated = studentService.findById(1);
        assertNotNull(updated);
        assertEquals("John Updated", updated.getName());
        assertEquals("BSCS", updated.getProgram());
    }

    @Test
    void findById_nonExistent_shouldReturnNull() {
        assertNull(studentService.findById(999));
    }

    // --- NEW TESTS FOR PREREQUISITE FEATURE ---

    @Test
    void hasPassedCourse_shouldReturnFalseByDefault() {
        // Test that a new student hasn't passed anything yet
        assertFalse(studentService.hasPassedCourse(1, 101));
    }

    @Test
    void markCoursePassed_shouldUpdateStatus() {
        int studentId = 1;
        int courseId = 101;

        studentService.markCoursePassed(studentId, courseId);

        assertTrue(studentService.hasPassedCourse(studentId, courseId),
                "Student should have passed the course after being marked.");
    }

    @Test
    void hasPassedCourse_shouldBeSpecificToStudent() {
        studentService.markCoursePassed(1, 101); // Student 1 passed 101

        // Student 2 should still be false
        assertFalse(studentService.hasPassedCourse(2, 101),
                "Marking one student passed should not affect others.");
    }
}