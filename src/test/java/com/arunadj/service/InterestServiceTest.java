package com.arunadj.service;

import com.arunadj.dto.InterestRuleDTO;
import com.arunadj.model.Account;
import com.arunadj.model.InterestRule;
import com.arunadj.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterestServiceTest {

    private InterestService interestService;
    private Account account;

    @BeforeEach
    void setUp() {
        interestService = new InterestService(new SimpleInterestCalculator());

        account = Account.builder().build();
        account.addTransaction(new Transaction(LocalDate.of(2025, 1, 1), "", "D", 1000.0, ""));
        account.addTransaction(new Transaction(LocalDate.of(2025, 1, 10), "", "W", 500.0, ""));
        account.addTransaction(new Transaction(LocalDate.of(2025, 1, 20), "", "D", 300.0, ""));
    }

    @Test
    void testAddInterestRule() {
        InterestRuleDTO interestRuleDTO = InterestRuleDTO.builder().date("20250101").rate(5.0).build();

        interestService.addInterestRule(interestRuleDTO);

        assertEquals(1, interestService.getInterestRules().size());
        InterestRule rule = interestService.getInterestRules().firstEntry().getValue();
        assertEquals(5.0, rule.getRate());
        assertEquals(LocalDate.of(2025, 1, 1), rule.getDate());
        assertEquals(5.0, rule.getRate());
    }

    @Test
    void testApplyInterest() {
        InterestRuleDTO interestRuleDTO = InterestRuleDTO.builder().date("20250101").rate(5.0).build();

        interestService.addInterestRule(interestRuleDTO);

        interestService.applyInterest(account, YearMonth.of(2025, 1));

        assertEquals(4, account.getTransactions().size());
        Transaction interestTransaction = account.getTransactions().get(3);
        assertEquals("I", interestTransaction.getType());
        assertEquals(3.23, interestTransaction.getAmount());
    }

    @Test
    void testApplyInterestWithMultipleRules() {
        // Add two interest rules
        InterestRuleDTO interestRuleDTO1 = InterestRuleDTO.builder().date("20250101").rate(5.0).build();
        interestService.addInterestRule(interestRuleDTO1);

        InterestRuleDTO interestRuleDTO2 = InterestRuleDTO.builder().date("20250115").rate(7.0).build();
        interestService.addInterestRule(interestRuleDTO2);

        interestService.applyInterest(account, YearMonth.of(2025, 1));

        assertEquals(4, account.getTransactions().size());
        Transaction interestTransaction = account.getTransactions().get(3);
        assertEquals("I", interestTransaction.getType());
        assertEquals(3.9, interestTransaction.getAmount());
    }

    @Test
    void testDisplayInterestRules() {
        // Add two interest rules
        InterestRuleDTO interestRuleDTO1 = InterestRuleDTO.builder().date("20250101").ruleId("R001").rate(3.50).build();
        interestService.addInterestRule(interestRuleDTO1);

        InterestRuleDTO interestRuleDTO2 = InterestRuleDTO.builder().date("20250115").ruleId("R002").rate(4.00).build();
        interestService.addInterestRule(interestRuleDTO2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        interestService.displayInterestRules();

        String expectedOutput = "\nInterest rules:\r\n" +
                "| 20250101 | R001 | 3.50% |\n" +
                "| 20250115 | R002 | 4.00% |\n";

        assertEquals(expectedOutput, outputStream.toString());
    }
}
