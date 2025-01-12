package com.arunadj.action;

import com.arunadj.ScannerFactory;
import com.arunadj.ScannerWrapper;
import com.arunadj.dto.InterestRuleDTO;
import com.arunadj.service.InterestService;
import com.arunadj.service.ServiceFactory;
import com.arunadj.service.ValidationService;
import com.arunadj.validator.InputValidator;

/**
 * Define interest rules.
 * Interest rules details in <Date> <RuleId> <Rate in %> format.
 */
public class AddInterestRuleAction implements MenuAction {
    public static final int PERMITTED_TOKEN_SIZE_FOR_INTEREST_RULE = 3;
    private final InterestService interestService = ServiceFactory.getInterestService();
    private final ScannerWrapper scanner = ScannerFactory.getScanner();

    @Override
    public void execute() {
        while (true) {
            System.out.println("\nPlease enter interest rules details in <Date> <RuleId> <Rate in %> format\n(or enter blank to go back to main menu):");
            String entry = scanner.nextLine().trim();

            if (InputValidator.newInstance().isValid(entry, PERMITTED_TOKEN_SIZE_FOR_INTEREST_RULE)) {
                InterestRuleDTO interestRuleDTO = InterestRuleDTO.parse(entry);
                if (ValidationService.validate(interestRuleDTO)) {
                    interestService.addInterestRule(interestRuleDTO);
                    interestService.displayInterestRules();
                    return;
                } else {
                    System.out.println("Invalid input. Please follow the <Date> <RuleId> <Rate in %> format.");
                }
            } else {
                return;
            }
        }
    }

    @Override
    public String getCmdCode() {
        return "I";
    }
}
