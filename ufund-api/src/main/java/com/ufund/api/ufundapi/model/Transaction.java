package com.ufund.api.ufundapi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores a transaction from a donator to some need
 * @author Logan Nickerson
 */
public class Transaction {

    private static final String STRING_FORMAT = "Transaction of %d at %s";
    @JsonProperty("amount") private double amount;
    @JsonProperty("timestamp") private Date timestamp;

    public Transaction(@JsonProperty("amount") double amount, 
    @JsonProperty("timestamp") Date timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
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

    protected static String getStringFormat() {
        return STRING_FORMAT;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, amount, timestamp.toString());
    }

}
