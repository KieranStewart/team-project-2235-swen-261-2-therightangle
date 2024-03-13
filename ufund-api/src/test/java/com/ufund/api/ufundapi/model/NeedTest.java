package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Date deadline = new Date(1, 1, 2040);
        Date[] volunteerDates = {
            new Date(10, 10, 2030)
        };


        // Invoke
        Need need = new Need(expectedGoal, expectedProgress, expectedName, expectedDesc, volunteerDates, deadline);

        // Analyze
        assertEquals(expectedGoal, need.getGoal());
        assertEquals(expectedName, need.getName());
        assertEquals(expectedDesc, need.getDescription());
        assertEquals(expectedProgress, need.getProgress());
        assertEquals(deadline, need.getDeadline());
        assertEquals(volunteerDates, need.getVolunteerDates());
    }

    @Test
    public void testName() {
        // Setup
        String name = "Test";
        Need need = new Need(0, 0, name, null, null, null);

        String expectedName = "Dog throw rock";

        // Invoke
        need.setName(expectedName);

        // Analyze
        assertEquals(expectedName, need.getName());
    }
}