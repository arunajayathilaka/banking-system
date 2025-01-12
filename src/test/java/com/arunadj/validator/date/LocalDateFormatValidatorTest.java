package com.arunadj.validator.date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalDateFormatValidatorTest {

    private LocalDateFormatValidator validator;

    @BeforeEach
    void setUp() {
        validator = new LocalDateFormatValidator();
    }

    private void setFormatType(LocalDateFormatValidator validator, DateFormatType formatType) throws NoSuchFieldException, IllegalAccessException {
        Field field = LocalDateFormatValidator.class.getDeclaredField("formatType");
        field.setAccessible(true);
        field.set(validator, formatType);
    }

    @Test
    void testValidYearMonthFormat() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH);
        assertTrue(validator.isValid("202301", null));
    }

    @Test
    void testInvalidYearMonthFormat() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH);
        assertFalse(validator.isValid("202313", null));
    }

    @Test
    void testValidYearMonthDayFormat() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertTrue(validator.isValid("20230101", null));
    }

    @Test
    void testInvalidYearMonthDayFormat() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertFalse(validator.isValid("20230232", null));
    }

    @Test
    void testNullValue() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void testValidYearMonthWithFormatTypeYearMonth() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH);
        assertTrue(validator.isValid("202301", null));
    }

    @Test
    void testInvalidYearMonthWithFormatTypeYearMonth() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH);
        assertFalse(validator.isValid("202313", null));
    }

    @Test
    void testValidYearMonthDayWithFormatTypeYearMonthDay() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertTrue(validator.isValid("20230101", null));
    }

    @Test
    void testInvalidYearMonthDayWithFormatTypeYearMonthDay() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertFalse(validator.isValid("20230232", null));
    }

    @Test
    void testInvalidYearMonthWithSlash() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH);
        assertFalse(validator.isValid("2024/12", null));
    }

    @Test
    void testInvalidYearMonthWithDash() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH);
        assertFalse(validator.isValid("2024-8", null));
    }

    @Test
    void testInvalidYearMonthDayWithInvalidDay() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertFalse(validator.isValid("2024-01-34", null));
    }

    @Test
    void testInvalidYearMonthDayWithSlashes() throws NoSuchFieldException, IllegalAccessException {
        setFormatType(validator, DateFormatType.YEAR_MONTH_DAY);
        assertFalse(validator.isValid("2024/02/12", null));
    }
}
