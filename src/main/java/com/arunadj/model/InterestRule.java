package com.arunadj.model;

import com.arunadj.dto.InterestRuleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * THis acts as interest entity class.
 */
@Getter
@AllArgsConstructor
@Builder
public class InterestRule {
    private LocalDate date;
    private String ruleId;
    private double rate;

    public static InterestRule toEntity(InterestRuleDTO interestRuleDTO) {
        return InterestRule.builder().date(interestRuleDTO.getDate()).ruleId(interestRuleDTO.getRuleId()).rate(interestRuleDTO.getRate()).build();
    }
}
