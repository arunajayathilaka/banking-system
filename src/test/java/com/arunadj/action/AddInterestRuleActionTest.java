package com.arunadj.action;

import com.arunadj.ScannerFactory;
import com.arunadj.ScannerWrapper;
import com.arunadj.service.InterestService;
import com.arunadj.service.ServiceFactory;
import com.arunadj.service.ValidationService;
import com.arunadj.validator.InputValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddInterestRuleActionTest {

    private AddInterestRuleAction addInterestRuleAction;
    private InterestService mockInterestService;
    private ScannerWrapper mockScanner;
    private InputValidator mockInputValidator;

    MockedStatic<ServiceFactory> serviceFactoryMockedStatic;
    MockedStatic<ScannerFactory> scannerFactoryMockedStatic;
    MockedStatic<InputValidator> inputValidatorMockedStatic;
    MockedStatic<ValidationService> validationServiceMockedStatic;


    @BeforeEach
    void setUp() {
        mockInterestService = mock(InterestService.class);

        mockScanner = mock(ScannerWrapper.class);
        mockInputValidator = mock(InputValidator.class);

        serviceFactoryMockedStatic = mockStatic(ServiceFactory.class);
        scannerFactoryMockedStatic = mockStatic(ScannerFactory.class);
        inputValidatorMockedStatic = mockStatic(InputValidator.class);
        validationServiceMockedStatic = mockStatic(ValidationService.class);

        when(ServiceFactory.getInterestService()).thenReturn(mockInterestService);
        when(ScannerFactory.getScanner()).thenReturn(mockScanner);
        when(InputValidator.newInstance()).thenReturn(mockInputValidator);
    }

    @AfterEach
    void tearDown() {
        serviceFactoryMockedStatic.close();
        scannerFactoryMockedStatic.close();
        inputValidatorMockedStatic.close();
        validationServiceMockedStatic.close();
    }

    @Test
    void testExecuteWithValidInput() {
        String validInput = "2024-01-01 101 5";

        when(mockScanner.nextLine()).thenReturn(validInput);
        when(mockInputValidator.isValid(validInput, AddInterestRuleAction.PERMITTED_TOKEN_SIZE_FOR_INTEREST_RULE)).thenReturn(true);

        when(ValidationService.validate(any())).thenReturn(true);

        addInterestRuleAction = new AddInterestRuleAction();
        addInterestRuleAction.execute();

        verify(mockInterestService).addInterestRule(any());
        verify(mockInterestService).displayInterestRules();
        verify(mockScanner, times(1)).nextLine();
    }

    @Test
    void testExecuteWithInvalidInput() {
        String invalidInput = "invalid input";

        when(mockScanner.nextLine()).thenReturn(invalidInput);
        when(mockInputValidator.isValid(invalidInput, AddInterestRuleAction.PERMITTED_TOKEN_SIZE_FOR_INTEREST_RULE)).thenReturn(false);

        addInterestRuleAction = new AddInterestRuleAction();
        addInterestRuleAction.execute();

        verify(mockScanner, times(1)).nextLine();
        verify(mockScanner).nextLine();
    }

    @Test
    void testExecuteWithValidInputButInvalidValidation() {
        String validInput = "2024-01-01 101 5";

        when(mockScanner.nextLine()).thenReturn(validInput).thenReturn(" ");
        when(mockInputValidator.isValid(validInput, AddInterestRuleAction.PERMITTED_TOKEN_SIZE_FOR_INTEREST_RULE)).thenReturn(true);
        when(ValidationService.validate(any())).thenReturn(false);

        addInterestRuleAction = new AddInterestRuleAction();
        addInterestRuleAction.execute();

        verify(mockInterestService, never()).addInterestRule(any());
        verify(mockInterestService, never()).displayInterestRules();
    }

    @Test
    void testExecuteWithEmptyInput() {
        when(mockScanner.nextLine()).thenReturn("");

        addInterestRuleAction = new AddInterestRuleAction();
        addInterestRuleAction.execute();

        verify(mockScanner, times(1)).nextLine();
        verify(mockInterestService, never()).addInterestRule(any());
        verify(mockInterestService, never()).displayInterestRules();
    }

    @Test
    void testGetCmdCode() {
        addInterestRuleAction = new AddInterestRuleAction();
        assertEquals("I", addInterestRuleAction.getCmdCode());
    }
}

