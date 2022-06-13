package com.project2copy.calculations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Annuity extends Mortgage {

    public Annuity(double amount, int term, double interest) {
        super(amount, term, interest);
    }

    @Override
    public void calculate(int start, int deferLength, double deferInterest) {
        double n = interest * Math.pow(interest + 1, term);
        double d = Math.pow(interest + 1, term) - 1;
        double paymentAmount = loanAmount * ( n / d);
        double tempLoanAmount = loanAmount;
        for (int i = 0; i < term + deferLength; i++){
            if(i + 1 == start){
                for(int j = 0; j < deferLength; j++){
                    double tempInterest = tempLoanAmount * deferInterest;
                    Payment deferredPayment = new Payment(i + 1, tempInterest, 0, tempInterest, tempLoanAmount);
                    allPayments.add(deferredPayment);
                    i++;
                }
            }
            double tempInterest = tempLoanAmount * interest;
            double tempPrincipal = paymentAmount - tempInterest;
            Payment payment = new Payment(i + 1, paymentAmount, tempPrincipal, tempInterest, tempLoanAmount);
            tempLoanAmount -= tempPrincipal;
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

