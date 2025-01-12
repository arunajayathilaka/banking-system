package com.arunadj.service;

import com.arunadj.dto.InterestRuleDTO;
import com.arunadj.model.Account;
import com.arunadj.model.InterestRule;
import com.arunadj.model.Transaction;
import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class InterestService implements Service {
    @Getter
    private final TreeMap<LocalDate, InterestRule> interestRules = new TreeMap<>();
    private final InterestCalculator calculator;

    public InterestService(InterestCalculator calculator) {
        this.calculator = calculator;
    }

    public void addInterestRule(InterestRuleDTO interestRuleDTO) {
        interestRules.put(interestRuleDTO.getDate(), InterestRule.toEntity(interestRuleDTO));
    }

    public void applyInterest(Account account, YearMonth yearMonth) {
        LocalDate startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate endDate = YearMonth.of(yearMonth.getYear(), yearMonth.getMonth()).atEndOfMonth();

        double totalInterest = 0.0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            LocalDate finalDate = date;
            double balance = account.getTransactions().stream()
                    .filter(txn -> !txn.getDate().isAfter(finalDate))
                    .mapToDouble(txn -> txn.getType().equals("D") ? txn.getAmount() : -txn.getAmount())
                    .sum();

            InterestRule rule = interestRules.floorEntry(date).getValue();
            totalInterest += calculator.calculateInterest(balance, rule.getRate(), 1);
        }

        if (totalInterest > 0) {
            account.addTransaction(new Transaction(endDate, "", "I", Math.round(totalInterest * 100.0) / 100.0, ""));
        }
    }

    public void displayInterestRules() {
        System.out.println("\nInterest rules:");
        interestRules.forEach((date, rule) -> System.out.printf("| %s | %s | %.2f%% |\n", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")), rule.getRuleId(), rule.getRate()));
    }

}
