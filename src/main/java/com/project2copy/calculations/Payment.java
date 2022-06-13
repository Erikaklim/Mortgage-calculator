package com.project2copy.calculations;

public class Payment {

    private int month;
    private double paymentAmount;
    private double principal;
    private double interest;
    private double remainingLoanAmount;

    public Payment(int month, double paymentAmount, double principal, double interest, double remainingLoanAmount) {
        this.month = month;
        this.paymentAmount = paymentAmount;
        this.principal = principal;
        this.interest = interest;
        this.remainingLoanAmount = remainingLoanAmount;
    }

    public int getMonth() {
        return month;
    }

    public double getPaymentAmount() {
        return Math.round(paymentAmount * 100d) / 100d;
    }

    public double getPrincipal() {
        return Math.round(principal * 100d) / 100d;
    }

    public double getInterest() {
        return Math.round(interest * 100d) / 100d;
    }

    public double getRemainingLoanAmount() {
        return Math.round(remainingLoanAmount * 100d) / 100d;
    }

}
