package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        int expectedID = 1;

        // Invoke
        Transaction transaction = new Transaction(expectedAmount, expectedTimestamp, expectedNeedName, expectedID);

        // Analyze
        assertEquals(expectedAmount, transaction.getAmount());
        assertEquals(expectedTimestamp, transaction.getTimestamp());
        assertEquals(expectedNeedName, transaction.getNeedName());
    }

    @Test
    public void testToString() {
        // Setup
        double expectedAmount = 110.99;
        int expectedID = 1;
        Date expectedTimestamp = new Date(1, 1, 2024);
        String expectedNeedName = "hi";
        Transaction transaction = new Transaction(expectedAmount, expectedTimestamp, expectedNeedName, expectedID);

        // Invoke
        String expected = String.format(Transaction.getStringFormat(), expectedAmount, expectedTimestamp.toString(), expectedNeedName);

        // Analyze
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void testLocalTime() {
        // Setup & Invoke
        Transaction transaction = new Transaction(10.1, "");

        // Analyze
        assertNotNull(transaction.getTimestamp());
    }

    @Test 
    public void testInsertionOrder() {
        // Setup
        Transaction t1 = new Transaction(10.1, "");
        Transaction t2 = new Transaction(10.1, null, "", 10);
        Transaction t3 = new Transaction(10.1, "");

        // Invoke
        boolean firstIsFirst = t1.compareTo(t2) < 0;
        boolean lastIsNewest = t3.compareTo(t2) == 1;

        // Analyze
        assertTrue(firstIsFirst);
        assertTrue(lastIsNewest);
        assertEquals(0, t1.compareTo(t1));
        assertEquals(0, t2.compareTo(t2));
    }
}