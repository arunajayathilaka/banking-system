package com.arunadj.validator.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Custom validator for date.
 * Validates date strings in {@code yyyyMM} or {@code yyyyMMdd} formats.
 *
 */
public class LocalDateFormatValidator implements ConstraintValidator<ValidLocalDate, String> {

    private static final DateTimeFormatter FORMATTER_YEAR_MONTH = DateTimeFormatter.ofPattern("yyyyMM");
    private static final DateTimeFormatter FORMATTER_YEAR_MONTH_DAY = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String YEAR_MONTH_PATTERN = "^[0-9]{6}$";
    private static final String YEAR_MONTH_DAY_PATTERN = "^[0-9]{8}$";

    private DateFormatType formatType;

    @Override
    public void initialize(ValidLocalDate constraintAnnotation) {
        formatType = constraintAnnotation.formatType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (DateFormatType.YEAR_MONTH.equals(formatType) && value.matches(YEAR_MONTH_PATTERN)) {
            return validateYearMonth(value);
        } else if (DateFormatType.YEAR_MONTH_DAY.equals(formatType) && value.matches(YEAR_MONTH_DAY_PATTERN)) {
            return validateYearMonthDay(value);
        }
        return false;
    }

    private boolean validateYearMonth(String value) {
        try {
            YearMonth.parse(value, FORMATTER_YEAR_MONTH);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean validateYearMonthDay(String value) {
        try {
            LocalDate.parse(value, FORMATTER_YEAR_MONTH_DAY);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
