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
        int expected_goal = 100;
        int expected_progress = 0;
        String expected_name = "Unlimited Games";
        String expected_desc = "but no games";
        Date deadline = new Date(1, 1, 2040);
        Date[] volunteerDates = {
            new Date(10, 10, 2030)
        };


        // Invoke
        Need need = new Need(expected_goal, expected_progress, expected_name, expected_desc, volunteerDates, deadline, "donation");

        // Analyze
        assertEquals(expected_goal, need.getGoal());
        assertEquals(expected_name, need.getName());
        assertEquals(expected_desc, need.getDescription());
        assertEquals(expected_progress, need.getProgress());
        assertEquals(deadline, need.getDeadline());
        assertEquals(volunteerDates, need.getVolunteerDates());
    }

    @Test
    public void testName() {
        // Setup
        String name = "Test";
        Need need = new Need(0, 0, name, null, null, null, "donation");

        String expected_name = "Dog throw rock";

        // Invoke
        need.setName(expected_name);

        // Analyze
        assertEquals(expected_name, need.getName());
    }
}