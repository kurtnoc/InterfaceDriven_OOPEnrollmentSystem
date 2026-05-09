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
        // Passing the implementation into enrollment
        enrollmentService = new EnrollmentServiceImpl(studentService);
        // Passing both services into tuition
        tuitionService = new TuitionServiceImpl(studentService, enrollmentService);

        // Add a test student
        studentService.addStudent(new Student(1, "Alice", "BSIT"));
    }

    @Test
    void calculateFee_standardRate_shouldBeCorrect() {
        double rate = 1000.0;
        double expected = 3 * rate; // 3000.0

        double actual = tuitionService.calculateFee(1, rate);

        assertEquals(expected, actual, "Standard fee should be 3 units times the rate.");
    }

    @Test
    void calculateFee_withScholarship_shouldApply50PercentDiscount() {
        double rate = 1000.0;
        double normalFee = 3 * rate; // 3000.0
        double expectedDiscounted = normalFee * 0.50; // 1500.0

        tuitionService.grantScholarship(1);
        double actual = tuitionService.calculateFee(1, rate);

        assertEquals(expectedDiscounted, actual, "Scholarship students should only pay 50%.");
    }

    @Test
    void makePayment_shouldReduceBalance() {
        tuitionService.calculateFee(1, 1000.0); // 3000.0 total
        double before = tuitionService.getRemainingBalance(1);

        tuitionService.makePayment(1, 1000.0);

        double after = tuitionService.getRemainingBalance(1);
        assertEquals(before - 1000.0, after, "Balance should decrease by the payment amount.");
    }

    @Test
    void makePayment_overpay_shouldResultInZeroBalance() {
        tuitionService.calculateFee(1, 500.0); // 1500.0 total

        // Paying more than the total (2000.0)
        tuitionService.makePayment(1, 2000.0);

        assertEquals(0.0, tuitionService.getRemainingBalance(1),
                "Balance should not go negative; it should cap at 0.");
    }

    @Test
    void getRemainingBalance_withNoRecord_shouldReturnZero() {
        // ID 999 hasn't had calculateFee() called yet
        assertEquals(0.0, tuitionService.getRemainingBalance(999),
                "Non-existent records should default to a balance of 0.");
    }

    @Test
    void fullPayment_shouldResultInZeroBalance() {
        double fee = tuitionService.calculateFee(1, 1000.0);
        tuitionService.makePayment(1, fee);

        assertEquals(0.0, tuitionService.getRemainingBalance(1),
                "Balance should be exactly 0 after full payment.");
    }
}