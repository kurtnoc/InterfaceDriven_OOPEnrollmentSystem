
package org.example;

import org.example.model.*;
import org.example.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TuitionServiceTest {
    private StudentServiceImpl studentService;
    private EnrollmentServiceImpl enrollmentService;
    private TuitionServiceImpl tuitionService;

    @BeforeEach
    void setUp() {
        studentService    = new StudentServiceImpl();
        enrollmentService = new EnrollmentServiceImpl(studentService);
        tuitionService    = new TuitionServiceImpl(studentService, enrollmentService);

        studentService.addStudent(new Student(1, "Alice", "BSIT"));
        studentService.addStudent(new Student(2, "Bob",   "BSIT"));
    }

    @Test
    void calculateFee_shouldReturnPositiveAmount() {
        double fee = tuitionService.calculateFee(1, 500.0);
        assertTrue(fee > 0);
    }

    @Test
    void calculateFee_nonExistentStudent_shouldReturnZero() {
        assertEquals(0, tuitionService.calculateFee(999, 500.0));
    }

    @Test
    void makePayment_shouldReduceRemainingBalance() {
        tuitionService.calculateFee(1, 500.0);
        double before = tuitionService.getRemainingBalance(1);
        tuitionService.makePayment(1, 500.0);
        double after = tuitionService.getRemainingBalance(1);
        assertTrue(after < before);
    }

    @Test
    void makePayment_overpayment_balanceShouldNotGoBelowZero() {
        tuitionService.calculateFee(1, 500.0);
        tuitionService.makePayment(1, 999999.0);
        assertEquals(0.0, tuitionService.getRemainingBalance(1));
    }

    @Test
    void makePayment_withNoRecord_shouldNotCrash() {
        assertDoesNotThrow(() -> tuitionService.makePayment(1, 500.0));
    }

    @Test
    void getRemainingBalance_withNoRecord_shouldReturnZero() {
        assertEquals(0.0, tuitionService.getRemainingBalance(999));
    }

    @Test
    void grantScholarship_shouldApply50PercentDiscount() {
        // Without scholarship: 3 units * 500 = 1500
        double normalFee = tuitionService.calculateFee(2, 500.0);

        // With scholarship on student 1
        tuitionService.grantScholarship(1);
        double discountedFee = tuitionService.calculateFee(1, 500.0);

        assertEquals(normalFee * 0.5, discountedFee, 0.01);
    }

    @Test
    void grantScholarship_isFullyPaid_afterPayingDiscountedAmount() {
        tuitionService.grantScholarship(1);
        double fee = tuitionService.calculateFee(1, 500.0);
        tuitionService.makePayment(1, fee);
        TuitionFeePayment record = tuitionService.getPaymentRecord(1);
        assertTrue(record.isFullyPaid());
    }

    @Test
    void getPaymentRecord_shouldReturnCorrectStudent() {
        tuitionService.calculateFee(1, 500.0);
        TuitionFeePayment record = tuitionService.getPaymentRecord(1);
        assertNotNull(record);
        assertEquals("Alice", record.getStudent().getName());
    }
}