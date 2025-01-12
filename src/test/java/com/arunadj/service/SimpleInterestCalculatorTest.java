package com.arunadj.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleInterestCalculatorTest {

    private SimpleInterestCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimpleInterestCalculator();
    }

    @Test
    void testCalculateInterest_PositiveBalance() {
        double balance = 1000.0;
        double rate = 5.0;
        int days = 30;

        double expectedInterest = balance * (rate / 100) * days / 365;
        double actualInterest = calculator.calculateInterest(balance, rate, days);

        assertEquals(expectedInterest, actualInterest, 0.0001);
    }

    @Test
    void testCalculateInterest_ZeroBalance() {
        double balance = 0.0;
        double rate = 5.0;
        int days = 30;

        double actualInterest = calculator.calculateInterest(balance, rate, days);

        assertEquals(0.0, actualInterest, 0.0001);
    }

    @Test
    void testCalculateInterest_NegativeBalance() {
        double balance = -500.0;
        double rate = 5.0;
        int days = 30;

        double expectedInterest = balance * (rate / 100) * days / 365;
        double actualInterest = calculator.calculateInterest(balance, rate, days);

        assertEquals(expectedInterest, actualInterest, 0.0001);
    }

    @Test
    void testCalculateInterest_ZeroDays() {
        double balance = 1000.0;
        double rate = 5.0;
        int days = 0;

        double actualInterest = calculator.calculateInterest(balance, rate, days);

        assertEquals(0.0, actualInterest, 0.0001);
    }

    @Test
    void testCalculateInterest_ZeroRate() {
        double balance = 1000.0;
        double rate = 0.0;
        int days = 30;

        double actualInterest = calculator.calculateInterest(balance, rate, days);

        assertEquals(0.0, actualInterest, 0.0001);
    }
}
