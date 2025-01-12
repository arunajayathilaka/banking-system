package com.arunadj.service;

/**
 * This is the simplest way of calculating interest based on annual rate.
 */
public class SimpleInterestCalculator implements InterestCalculator {
    @Override
    public double calculateInterest(double balance, double rate, int days) {
        return balance * (rate / 100) * days / 365;
    }
}
