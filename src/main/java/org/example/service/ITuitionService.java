package org.example.service;
import org.example.model.Student;
import org.example.model.TuitionFeePayment;

public interface ITuitionService {
    double calculateFee(int studentId, double ratePerUnit);
    //Scholarship Feature
    void grantScholarship(int studentId);
    void makePayment(int studentId, double amount);
    double getRemainingBalance(int studentId);
    TuitionFeePayment getPaymentRecord(int studentId);
}