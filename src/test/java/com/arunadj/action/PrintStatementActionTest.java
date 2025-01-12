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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PrintStatementActionTest {
    private PrintStatementAction printStatementAction;
    private InterestService mockInterestService;
    private AccountService mockAccountService;
    private ScannerWrapper mockScanner;

    private MockedStatic<ServiceFactory> serviceFactoryMockedStatic;
    private MockedStatic<ScannerFactory> scannerFactoryMockedStatic;
    private MockedStatic<InputValidator> inputValidatorMockedStatic;
    private MockedStatic<ValidationService> validationServiceMockedStatic;

    @BeforeEach
    void setUp() {
        mockInterestService = mock(InterestService.class);
        mockAccountService = mock(AccountService.class);
        mockScanner = mock(ScannerWrapper.class);

        serviceFactoryMockedStatic = Mockito.mockStatic(ServiceFactory.class);
        scannerFactoryMockedStatic = Mockito.mockStatic(ScannerFactory.class);
        inputValidatorMockedStatic = Mockito.mockStatic(InputValidator.class);
        validationServiceMockedStatic = Mockito.mockStatic(ValidationService.class);

        when(ServiceFactory.getInterestService()).thenReturn(mockInterestService);
        when(ServiceFactory.getAccountService()).thenReturn(mockAccountService);
        when(ScannerFactory.getScanner()).thenReturn(mockScanner);

        printStatementAction = new PrintStatementAction();
    }

    @AfterEach
    void tearDown() {
        serviceFactoryMockedStatic.close();
        scannerFactoryMockedStatic.close();
        inputValidatorMockedStatic.close();
        validationServiceMockedStatic.close();
    }

    @Test
    void testValidPrintRequest() {
        when(mockScanner.nextLine()).thenReturn("ACC123 202401");

        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);
        when(mockValidator.isValid("ACC123 202401", PrintStatementAction.PERMITTED_TOKEN_SIZE_FOR_PRINT_REQUEST))
                .thenReturn(true);

        PrintRequestDTO mockPrintRequest = mock(PrintRequestDTO.class);
        validationServiceMockedStatic.when(() -> ValidationService.validate(any(PrintRequestDTO.class))).thenReturn(true);
        when(mockPrintRequest.getAccountId()).thenReturn("ACC123");
        when(mockPrintRequest.getDate()).thenReturn(java.time.YearMonth.of(2024, 1));

        Account mockAccount = mock(Account.class);
        when(mockAccountService.getAccount("ACC123")).thenReturn(mockAccount);

        printStatementAction.execute();

        verify(mockInterestService).applyInterest(mockAccount, java.time.YearMonth.of(2024, 1));
        verify(mockAccountService).printAccountStatement(mockAccount);
    }

    @Test
    void testInvalidPrintRequest_invalidDate() {
        when(mockScanner.nextLine()).thenReturn("ACC123 202414").thenReturn(" ");

        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);
        when(mockValidator.isValid("ACC123 202414", PrintStatementAction.PERMITTED_TOKEN_SIZE_FOR_PRINT_REQUEST))
                .thenReturn(true);

        printStatementAction.execute();

        verify(mockInterestService, never()).applyInterest(any(), any());
        verify(mockAccountService, never()).printAccountStatement(any(Account.class));
    }

    @Test
    void testInvalidPrintRequest() {
        when(mockScanner.nextLine()).thenReturn("invalid input");

        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);
        when(mockValidator.isValid("invalid input", PrintStatementAction.PERMITTED_TOKEN_SIZE_FOR_PRINT_REQUEST))
                .thenReturn(false);

        printStatementAction.execute();

        verify(mockInterestService, never()).applyInterest(any(), any());
        verify(mockAccountService, never()).printAccountStatement(any(Account.class));
    }

    @Test
    void testBlankInputExits() {
        when(mockScanner.nextLine()).thenReturn(" ");
        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);

        printStatementAction.execute();

        verify(mockInterestService, never()).applyInterest(any(), any());
        verify(mockAccountService, never()).printAccountStatement(any(Account.class));
    }

    @Test
    void testGetCmdCode() {
        assertEquals("P", printStatementAction.getCmdCode());
    }
}
