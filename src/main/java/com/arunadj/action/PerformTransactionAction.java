package com.arunadj.action;

import com.arunadj.ScannerFactory;
import com.arunadj.ScannerWrapper;
import com.arunadj.dto.TransactionDTO;
import com.arunadj.service.AccountService;
import com.arunadj.service.ServiceFactory;
import com.arunadj.service.ValidationService;
import com.arunadj.validator.InputValidator;

/**
 * Perform transaction.
 * transaction details in <Date> <Account> <Type> <Amount> format
 */
public class PerformTransactionAction implements MenuAction {
    public static final int PERMITTED_TOKEN_SIZE_FOR_TRANSACTION = 4;
    private final AccountService accountService = ServiceFactory.getAccountService();
    private final ScannerWrapper scanner = ScannerFactory.getScanner();

    @Override
    public void execute() {
        while (true) {
            System.out.println("\nPlease enter transaction details in <Date> <Account> <Type> <Amount> format\n(or enter blank to go back to main menu):");
            String entry = scanner.nextLine().trim();

            if (InputValidator.newInstance().isValid(entry, PERMITTED_TOKEN_SIZE_FOR_TRANSACTION)) {
                TransactionDTO transactionDTO = TransactionDTO.parse(entry);
                if (ValidationService.validate(transactionDTO)) {
                    accountService.addTransaction(transactionDTO);
                    return;
                } else {
                    System.out.println("Invalid input. Please follow the <Date> <Account> <Type> <Amount> format.");
                }
            } else {
                return;
            }

        }
    }

    @Override
    public String getCmdCode() {
        return "T";
    }
}
