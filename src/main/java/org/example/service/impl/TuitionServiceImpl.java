package org.example.service.impl;
import org.example.model.Student;
import org.example.model.TuitionFeePayment;
import org.example.service.ITuitionService;
import org.example.service.IStudentService;
import org.example.service.IEnrollmentService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TuitionServiceImpl implements ITuitionService {
    private Map<Integer, TuitionFeePayment> paymentRecords = new HashMap<>();
//Feature Scholarship Discount
    private Set<Integer> scholarshipStudents = new HashSet<>();
    private static final double SCHOLARSHIP_DISCOUNT = 0.50; // 50% off

    private IStudentService studentService;
    private IEnrollmentService enrollmentService;
    private static final double RATE_PER_UNIT = 500.0;

    public TuitionServiceImpl(IStudentService studentService, IEnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }


    @Override
    public double calculateFee(int studentId, double ratePerUnit) {
        // Find section the student belongs to, count their courses' total units
        var section = enrollmentService.findSectionById(-1); // iterate all sections via enrollment
        // Simplified: use a fixed 3-unit default if no section found with courses
        Student s = studentService.findById(studentId);
        if (s == null) { System.out.println("Student not found."); return 0; }

        // Find student's section and sum units
        double total = 0;
        for (int i = 1; i <= 100; i++) {
            var sec = enrollmentService.findSectionById(i);
            if (sec != null && sec.getEnrolledStudents().stream().anyMatch(st -> st.getId() == studentId)) {
                total = sec.getCourses().stream().mapToInt(c -> c.getUnits()).sum() * ratePerUnit;
                break;
            }
        }
        if (total == 0) total = 3 * ratePerUnit; // default 3 units

        paymentRecords.put(studentId, new TuitionFeePayment(studentId, s, total));
        System.out.printf("Tuition fee for %s: PHP %.2f%n", s.getName(), total);
        return total;
    }

    @Override
    public void makePayment(int studentId, double amount) {
        TuitionFeePayment rec = paymentRecords.get(studentId);
        if (rec == null) { System.out.println("No payment record. Calculate fee first."); return; }
        rec.pay(amount);
        System.out.printf("Payment of PHP %.2f accepted. Remaining balance: PHP %.2f%n",
                amount, rec.getRemainingBalance());
    }
//Scholarship Feature
    public void grantScholarship(int studentId) {
        scholarshipStudents.add(studentId);
        System.out.println("Scholarship granted to student ID " + studentId);
    }

    @Override
    public double calculateFee(int studentId, double ratePerUnit) {
        Student s = studentService.findById(studentId);
        if (s == null) { System.out.println("Student not found."); return 0; }

        double total = 3 * ratePerUnit; // default 3 units (replace with real section lookup)

        if (scholarshipStudents.contains(studentId)) {
            double discounted = total * (1 - SCHOLARSHIP_DISCOUNT);
            System.out.printf("Scholarship applied! Original: PHP %.2f → Discounted: PHP %.2f%n",
                    total, discounted);
            total = discounted;
        }

        paymentRecords.put(studentId, new TuitionFeePayment(studentId, s, total));
        System.out.printf("Tuition fee for %s: PHP %.2f%n", s.getName(), total);
        return total;
    }

    @Override
    public double getRemainingBalance(int studentId) {
        TuitionFeePayment rec = paymentRecords.get(studentId);
        if (rec == null) { System.out.println("No payment record found."); return 0; }
        System.out.printf("Remaining balance for %s: PHP %.2f [%s]%n",
                rec.getStudent().getName(), rec.getRemainingBalance(),
                rec.isFullyPaid() ? "FULLY PAID" : "PENDING");
        return rec.getRemainingBalance();
    }

    @Override
    public TuitionFeePayment getPaymentRecord(int studentId) {
        return paymentRecords.get(studentId);
    }
}