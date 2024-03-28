package com.ufund.api.ufundapi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores a transaction from a donator to some need
 * @author Logan Nickerson
 */
public class Transaction implements Comparable<Transaction> {

    private static final String STRING_FORMAT = "Transaction of %f at %s for %s";
    @JsonProperty("amount") private double amount;
    @JsonProperty("timestamp") private Date timestamp;
    @JsonProperty("needName") String needName;

    public Transaction(@JsonProperty("amount") double amount, 
    @JsonProperty("timestamp") Date timestamp,
    @JsonProperty("needName") String needName) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.needName = needName;
        if(this.timestamp == null) {
            this.timestamp = new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        }
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getNeedName() {
        return needName;
    }

    protected static String getStringFormat() {
        return STRING_FORMAT;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, amount, timestamp.toString(), needName);
    }

    public void setNeedName(String name) {
        this.needName = name;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.timestamp.compareTo(o.timestamp);
    }

}
