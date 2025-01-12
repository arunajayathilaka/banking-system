package com.arunadj.validator;

public class InputValidator {

    private static InputValidator inputValidator;

    private InputValidator() {

    }

    public static InputValidator newInstance() {
        if (inputValidator == null) {
            inputValidator = new InputValidator();
        }
        return inputValidator;
    }


    public boolean isValid(String input, int size) {
        if (input == null || input.isEmpty()) return false;
        String[] parts = input.split(" ");
        if (parts.length != size) {
            return false;
        }
        return true;
    }
}
