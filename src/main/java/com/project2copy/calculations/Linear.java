package com.project2copy.calculations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Linear extends Mortgage{

    public Linear(double loanAmount, int term, double interest){
        super(loanAmount, term, interest);
    }

    @Override
    public void calculate(int start, int deferLength, double deferInterest) {
        double principal = loanAmount / term;
        double tempLoanAmount = loanAmount;

        for(int i = 0; i < term + deferLength; i++){
            if(i + 1 == start){
                for(int j = 0; j < deferLength; j++){
                    double tempInterest = tempLoanAmount * deferInterest;
                    Payment deferredPayment = new Payment(i + 1, tempInterest, 0, tempInterest, tempLoanAmount);
                    allPayments.add(deferredPayment);
                    i++;
                }
            }

            double paymentAmount = principal + (tempLoanAmount * interest);
            Payment payment = new Payment(i + 1, paymentAmount, principal, (tempLoanAmount * interest), tempLoanAmount);
            tempLoanAmount -= principal;
            allPayments.add(payment);
        }
    }

    @Override
    public ObservableList<Payment> getAllPayments() {
        return super.allPayments;
    }

    @Override
    public ObservableList<Payment> getAllPayments(int from, int to) {
        return FXCollections.observableArrayList(super.allPayments.subList(from, to));
    }
}
