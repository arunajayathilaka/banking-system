package com.arunadj.service;

import com.arunadj.dto.InterestRuleDTO;
import com.arunadj.dto.PrintRequestDTO;
import com.arunadj.dto.TransactionDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationServiceTest {

    @Test
    void testValidInterestRuleDTO() {
        InterestRuleDTO rule = new InterestRuleDTO("20250101", "R001", 5.0);
        boolean validated = ValidationService.validate(rule);
        assertTrue(validated);
    }

    @Test
    void testInvalidDateFormat_InterestRule() {
        InterestRuleDTO rule = new InterestRuleDTO("20251301", "R001", 5.0);
        boolean validated = ValidationService.validate(rule);
        assertFalse(validated);
    }

    @Test
    void testInvalidRate_InterestRule() {
        InterestRuleDTO rule = new InterestRuleDTO("20250101", "R001", 101.0);
        boolean validated = ValidationService.validate(rule);
        assertFalse(validated);
    }

    @Test
    void testMissingRuleId_InterestRule() {
        InterestRuleDTO rule = new InterestRuleDTO("20250101", "", 5.0);
        boolean validated = ValidationService.validate(rule);
        assertFalse(validated);
    }

    @Test
    void testNegativeRate_InterestRule() {
        InterestRuleDTO rule = new InterestRuleDTO("20250101", "R001", -1.0);
        boolean validated = ValidationService.validate(rule);
        assertFalse(validated);
    }

    @Test
    void testValidPrintRequestDTO() {
        PrintRequestDTO request = new PrintRequestDTO("ACC123", "202501");
        boolean validated = ValidationService.validate(request);
        assertTrue(validated);
    }

    @Test
    void testMissingAccountId_PrintRequest() {
        PrintRequestDTO request = new PrintRequestDTO("", "202501");
        boolean validated = ValidationService.validate(request);
        assertFalse(validated);
    }

    @Test
    void testNullAccountId_PrintRequest() {
        PrintRequestDTO request = new PrintRequestDTO(null, "202501");
        boolean validated = ValidationService.validate(request);
        assertFalse(validated);
    }

    @Test
    void testInvalidDateFormat_YYYYMM_PrintRequest() {
        PrintRequestDTO request = new PrintRequestDTO("ACC123", "202513");
        boolean validated = ValidationService.validate(request);
        assertFalse(validated);
    }

    @Test
    void testNullDate_PrintRequest() {
        PrintRequestDTO request = new PrintRequestDTO("ACC123", null);
        boolean validated = ValidationService.validate(request);
        assertFalse(validated);
    }

    @Test
    void testEmptyDate_PrintRequest() {
        PrintRequestDTO request = new PrintRequestDTO("ACC123", "");
        boolean validated = ValidationService.validate(request);
        assertFalse(validated);
    }

    @Test
    void testValidTransactionDTO_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", "ACC123", "D", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertTrue(validated);
    }

    @Test
    void testInvalidDateFormat_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20251301", "ACC123", "D", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testNullDate_Transaction() {
        TransactionDTO transaction = new TransactionDTO(null, "ACC123", "D", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testBlankAccountId_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", "", "D", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testNullAccountId_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", null, "D", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testInvalidType_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", "ACC123", "X", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testBlankType_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", "ACC123", "", 100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testNegativeAmount_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", "ACC123", "D", -100.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

    @Test
    void testZeroAmount_Transaction() {
        TransactionDTO transaction = new TransactionDTO("20250101", "ACC123", "D", 0.0);
        boolean validated = ValidationService.validate(transaction);
        assertFalse(validated);
    }

}
