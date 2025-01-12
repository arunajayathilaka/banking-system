package com.arunadj;

import lombok.Getter;

/**
 * Provides a single instance of {@code ScannerWrapper} for input operations.
 */
public class ScannerFactory {
    @Getter
    private static final ScannerWrapper scanner = new ScannerWrapper();

}
