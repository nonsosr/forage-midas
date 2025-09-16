package com.jpmc.midascore.foundation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String senderId;
    private String recipientId;
    private BigDecimal amount;


    public String getSenderId() {
        return senderId;
    }


    public String getRecipientId() {
        return recipientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction {senderId=" + senderId + ", recipientId=" + recipientId + ", amount=" + amount + "}";
    }
}
