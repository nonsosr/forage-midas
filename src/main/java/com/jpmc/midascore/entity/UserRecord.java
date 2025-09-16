package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "sender")
    private Set<TransactionRecord> sentTransactions;

    @OneToMany(mappedBy = "recipient")
    private Set<TransactionRecord> receivedTransactions;

    public UserRecord() {}

    public UserRecord(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }


    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
