package org.example;

import org.example.model.*;
import org.example.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TuitionServiceTest {
    private TuitionServiceImpl tuitionService;
    private StudentServiceImpl studentService;
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl();
        enrollmentService = new EnrollmentServiceImpl(studentService);
        tuitionService = new TuitionServiceImpl(studentService, enrollmentService);

        studentService.addStudent(new Student(1, "Alice", "BSIT"));
    }

    @Test
    void calculateFee_shouldReturnPositiveAmount() {
        double fee = tuitionService.calculateFee(1, 500.0);
        assertTrue(fee > 0);
    }

    @Test
    void makePayment_shouldReduceBalance() {
        tuitionService.calculateFee(1, 500.0);
        double before = tuitionService.getRemainingBalance(1);
        tuitionService.makePayment(1, 500.0);
        double after = tuitionService.getRemainingBalance(1);
        assertTrue(after < before);
    }

    @Test
    void makePayment_shouldNotExceedTotalFee() {
        tuitionService.calculateFee(1, 500.0);
        tuitionService.makePayment(1, 99999.0); // overpay
        assertEquals(0.0, tuitionService.getRemainingBalance(1));
    }

    @Test
    void getRemainingBalance_withNoRecord_shouldReturnZero() {
        assertEquals(0.0, tuitionService.getRemainingBalance(999));
    }
}