package com.arunadj.service;

import com.arunadj.dto.TransactionDTO;
import com.arunadj.model.Account;
import com.arunadj.model.Transaction;
import com.arunadj.repository.AccountRepository;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AccountService implements Service {
    private final Map<String, Integer> transactionCounter = new HashMap<>();

    private final AccountRepository accountRepository;

    public void addTransaction(TransactionDTO transactionDTO) {
        Account account = accountRepository.getAccountById(transactionDTO.getAccountId());

        Transaction entity = Transaction.toEntity(transactionDTO);
        entity.setTxnId(generateTxnId(transactionDTO.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
        account.addTransaction(entity);
        printAccountStatement(account);
    }

    public void printAccountStatement(Account account) {
        System.out.println("\nAccount: " + account.getAccountId());
        System.out.println("| Date     | Txn Id      | Type | Amount |");
        for (Transaction txn : account.getTransactions()) {
            System.out.printf("| %s | %-10s | %-4s | %.2f |\n", txn.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")), txn.getTxnId(), txn.getType(), txn.getAmount());
        }
    }

    public void printAccountStatement(String accountId) {
        Account account = accountRepository.getAccountById(accountId);
        System.out.println("\nAccount: " + account.getAccountId());
        System.out.println("| Date     | Txn Id      | Type | Amount |");
        for (Transaction txn : account.getTransactions()) {
            System.out.printf("| %s | %-10s | %-4s | %.2f |\n", txn.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")), txn.getTxnId(), txn.getType(), txn.getAmount());
        }
    }

    private String generateTxnId(String dateStr) {
        int count = transactionCounter.getOrDefault(dateStr, 1);
        String txnId = dateStr + "-" + String.format("%02d", count);
        transactionCounter.put(dateStr.toString(), count + 1);
        return txnId;
    }

    public Account getAccount(String accountId) {
        return accountRepository.getAccountById(accountId);
    }
}
