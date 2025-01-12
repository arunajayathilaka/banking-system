package com.arunadj.repository;

import com.arunadj.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
    }

    @Test
    void testGetAccountById_whenAccountExists() throws NoSuchFieldException, IllegalAccessException {
        String accountId = "12345";
        Account existingAccount = Account.builder().accountId(accountId).build();

        setAccounts(existingAccount);

        Account retrievedAccount = accountRepository.getAccountById(accountId);

        assertNotNull(retrievedAccount);
        assertEquals(accountId, retrievedAccount.getAccountId());
    }

    private void setAccounts(Account existingAccount) throws NoSuchFieldException, IllegalAccessException {
        Field accountsField = AccountRepository.class.getDeclaredField("accounts");
        accountsField.setAccessible(true);
        Map<String, Account> accountMap = (Map<String, Account>) accountsField.get(null);
        accountMap.put(existingAccount.getAccountId(), existingAccount);
    }

    @Test
    void testGetAccountById_whenAccountDoesNotExist() {
        String accountId = "67890";

        Account retrievedAccount = accountRepository.getAccountById(accountId);

        assertNotNull(retrievedAccount);
        assertEquals(accountId, retrievedAccount.getAccountId());
    }


}
