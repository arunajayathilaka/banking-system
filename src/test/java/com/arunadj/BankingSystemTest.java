package com.arunadj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class BankingSystemTest {

    private MockedStatic<ScannerFactory> mockedScannerFactory;

    @BeforeEach
    void setUp() {
        mockedScannerFactory = mockStatic(ScannerFactory.class);
    }

    @AfterEach
    void tearDown() {
        mockedScannerFactory.close();
    }

    @Test
    void testValidChoice() {
    }
}


