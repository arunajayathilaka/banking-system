package com.arunadj;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerWrapperTest {

    @Test
    void testNextLineWithValidInput() {
        String input = "Hello, World!";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ScannerWrapper scannerWrapper = new ScannerWrapper();
        String result = scannerWrapper.nextLine();
        assertEquals(input, result);
    }

    @Test
    void testNextLineWithEmptyInput() {
        String input = " ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ScannerWrapper scannerWrapper = new ScannerWrapper();
        String result = scannerWrapper.nextLine();
        assertEquals(input, result);
    }

    @Test
    void testNextLineWithMultipleLines() {
        String input = "Line 1\nLine 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ScannerWrapper scannerWrapper = new ScannerWrapper();
        String firstLine = scannerWrapper.nextLine();
        String secondLine = scannerWrapper.nextLine();

        assertEquals("Line 1", firstLine);
        assertEquals("Line 2", secondLine);
    }
}
