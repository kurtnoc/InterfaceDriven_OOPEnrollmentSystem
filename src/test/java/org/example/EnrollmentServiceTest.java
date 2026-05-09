package org.example;

import org.example.model.*;
import org.example.service.IStudentService;
import org.example.service.impl.EnrollmentServiceImpl;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentServiceTest {
    private EnrollmentServiceImpl enrollmentService;
    private IStudentService studentService;
    private StudentServiceImpl studentServiceImplementation;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();
        enrollmentService = new EnrollmentServiceImpl(studentService);

        // Seed a department and section with capacity 2
        Department dept = new Department(1, "College of Computer Studies");
        enrollmentService.addDepartment(dept);
        enrollmentService.addSection(1, new Section(101, "BSIT-1A", 2));

        studentService.addStudent(new Student(1, "Alice", "BSIT"));
        studentService.addStudent(new Student(2, "Bob", "BSIT"));
        studentService.addStudent(new Student(3, "Charlie", "BSIT"));
    }

    @Test
    void enrollStudent_shouldSucceed_whenSectionHasSpace() {
        enrollmentService.enrollStudentInSection(1, 101);
        Section sec = enrollmentService.findSectionById(101);
        assertEquals(1, sec.getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_shouldFail_whenSectionIsFull() {
        enrollmentService.enrollStudentInSection(1, 101);
        enrollmentService.enrollStudentInSection(2, 101);
        enrollmentService.enrollStudentInSection(3, 101); // should be rejected
        Section sec = enrollmentService.findSectionById(101);
        assertEquals(2, sec.getEnrolledStudents().size()); // still 2, not 3
    }

    @Test
    void enrollStudent_shouldFail_whenAlreadyEnrolled() {
        enrollmentService.enrollStudentInSection(1, 101);
        enrollmentService.enrollStudentInSection(1, 101); // duplicate
        Section sec = enrollmentService.findSectionById(101);
        assertEquals(1, sec.getEnrolledStudents().size());
    }

    @Test
    void enrollStudent_shouldFail_whenStudentNotFound() {
        enrollmentService.enrollStudentInSection(999, 101); // non-existent student
        Section sec = enrollmentService.findSectionById(101);
        assertEquals(0, sec.getEnrolledStudents().size());
    }
}

