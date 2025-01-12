package com.arunadj.action;

import com.arunadj.ScannerFactory;
import com.arunadj.ScannerWrapper;
import com.arunadj.dto.TransactionDTO;
import com.arunadj.service.AccountService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PerformTransactionActionTest {

    private PerformTransactionAction performTransactionAction;
    private AccountService mockAccountService;
    private ScannerWrapper mockScanner;
    private MockedStatic<ServiceFactory> serviceFactoryMockedStatic;
    private MockedStatic<ScannerFactory> scannerFactoryMockedStatic;
    private MockedStatic<InputValidator> inputValidatorMockedStatic;
    private MockedStatic<ValidationService> validationServiceMockedStatic;

    @BeforeEach
    void setUp() {
        mockAccountService = mock(AccountService.class);
        mockScanner = mock(ScannerWrapper.class);

        serviceFactoryMockedStatic = Mockito.mockStatic(ServiceFactory.class);
        scannerFactoryMockedStatic = Mockito.mockStatic(ScannerFactory.class);
        inputValidatorMockedStatic = Mockito.mockStatic(InputValidator.class);
        validationServiceMockedStatic = Mockito.mockStatic(ValidationService.class);

        when(ServiceFactory.getAccountService()).thenReturn(mockAccountService);
        when(ScannerFactory.getScanner()).thenReturn(mockScanner);

        performTransactionAction = new PerformTransactionAction();
    }

    @AfterEach
    void tearDown() {
        serviceFactoryMockedStatic.close();
        scannerFactoryMockedStatic.close();
        inputValidatorMockedStatic.close();
        validationServiceMockedStatic.close();
    }

    @Test
    void testValidTransactionInput() {
        when(mockScanner.nextLine()).thenReturn("20240101 ACC123 D 100.00");

        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);
        when(mockValidator.isValid("20240101 ACC123 D 100.00", PerformTransactionAction.PERMITTED_TOKEN_SIZE_FOR_TRANSACTION))
                .thenReturn(true);

        when(ValidationService.validate(any(TransactionDTO.class))).thenReturn(true);

        performTransactionAction.execute();

        verify(mockAccountService).addTransaction(any(TransactionDTO.class));
    }

    @Test
    void testInvalidTransactionInput_invalidType() {
        when(mockScanner.nextLine()).thenReturn("20240101 ACC123 S 100.00").thenReturn(" ");

        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);
        when(mockValidator.isValid("20240101 ACC123 S 100.00", PerformTransactionAction.PERMITTED_TOKEN_SIZE_FOR_TRANSACTION))
                .thenReturn(true);

        performTransactionAction.execute();

        verify(mockAccountService, never()).addTransaction(any());
    }

    @Test
    void testInvalidTransactionInput() {
        when(mockScanner.nextLine()).thenReturn("invalid input");

        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);
        when(mockValidator.isValid("invalid input", PerformTransactionAction.PERMITTED_TOKEN_SIZE_FOR_TRANSACTION))
                .thenReturn(false);

        performTransactionAction.execute();

        verify(mockAccountService, never()).addTransaction(any());
    }

    @Test
    void testBlankInputExits() {
        when(mockScanner.nextLine()).thenReturn(" ");
        InputValidator mockValidator = mock(InputValidator.class);
        when(InputValidator.newInstance()).thenReturn(mockValidator);

        performTransactionAction.execute();

        verify(mockAccountService, never()).addTransaction(any());
    }

    @Test
    void testGetCmdCode() {
        assertEquals("T", performTransactionAction.getCmdCode());
    }
}
