package com.arunadj.service;

/**
 * This is contract for interest calculations.
 */
public interface InterestCalculator {
    /**
     *
     * @param balance balance to calculate
     * @param rate rate to calculate
     * @param days days to calculate
     * @return type of {@link double} interest value for days
     */
    double calculateInterest(double balance, double rate, int days);
}
