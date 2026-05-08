package org.example.model;
public class TuitionFeePayment {
    private int paymentId;
    private Student student;
    private double totalFee;
    private double amountPaid;

    public TuitionFeePayment(int paymentId, Student student, double totalFee) {
        this.paymentId = paymentId;
        this.student = student;
        this.totalFee = totalFee;
        this.amountPaid = 0;
    }

    public int getPaymentId() { return paymentId; }
    public Student getStudent() { return student; }
    public double getTotalFee() { return totalFee; }
    public double getAmountPaid() { return amountPaid; }
    public double getRemainingBalance() { return totalFee - amountPaid; }

    public void pay(double amount) {
        if (amount > 0) amountPaid = Math.min(amountPaid + amount, totalFee);
    }

    public boolean isFullyPaid() { return amountPaid >= totalFee; }

    @Override
    public String toString() {
        return String.format("Payment{student='%s', total=%.2f, paid=%.2f, balance=%.2f, status='%s'}",
                student.getName(), totalFee, amountPaid, getRemainingBalance(),
                isFullyPaid() ? "PAID" : "PENDING");
    }
}