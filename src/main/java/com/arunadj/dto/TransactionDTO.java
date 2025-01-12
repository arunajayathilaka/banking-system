package com.arunadj.dto;

import com.arunadj.validator.date.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DTO for Transaction.
 */
@Getter
@AllArgsConstructor
@Builder
public class TransactionDTO {
    @NotNull
    @ValidLocalDate(message = "Date must be in the format yyyyMMdd")
    private String date;

    @NotBlank
    private String accountId;

    @NotBlank
    @Pattern(regexp = "^[DW]{1}$", message = "type should be D or W")
    private String type; // D for deposit, W for withdrawal

    @Positive
    private double amount;

    public static TransactionDTO parse(String input) {
        String[] parts = input.split(" ");
        return TransactionDTO.builder().date(parts[0]).accountId(parts[1]).type(parts[2].toUpperCase()).amount(Double.parseDouble(parts[3])).build();
    }

    public LocalDate getDate() {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
