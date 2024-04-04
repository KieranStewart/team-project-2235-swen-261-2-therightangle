package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the VolunteerDate class
 * 
 * @author Logan Nickerson
 */
@Tag("Model-tier")
public class VolunteerDateTest {
    @Test
    public void testConstructor() {
        // Setup
        int expectedDay = 12;
        int expectedMonth = 3;
        int expectedYear = 2024;
        boolean expectedFill = false;

        // Invoke
        VolunteerDate date = new VolunteerDate(expectedDay, expectedMonth, expectedYear, expectedFill);

        // Analyze
        assertEquals(expectedDay, date.getDay());
        assertEquals(expectedMonth, date.getMonth());
        assertEquals(expectedYear, date.getYear());
        assertEquals(expectedFill, date.getFilled());
    }

    @Test
    public void testToString() {
        // Setup
        int expectedDay = 12;
        int expectedMonth = 3;
        int expectedYear = 2024;
        boolean expectedFill = false;
        VolunteerDate date = new VolunteerDate(expectedDay, expectedMonth, expectedYear, expectedFill);

        // Invoke
        String actual = String.format("%d/%d/%d:%s", expectedMonth, expectedDay, expectedYear, expectedFill?"filled":"empty");

        // Analyze
        assertEquals(date.toString(), actual);
    }

    @Test
    public void testToStringFilled() {
        // Setup
        int expectedDay = 12;
        int expectedMonth = 3;
        int expectedYear = 2024;
        boolean expectedFill = true;
        VolunteerDate date = new VolunteerDate(expectedDay, expectedMonth, expectedYear, expectedFill);

        // Invoke
        String actual = String.format("%d/%d/%d:%s", expectedMonth, expectedDay, expectedYear, expectedFill?"filled":"empty");

        // Analyze
        assertEquals(date.toString(), actual);
    }
}