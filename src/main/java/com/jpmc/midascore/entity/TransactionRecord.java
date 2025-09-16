package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private Instant timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserRecord sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private UserRecord recipient;

    protected TransactionRecord() {}

    public TransactionRecord(UserRecord sender, UserRecord recipient, double amount, Instant timestamp) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    public Long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public UserRecord getSender() {
        return sender;
    }
    public UserRecord getRecipient() {
        return recipient;
    }
}
