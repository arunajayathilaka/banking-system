package com.arunadj.action;

import com.arunadj.ScannerFactory;
import com.arunadj.ScannerWrapper;
import com.arunadj.dto.PrintRequestDTO;
import com.arunadj.model.Account;
import com.arunadj.service.AccountService;
import com.arunadj.service.InterestService;
import com.arunadj.service.ServiceFactory;
import com.arunadj.service.ValidationService;
import com.arunadj.validator.InputValidator;

import java.util.Scanner;

/**
 * Print statement for month.
 * need to input account and month to generate the statement <Account> <Year><Month>
 */
public class PrintStatementAction implements MenuAction {
    public static final int PERMITTED_TOKEN_SIZE_FOR_PRINT_REQUEST = 2;

    private final InterestService interestService = ServiceFactory.getInterestService();
    private final AccountService accountService = ServiceFactory.getAccountService();

    private final ScannerWrapper scanner = ScannerFactory.getScanner();

    @Override
    public void execute() {
        while (true) {
            System.out.println("\nPlease enter account and month to generate the statement <Account> <Year><Month>\n(or enter blank to go back to main menu):");
            String entry = scanner.nextLine().trim();
            if (InputValidator.newInstance().isValid(entry, PERMITTED_TOKEN_SIZE_FOR_PRINT_REQUEST)) {
                PrintRequestDTO printRequestDTO = PrintRequestDTO.parse(entry);
                if (ValidationService.validate(printRequestDTO)) {
                    Account account = accountService.getAccount(printRequestDTO.getAccountId());
                    interestService.applyInterest(account, printRequestDTO.getDate());
                    accountService.printAccountStatement(account);
                    return;
                } else {
                    System.out.println("Invalid input. Please follow the <Account> <Year><Month> format.");
                }
            } else {
                return;
            }
        }
    }

    @Override
    public String getCmdCode() {
        return "P";
    }
}
