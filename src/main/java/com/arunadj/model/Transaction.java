package com.arunadj.model;

import com.arunadj.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * This acts as transaction entity class.
 */
@Getter
@AllArgsConstructor
@Builder
public class Transaction {
    private LocalDate date;
    private String accountId;
    private String type; // D for deposit, W for withdrawal
    private double amount;
    private String txnId;

    public static Transaction toEntity(TransactionDTO transactionDTO) {
        return Transaction.builder()
                .date(transactionDTO.getDate())
                .accountId(transactionDTO.getAccountId())
                .type(transactionDTO.getType().toUpperCase())
                .amount(transactionDTO.getAmount())
                .build();
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
}
