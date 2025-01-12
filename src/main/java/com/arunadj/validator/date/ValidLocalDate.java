package com.arunadj.validator.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a date string matches the specified format ({@code yyyyMM} or {@code yyyyMMdd}).
 */
@Constraint(validatedBy = LocalDateFormatValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocalDate {
    String message() default "Invalid date format. It should be yyyyMMdd.";

    /**
     * The expected date format type.
     */
    DateFormatType formatType() default DateFormatType.YEAR_MONTH_DAY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

