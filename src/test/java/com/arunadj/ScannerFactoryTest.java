package com.arunadj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ScannerFactoryTest {

    @Test
    void testScannerInitialization() {
        ScannerWrapper scanner = ScannerFactory.getScanner();
        assertNotNull(scanner);
    }

    @Test
    void testScannerSingleton() {
        ScannerWrapper scanner1 = ScannerFactory.getScanner();
        ScannerWrapper scanner2 = ScannerFactory.getScanner();
        assertSame(scanner1, scanner2);
    }
}

