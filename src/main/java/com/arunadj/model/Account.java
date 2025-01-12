package com.arunadj.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This acts as an account entity class.
 */
@Getter
@AllArgsConstructor
@Builder
public class Account {
    private String accountId;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        if (transaction.getType().equalsIgnoreCase("D")) {
            balance += transaction.getAmount();
        } else if (transaction.getType().equalsIgnoreCase("W")) {
            if (balance >= transaction.getAmount()) {
                balance -= transaction.getAmount();
            } else {
                System.out.println("Insufficient funds. Transaction canceled.");
                return;
            }
        }
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
