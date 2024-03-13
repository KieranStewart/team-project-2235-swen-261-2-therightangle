package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Date class
 * 
 * Logan Nickerson
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
    public void testName() {
        // Setup
        String name = "Test";
        Need need = new Need(0, 0, name, null, null, null);

        String expected_name = "Dog throw rock";

        // Invoke
        need.setName(expected_name);

        // Analyze
        assertEquals(expected_name, need.getName());
    }
}