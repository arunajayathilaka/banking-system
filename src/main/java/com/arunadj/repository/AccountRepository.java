package com.arunadj.repository;

import com.arunadj.model.Account;

import java.util.HashMap;
import java.util.Map;

/**
 * This will store all accounts in hashmap.
 */
public class AccountRepository {
    private static final Map<String, Account> accounts = new HashMap<>();

    /**
     * Gets Account by id,
     * if it's not there, create new.
     * @param accountId String value of accountId
     * @return Account Object
     */
    public Account getAccountById(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            account = Account.builder().accountId(accountId).build();
            accounts.put(accountId, account);
        }
        return account;
    }
}
