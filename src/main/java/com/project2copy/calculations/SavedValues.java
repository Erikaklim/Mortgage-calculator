package com.project2copy.calculations;

public class SavedValues {
    private double amount, interest, deferInterest;
    private int filter1, filter2, start, length, termYears, termMonths;
    private String type;

    public SavedValues(double amount, double interest, int filter1,
                       int filter2, int start, int length, double deferInterest, int termYears, int termMonths, String type) {
        this.amount = amount;
        this.interest = interest;
        this.filter1 = filter1;
        this.filter2 = filter2;
        this.start = start;
        this.length = length;
        this.deferInterest = deferInterest;
        this.termYears = termYears;
        this.termMonths = termMonths;
        this.type = type;
    }

    public double getAmount() {
        return Math.round(amount * 100d) / 100d;
    }

    public double getInterest() {
        return Math.round(interest * 100d) / 100d;
    }

    public int getFilter1() {
        return filter1;
    }

    public int getFilter2() {
        return filter2;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public double getDeferInterest(){
        return Math.round(deferInterest * 100d) / 100d;
    }

    public int getTermYears() {
        return termYears;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public String getType() {
        return type;
    }

}
