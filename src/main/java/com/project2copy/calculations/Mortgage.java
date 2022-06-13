package com.project2copy.calculations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Mortgage {
    protected double loanAmount;
    protected int term;
    protected double interest;
    protected ObservableList<Payment> allPayments;

    public Mortgage(double loanAmount, int term, double interest) {
        this.loanAmount= loanAmount;
        this.term = term;
        this.interest = interest;
        allPayments = FXCollections.observableArrayList();
    }

    public abstract void calculate(int start, int deferLength, double deferInterest);
    public abstract ObservableList<Payment> getAllPayments();
    public abstract ObservableList<Payment> getAllPayments(int from, int to);

}
