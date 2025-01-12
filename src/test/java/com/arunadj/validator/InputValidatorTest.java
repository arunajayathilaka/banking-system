package com.arunadj.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputValidatorTest {

    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = InputValidator.newInstance();
    }

    @Test
    void testValidInputWithCorrectSize() {
        String input = "Hello World";
        int size = 2;
        assertTrue(inputValidator.isValid(input, size));
    }

    @Test
    void testValidInputWithIncorrectSize() {
        String input = "Hello World";
        int size = 3;
        assertFalse(inputValidator.isValid(input, size));
    }

    @Test
    void testEmptyInput() {
        String input = "";
        int size = 2;
        assertFalse(inputValidator.isValid(input, size));
    }

    @Test
    void testInputWithExtraSpaces() {
        String input = "  Hello World  ";
        int size = 2;
        assertTrue(inputValidator.isValid(input.trim(), size));
    }

    @Test
    void testInputWithLessThanRequiredParts() {
        String input = "Hello";
        int size = 2;
        assertFalse(inputValidator.isValid(input, size));
    }

    @Test
    void testNullInput() {
        String input = null;
        int size = 2;
        assertFalse(inputValidator.isValid(input, size));
    }
}

