package com.ufund.api.ufundapi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores a transaction from a donator to some need
 * @author Logan Nickerson
 */
public class Transaction {

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


}
