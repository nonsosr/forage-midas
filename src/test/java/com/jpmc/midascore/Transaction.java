package com.jpmc.midascore;

public class Transaction {
    private String id;
    private double amount;

    @Override
    public String toString() {
        return "Transaction{id='" + id + "', amount=" + amount + "}";
    }
}
