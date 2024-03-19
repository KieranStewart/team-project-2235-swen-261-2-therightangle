package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Account class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Model-tier")
public class AccountTest {
    @Test
    public void testConstructor() {
        // Setup
        String expectedName = "a";
        String expectedPassword = "a";
        String expectedEmail = "a@gmail.com";
        boolean expectedAdminStatus = false;

        // Invoke
        Account account = new Account(expectedName, expectedPassword, expectedEmail, expectedAdminStatus);

        // Analyze
        assertEquals(expectedName, account.getName());
        assertEquals(expectedPassword, account.getPassword());
        assertEquals(expectedEmail, account.getEmail());
        assertEquals(expectedAdminStatus, account.getIsAdmin());
    }

    @Test
    public void testSetters() {
        // Setup
        String expectedName = "a";
        String expectedPassword = "a";
        String expectedEmail = "a@gmail.com";
        boolean expectedAdminStatus = false;
        Account account = new Account("b", "b", "b@g.rit.edu", true);

        // Invoke
        account.setName(expectedName);
        account.setPassword(expectedPassword);
        account.setEmail(expectedEmail);
        account.setIsAdmin(expectedAdminStatus);

        // Analyze
        assertEquals(expectedName, account.getName());
        assertEquals(expectedPassword, account.getPassword());
        assertEquals(expectedEmail, account.getEmail());
        assertEquals(expectedAdminStatus, account.getIsAdmin());
    }

    @Test
    public void testToString() {
        // Setup
        String expectedName = "a";
        String expectedPassword = "a";
        String expectedEmail = "a@gmail.com";
        boolean expectedAdminStatus = false;
        Account account = new Account(expectedName, expectedPassword, expectedEmail, expectedAdminStatus);
        String expected = String.format(Account.getStringFormat(), expectedName, expectedPassword, expectedEmail, expectedAdminStatus);

        // Invoke
        String actual = account.toString();

        // Analyze
        assertEquals(expected, actual);
    }
}