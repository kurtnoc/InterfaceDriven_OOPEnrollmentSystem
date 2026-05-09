
package org.example;

import org.example.model.*;
import org.example.service.impl.EnrollmentServiceImpl;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentServiceTest {
    private StudentServiceImpl studentService;
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();
        enrollmentService = new EnrollmentServiceImpl(studentService);

        // One department, one section with capacity 2
        enrollmentService.addDepartment(new Department(1, "College of Computer Studies"));
        enrollmentService.addSection(1, new Section(101, "BSIT-1A", 2));

        studentService.addStudent(new Student(1, "Alice", "BSIT"));
        studentService.addStudent(new Student(2, "Bob", "BSIT"));
        studentService.addStudent(new Student(3, "Charlie", "BSIT"));
    }

    @Test
    void enrollStudent_shouldSucceedWhenSectionHasSpace() {
        enrollmentService.enrollStudentInSection(1, 101);
        assertEquals(1, enrollmentService.findSectionById(101).getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_shouldRejectWhenSectionIsFull() {
        enrollmentService.enrollStudentInSection(1, 101);
        enrollmentService.enrollStudentInSection(2, 101); // section now full
        enrollmentService.enrollStudentInSection(3, 101); // must be rejected
        assertEquals(2, enrollmentService.findSectionById(101).getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_shouldRejectDuplicateEnrollment() {
        enrollmentService.enrollStudentInSection(1, 101);
        enrollmentService.enrollStudentInSection(1, 101); // same student again
        assertEquals(1, enrollmentService.findSectionById(101).getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_nonExistentStudent_shouldNotCrash() {
        assertDoesNotThrow(() -> enrollmentService.enrollStudentInSection(999, 101));
        assertEquals(0, enrollmentService.findSectionById(101).getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_nonExistentSection_shouldNotCrash() {
        assertDoesNotThrow(() -> enrollmentService.enrollStudentInSection(1, 999));
    }

    @Test
    void enrollStudent_shouldRejectWhenPrerequisiteNotMet() {
        // Section has a course with prerequisite course ID = 50
        Section sec = enrollmentService.findSectionById(101);
        sec.getCourses().add(new Course(10, "ADV", "Advanced Course", "BSIT", 3, 50));

        // Student 1 has NOT passed course 50
        enrollmentService.enrollStudentInSection(1, 101);
        assertEquals(0, sec.getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_shouldSucceedWhenPrerequisiteMet() {
        Section sec = enrollmentService.findSectionById(101);
        sec.getCourses().add(new Course(10, "ADV", "Advanced Course", "BSIT", 3, 50));

        // Mark student 1 as having passed course 50
        studentService.markCoursePassed(1, 50);
        enrollmentService.enrollStudentInSection(1, 101);
        assertEquals(1, sec.getEnrolledStudents().size());
    }

    @Test
    void findDepartmentById_nonExistent_shouldReturnNull() {
        assertNull(enrollmentService.findDepartmentById(999));
    }

    @Test
    void findSectionById_nonExistent_shouldReturnNull() {
        assertNull(enrollmentService.findSectionById(999));
    }
}
