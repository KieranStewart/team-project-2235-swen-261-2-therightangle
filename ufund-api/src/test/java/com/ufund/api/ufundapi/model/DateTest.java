package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Date class
 * 
 * @author Logan Nickerson
 */
@Tag("Model-tier")
public class DateTest {
    @Test
    public void testConstructor() {
        // Setup
        int expectedDay = 12;
        int expectedMonth = 3;
        int expectedYear = 2024;

        // Invoke
        Date date = new Date(expectedDay, expectedMonth, expectedYear);

        // Analyze
        assertEquals(expectedDay, date.getDay());
        assertEquals(expectedMonth, date.getMonth());
        assertEquals(expectedYear, date.getYear());
    }

    @Test
    public void testToString() {
        // Setup
        int expectedDay = 12;
        int expectedMonth = 3;
        int expectedYear = 2024;
        Date date = new Date(expectedDay, expectedMonth, expectedYear);

        // Invoke
        String actual = String.format("%d/%d/%d", expectedMonth, expectedDay, expectedYear);

        // Analyse
        assertEquals(date.toString(), actual);
    }
}