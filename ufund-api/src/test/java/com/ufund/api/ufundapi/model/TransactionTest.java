package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Transaction class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Model-tier")
public class TransactionTest {
    @Test
    public void testConstructor() {
        // Setup
        double expectedAmount = 110.99;
        Date expectedTimestamp = new Date(1, 1, 2024);

        // Invoke
        Transaction transaction = new Transaction(expectedAmount, expectedTimestamp);

        // Analyze
        assertEquals(expectedAmount, transaction.getAmount());
        assertEquals(expectedTimestamp, transaction.getTimestamp());
    }

    // @Test
    // public void testSetters() {
        
    // }

    @Test
    public void testToString() {
        // Setup
        double expectedAmount = 110.99;
        Date expectedTimestamp = new Date(1, 1, 2024);
        Transaction transaction = new Transaction(expectedAmount, expectedTimestamp);

        // Invoke
        String expected = String.format(Transaction.getStringFormat(), expectedAmount, expectedTimestamp.toString());

        // Analyze
        assertEquals(expected, transaction.toString());
    }
}