package com.arunadj.service;

import com.arunadj.dto.TransactionDTO;
import com.arunadj.model.Account;
import com.arunadj.model.Transaction;
import com.arunadj.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account("12345", 0);
    }

    @Test
    void testAddDepositTransaction() {
        TransactionDTO depositDTO = new TransactionDTO("20240612", "12345", "D", 500.0);
        when(accountRepository.getAccountById("12345")).thenReturn(account);

        accountService.addTransaction(depositDTO);

        assertEquals(1, account.getTransactions().size());
        assertEquals("20240612-01", account.getTransactions().get(0).getTxnId());
        assertEquals(500.0, account.getBalance());
        assertEquals("D", account.getTransactions().get(0).getType());
    }

    @Test
    void testAddWithdrawalTransactionWithSufficientFunds() {
        account.addTransaction(new Transaction(LocalDate.now(), "12345", "D", 1000.0, "txn-01"));
        TransactionDTO withdrawalDTO = new TransactionDTO(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE), "12345", "W", 500.0);
        when(accountRepository.getAccountById("12345")).thenReturn(account);

        accountService.addTransaction(withdrawalDTO);

        assertEquals(2, account.getTransactions().size());
        assertEquals("W", account.getTransactions().get(1).getType());
        assertEquals("12345", account.getTransactions().get(1).getAccountId());
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void testAddWithdrawalTransactionWithInsufficientFunds() {
        TransactionDTO withdrawalDTO = new TransactionDTO(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE), "12345", "W", 500.0);
        when(accountRepository.getAccountById("12345")).thenReturn(account);

        accountService.addTransaction(withdrawalDTO);

        assertEquals(0, account.getTransactions().size());
        assertEquals(0, account.getBalance());
    }

    @Test
    void testPrintAccountStatement() {
        account.addTransaction(new Transaction(LocalDate.now(), "1234", "D", 1000.0, "txn-01"));
        when(accountRepository.getAccountById("12345")).thenReturn(account);

        assertDoesNotThrow(() -> accountService.printAccountStatement("12345"));
    }

    @Test
    void testGetAccount() {
        when(accountRepository.getAccountById("12345")).thenReturn(account);
        Account actualAccount = accountService.getAccount("12345");
        assertNotNull(actualAccount);
    }
}
