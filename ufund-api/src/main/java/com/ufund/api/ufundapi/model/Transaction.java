package com.ufund.api.ufundapi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores a transaction from a donator to some need
 * @author Logan Nickerson
 */
public class Transaction implements Comparable<Transaction> {

    private static final String STRING_FORMAT = "Transaction of %f at %s for %s";
    private static int nextId = 0;
    @JsonProperty("amount") private double amount;
    @JsonProperty("timestamp") private Date timestamp;
    @JsonProperty("needName") private String needName;
    /**
     * Internal insertion order identifier.
     * This should never be exposed to the client.
     */
    @JsonProperty("id") private int id;

    /**
     * This constructor should be used to construct Transactions from
     * storage into the cache.  All Transactions must be loaded
     * before trying to save any more Transactions.
     * @param amount
     * @param timestamp
     * @param needName
     * @param id
     */
    public Transaction(@JsonProperty("amount") double amount, 
    @JsonProperty("timestamp") Date timestamp,
    @JsonProperty("needName") String needName,
    @JsonProperty("id") int id) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.needName = needName;
        this.id = id;
        Transaction.updateNextID(id);
    }

    /**
     * Constructor for new Transactions coming from the client.
     * The client does not get to choose the timestamp or id,
     * even if they try to put them in the request.
     * 
     * @param amount
     * @param needName
     */
    public Transaction(double amount, String needName) {
        this.amount = amount;
        this.needName = needName;
        this.timestamp = new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        this.id = Transaction.getNextID();
    }

    /**
     * Gets the next unique ID for a transaction.
     * These are assigned and stored to maintain
     * insertion order (which is chronological order). 
     * @return the next ID
     */
    private static int getNextID() {
        return nextId++; // returns nextId, then increments.
    }

    /**
     * Called when reading existing Transactions.
     * This makes sure that the next id for a new
     * Transaction will be unique and large.
     * @param existingID
     */
    private static void updateNextID(int existingID) {
        if(existingID >= nextId) {
            nextId = existingID + 1;
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

    /**
     * @return If this transaction has its internal order set
     */
    public boolean isInitialized() {
        return id != 0;
    }

    /**
     * Checks which Transaction is newest
     * @param other
     * @return Negative if the other is newer
     * Positive if this is newer
     * 0 if the Transactions have the same ID (same Transaction)
     */
    @Override
    public int compareTo(Transaction other) {
        return this.id - other.id;
    }

}
