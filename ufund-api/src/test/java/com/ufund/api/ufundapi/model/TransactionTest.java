package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        String expectedNeedName = "hi";

        // Invoke
        Transaction transaction = new Transaction(expectedAmount, expectedTimestamp, expectedNeedName);

        // Analyze
        assertEquals(expectedAmount, transaction.getAmount());
        assertEquals(expectedTimestamp, transaction.getTimestamp());
        assertEquals(expectedNeedName, transaction.getNeedName());
    }

    // @Test
    // public void testSetters() {
        
    // }

    @Test
    public void testToString() {
        // Setup
        double expectedAmount = 110.99;
        Date expectedTimestamp = new Date(1, 1, 2024);
        String expectedNeedName = "hi";
        Transaction transaction = new Transaction(expectedAmount, expectedTimestamp, expectedNeedName);

        // Invoke
        String expected = String.format(Transaction.getStringFormat(), expectedAmount, expectedTimestamp.toString(), expectedNeedName);

        // Analyze
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void testLocalTime() {
        // Setup & Invoke
        Transaction transaction = new Transaction(10.1, null, "");

        // Analyze
        assertNotNull(transaction.getTimestamp());
    }
}