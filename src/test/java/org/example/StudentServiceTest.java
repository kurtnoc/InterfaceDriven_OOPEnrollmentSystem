
package org.example;

import org.example.model.Student;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    private StudentServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new StudentServiceImpl();
    }

    @Test
    void addStudent_shouldIncreaseListSize() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        assertEquals(1, service.getAllStudents().size());
    }

    @Test
    void addStudent_duplicateId_shouldRejectAndKeepSizeAt1() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.addStudent(new Student(1, "Bob", "BSCS")); // duplicate — must be rejected
        assertEquals(1, service.getAllStudents().size());
    }

    @Test
    void addStudent_duplicateId_originalDataShouldBePreserved() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.addStudent(new Student(1, "Impostor", "BSCS"));
        assertEquals("Alice", service.findById(1).getName());
    }

    @Test
    void updateStudent_shouldChangeNameAndProgram() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.updateStudent(new Student(1, "Alice Updated", "BSCS"));
        Student updated = service.findById(1);
        assertEquals("Alice Updated", updated.getName());
        assertEquals("BSCS", updated.getProgram());
    }

    @Test
    void updateStudent_nonExistentId_shouldNotCrash() {
        assertDoesNotThrow(() -> service.updateStudent(new Student(999, "Ghost", "BSIT")));
    }

    @Test
    void removeStudent_shouldDecreaseListSize() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.removeStudent(1);
        assertEquals(0, service.getAllStudents().size());
    }

    @Test
    void removeStudent_nonExistentId_listShouldBeUnchanged() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.removeStudent(999);
        assertEquals(1, service.getAllStudents().size());
    }

    @Test
    void findById_nonExistent_shouldReturnNull() {
        assertNull(service.findById(999));
    }

    @Test
    void markCoursePassed_shouldRecordPassedCourse() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.markCoursePassed(1, 101);
        assertTrue(service.hasPassedCourse(1, 101));
    }

    @Test
    void hasPassedCourse_notPassed_shouldReturnFalse() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        assertFalse(service.hasPassedCourse(1, 101));
    }

    @Test
    void hasPassedCourse_differentCourse_shouldReturnFalse() {
        service.addStudent(new Student(1, "Alice", "BSIT"));
        service.markCoursePassed(1, 101);
        assertFalse(service.hasPassedCourse(1, 202)); // passed 101, not 202
    }
}