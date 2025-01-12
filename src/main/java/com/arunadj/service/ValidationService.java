package com.arunadj.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * Provides a simple validation service for objects using Java Bean Validation.
 * Prints validation errors to the console if any are found.
 */
public class ValidationService implements Service {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * Validates the given object and prints any constraint violations.
     *
     * @param <T>    the type of the object to validate
     * @param object the object to validate
     * @return {@code true} if the object is valid, {@code false} otherwise
     */
    public static <T> boolean validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
            return false;
        }
        return true;
    }
}
