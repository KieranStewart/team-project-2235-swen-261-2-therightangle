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
        VolunteerDate[] expectedVolunteerDates = {
            new VolunteerDate(10, 10, 2030, false)
        };
        NeedType expectedType = NeedType.DONATION;

        // Invoke
        Need need = new Need(expectedGoal, expectedProgress, expectedName, expectedDesc, expectedVolunteerDates, expectedDeadline, NeedType.DONATION);

        // Analyze
        assertEquals(expectedGoal, need.getGoal());
        assertEquals(expectedName, need.getName());
        assertEquals(expectedDesc, need.getDescription());
        assertEquals(expectedProgress, need.getProgress());
        assertEquals(expectedDeadline, need.getDeadline());
        assertEquals(expectedVolunteerDates, need.getVolunteerDates());
        assertEquals(expectedType, need.getType());
    }

    @Test
    public void testSetters() {
        // Setup
        int expectedGoal = 100;
        int expectedProgress = 0;
        String expectedName = "test name";
        String expectedDesc = "test desc";
        Date expectedDeadline = new Date(1, 1, 2040);
        VolunteerDate[] expectedVolunteerDates = {
            new VolunteerDate(10, 10, 2030, false)
        };
        NeedType expectedType = NeedType.DONATION;
        Need need = new Need(-1, -1, "old name", "old description", null, null, NeedType.VOLUNTEER);

        // Invoke
        need.setName(expectedName);
        need.setDeadline(expectedDeadline);
        need.setDescription(expectedDesc);
        need.setGoal(expectedGoal);
        need.setVolunteerDates(expectedVolunteerDates);
        need.setProgress(expectedProgress);
        need.setType(expectedType);

        // Analyze
        assertEquals(expectedGoal, need.getGoal());
        assertEquals(expectedName, need.getName());
        assertEquals(expectedDesc, need.getDescription());
        assertEquals(expectedProgress, need.getProgress());
        assertEquals(expectedDeadline, need.getDeadline());
        assertEquals(expectedVolunteerDates, need.getVolunteerDates());
        assertEquals(expectedType, need.getType());
    }

    @Test
    public void testToString() {
        // Setup
        double expectedGoal = 100;
        double expectedProgress = 0;
        String expectedName = "Unlimited Games";
        String expectedDesc = "but no games";
        Date expectedDeadline = new Date(1, 1, 2040);
        VolunteerDate[] expectedVolunteerDates = {
            new VolunteerDate(10, 10, 2030, false)
        };
        Need need = new Need(expectedGoal, expectedProgress, expectedName, expectedDesc, expectedVolunteerDates, expectedDeadline, NeedType.DONATION);

        // Invoke
        String expected = String.format(Need.getStringFormat(), expectedGoal, expectedProgress, expectedName, expectedDesc, Arrays.toString(expectedVolunteerDates), expectedDeadline, NeedType.DONATION, Arrays.toString(new com.ufund.api.ufundapi.model.Tag[0]));

        // Analyze
        assertEquals(expected, need.toString());
    }
}