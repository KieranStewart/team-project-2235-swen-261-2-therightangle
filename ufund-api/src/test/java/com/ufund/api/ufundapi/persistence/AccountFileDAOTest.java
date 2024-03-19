package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Account File DAO class
 * 
 * @author Logan Nickerson
 * Adapted from SWEN Faculty
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {
    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[3];
        testAccounts[0] = new Account("admin", "admin", "admin@gmail.com", true);
        testAccounts[1] = new Account("Real User", "Real Password", "real@gmail.com", false);
        testAccounts[2] = new Account("Another Real User", "null", "a@gmail.com", false);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the account array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Account[].class))
                .thenReturn(testAccounts);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testCreateAccount() {
        // Setup
        Account account = new Account("New Name", null, null, false);

        // Invoke
        boolean createAccountSuccess = assertDoesNotThrow(() -> accountFileDAO.createAccount(account),
                                "Unexpected exception thrown");
        
        // Analyze
        assertTrue(createAccountSuccess);
        Account actual = accountFileDAO.getAccount(account.getName());
        assertEquals(actual.getName(), account.getName());
        assertEquals(actual.getPassword(), account.getPassword());
        assertEquals(actual.getEmail(), account.getEmail());
        assertEquals(actual.getIsAdmin(), account.getIsAdmin());
    }
    
    @Test 
    public void testCreateDuplicateAccount() {
        // Setup
        Account account = new Account("admin", null, null, false);

        // Invoke
        boolean createAccountSuccess = assertDoesNotThrow(() -> accountFileDAO.createAccount(account),
                                "Unexpected exception thrown");

        // Analyze
        assertFalse(createAccountSuccess);
    }

    @Test
    public void testSaveException() throws IOException {
        // Setup
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Account[].class));
        Account account = new Account("New User", null, null, false);

        // Invoke and Analyze
        assertThrows(IOException.class,
                        () -> accountFileDAO.createAccount(account),
                        "IOException not thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // Use a mock Object Mapper to simulate a deserialization error 
        // in readValue after calling Cupboard DAO's load method
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Account[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new AccountFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }

    /*
     * This test will only pass if the returned array is in the same arbitrary order as the test array
     * If it is to be ordered, the order should not be arbitrary, but dependent on a new field of Account (on design principle)
     * If it is to not be ordered, refactor this test so it does not require insertion order to be maintained
     */
    @Test
    public void testGetAccounts()
    {
        // Setup
        Account[] actualAccounts;

        // Invoke
        actualAccounts = accountFileDAO.getAccounts();

        // Analyze
        Comparator<Account> comparator = new Comparator<Account>() { 
            /** 
             * HashMap doesn't maintain insertion order, and we don't intend to implement a natural order of Accounts from a design
             * perspective.  Use this to sort accounts so we can test that the getAccounts method actually works.
             */
            @Override
            public int compare(Account o1, Account o2) {
                return o1.getName().compareTo(o2.getName());
            }};
        Arrays.sort(actualAccounts, comparator);
        Arrays.sort(testAccounts, comparator);
        for(int i = 0; i < actualAccounts.length; i++) {
            assertEquals(testAccounts[i].toString(), actualAccounts[i].toString());
        }
    }

    @Test
    public void testGetAccountNotFound() {
        // Invoke
        Account account = accountFileDAO.getAccount("fake account");

        // Analyze
        assertNull(account);
    }
    
    @Test
    public void testUpdateAccount() {
        // Setup
        Account account = new Account("Real User", "new pwd", "new email@gmail.com", true);

        // Invoke
        Account result = assertDoesNotThrow(() -> accountFileDAO.updateAccount(account),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Account actual = accountFileDAO.getAccount(account.getName());
        assertEquals(account, actual);
    }

    @Test
    public void testUpdateAccountNotFound() {
        // Setup
        Account account = new Account("Fake User", "Fake New Password", null, false);

        // Invoke
        Account result = assertDoesNotThrow(() -> accountFileDAO.updateAccount(account),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteAccount() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount("Real User"),
                            "Unexpected exception thrown");

        // Analyze
        assertTrue(result);
        // We check the internal hash map size against the length
        // of the test accounts array - 1 (because of the delete)
        // Because the accounts attribute of AccountFileDAO is package private we can access it directly
        // I don't know why they are encouraging this inappropriate and violent behavior (this should be private, we should test it a different way)
        assertEquals(testAccounts.length - 1, accountFileDAO.accounts.size());
    }

    @Test
    public void testDeleteAccountNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount("fake account"),
                                                "Unexpected exception thrown");

        // Analyze
        assertFalse(result);
        assertEquals(testAccounts.length, accountFileDAO.accounts.size());
    }
}
