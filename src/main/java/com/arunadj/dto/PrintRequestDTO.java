package com.arunadj.dto;

import com.arunadj.validator.date.DateFormatType;
import com.arunadj.validator.date.ValidLocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * DTO for print Request.
 */
@Getter
@AllArgsConstructor
@Builder
public class PrintRequestDTO {

    @NotBlank
    private String accountId;

    @NotNull
    @ValidLocalDate(message = "Date must be in the format yyyyMM", formatType = DateFormatType.YEAR_MONTH)
    private String date;

    public static PrintRequestDTO parse(String input) {
        String[] parts = input.split(" ");
        return PrintRequestDTO.builder().accountId(parts[0]).date(parts[1]).build();
    }

    public YearMonth getDate() {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyyMM"));
    }
}
