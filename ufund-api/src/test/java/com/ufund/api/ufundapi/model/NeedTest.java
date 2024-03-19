package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Need class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Model-tier")
public class NeedTest {
    @Test
    public void testConstructor() {
        // Setup
        int expectedGoal = 100;
        int expectedProgress = 0;
        String expectedName = "Unlimited Games";
        String expectedDesc = "but no games";
        Date expectedDeadline = new Date(1, 1, 2040);
        Date[] expectedVolunteerDates = {
            new Date(10, 10, 2030)
        };

        // Invoke
        Need need = new Need(expectedGoal, expectedProgress, expectedName, expectedDesc, expectedVolunteerDates, expectedDeadline, "");

        // Analyze
        assertEquals(expectedGoal, need.getGoal());
        assertEquals(expectedName, need.getName());
        assertEquals(expectedDesc, need.getDescription());
        assertEquals(expectedProgress, need.getProgress());
        assertEquals(expectedDeadline, need.getDeadline());
        assertEquals(expectedVolunteerDates, need.getVolunteerDates());
    }

    @Test
    public void testSetters() {
        // Setup
        int expectedGoal = 100;
        int expectedProgress = 0;
        String expectedName = "test name";
        String expectedDesc = "test desc";
        Date expectedDeadline = new Date(1, 1, 2040);
        Date[] expectedVolunteerDates = {
            new Date(10, 10, 2030)
        };
        Need need = new Need(-1, -1, "old name", "old description", null, null, "");

        // Invoke
        need.setName(expectedName);
        need.setDeadline(expectedDeadline);
        need.setDescription(expectedDesc);
        need.setGoal(expectedGoal);
        need.setVolunteerDates(expectedVolunteerDates);
        need.setProgress(expectedProgress);

        // Analyze
        assertEquals(expectedGoal, need.getGoal());
        assertEquals(expectedName, need.getName());
        assertEquals(expectedDesc, need.getDescription());
        assertEquals(expectedProgress, need.getProgress());
        assertEquals(expectedDeadline, need.getDeadline());
        assertEquals(expectedVolunteerDates, need.getVolunteerDates());
    }

    @Test
    public void testToString() {
        // Setup
        int expectedGoal = 100;
        int expectedProgress = 0;
        String expectedName = "Unlimited Games";
        String expectedDesc = "but no games";
        Date expectedDeadline = new Date(1, 1, 2040);
        Date[] expectedVolunteerDates = {
            new Date(10, 10, 2030)
        };
        Need need = new Need(expectedGoal, expectedProgress, expectedName, expectedDesc, expectedVolunteerDates, expectedDeadline, "");

        // Invoke
        String expected = String.format(Need.getStringFormat(), expectedGoal, expectedProgress, expectedName, expectedDesc, Arrays.toString(expectedVolunteerDates), expectedDeadline);

        // Analyze
        assertEquals(expected, need.toString());
    }
}