package com.arunadj.dto;

import com.arunadj.validator.date.ValidLocalDate;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DTO for Interest Rule.
 */
@Getter
@AllArgsConstructor
@Builder
public class InterestRuleDTO {

    @NotNull
    @ValidLocalDate(message = "Date must be in the format yyyyMMdd")
    private String date;

    @NotBlank
    private String ruleId;
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be between 0.0 and 100.0")
    @DecimalMax(value = "100.0", inclusive = false, message = "Rate must be between 0.0 and 100.0")
    private double rate;

    public static InterestRuleDTO parse(String input) {
        String[] parts = input.split(" ");
        return InterestRuleDTO.builder().date(parts[0]).ruleId(parts[1]).rate(Double.parseDouble(parts[2])).build();
    }

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
