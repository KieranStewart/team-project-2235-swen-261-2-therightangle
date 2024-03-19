package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.AccountDAO;
import com.ufund.api.ufundapi.model.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Account Controller class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Controller-tier")
public class AccountControllerTest {
    private AccountController accountController;
    private AccountDAO mockAccountDAO;

    /**
     * Before each test, create a new AccountController object and inject
     * a mock Account DAO
     */
    @BeforeEach
    public void setupAccountController() {
        mockAccountDAO = mock(AccountDAO.class);
        accountController = new AccountController(mockAccountDAO);
    }

    @Test
    public void testCreateAccount() throws IOException {
        // Setup
        Account account = new Account("new acccount", "password", "email@gmail.com", false);
        // simulate success
        when(mockAccountDAO.createAccount(account)).thenReturn(true);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(account,response.getBody());
    }

    @Test
    public void testCreateAccountFailed() throws IOException {
        // Setup
        Account account = new Account("admin", "fake", "fakeadmin@gmail.com", false);
        // when createAccount is called, return false simulating failure
        when(mockAccountDAO.createAccount(account)).thenReturn(false);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateAccountHandleException() throws IOException {
        // Setup
        Account account = new Account(null, null, null, false);

        doThrow(new IOException()).when(mockAccountDAO).createAccount(account); // Stimulate an exception

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchAccounts() throws IOException {
        // Setup
        String searchAccount = "account";
        Account[] accounts = new Account[3];
        accounts[0] = new Account(searchAccount, "a", "a@gmail.com", false);
        accounts[1] = new Account("b", "b", "b@gmail.com", false);
        accounts[2] = new Account("c", "c", "c@gmail.com", false);
    
        when(mockAccountDAO.getAccounts()).thenReturn(accounts);

        //Invoke
        ResponseEntity<Account[]> response = accountController.searchAccounts(searchAccount);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertArrayEquals(accounts,response.getBody());
    }

    @Test
    public void testSearchAccountsFailed() throws IOException {
        // Setup
        String searchAccount = "nonexistent account";
        Account[] accounts = null;

        when(mockAccountDAO.getAccounts()).thenReturn(accounts);

        // Invoke
        ResponseEntity<Account[]> response = accountController.searchAccounts(searchAccount);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testSearchAccountsHandleException() throws IOException {
        // Setup
        String searchAccount = "account";

        doThrow(new IOException()).when(mockAccountDAO).getAccounts();

        // Invoke
        ResponseEntity<Account[]> response = accountController.searchAccounts(searchAccount);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchAccountsNotFound() throws IOException {
        // Setup
        String searchQuery = "Fake User";
        Account[] accounts = new Account[3];
        accounts[0] = new Account("a", "a", "a@gmail.com", false);
        accounts[1] = new Account("b", "b", "b@gmail.com", false);
        accounts[2] = new Account("c", "c", "c@gmail.com", false);

        when(mockAccountDAO.getAccounts()).thenReturn(accounts);

        // Invoke
        ResponseEntity<Account[]> response = accountController.searchAccounts(searchQuery);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAccounts() throws IOException
    {
        // Setup
        Account[] expectedAccounts = mockAccountDAO.getAccounts();
        ResponseEntity<Account[]> expected = new ResponseEntity<Account[]>(expectedAccounts, HttpStatus.OK);

        // Invoke
        ResponseEntity<Account[]> actual = accountController.getAccounts();

        // Check
        assertEquals(expected, actual);
    }


    
    @Test
    public void testUpdateAccount() throws IOException { // updateAccount may throw IOException
        // Setup
        Account account = new Account("Real user", null, null, false);
        when(mockAccountDAO.updateAccount(account)).thenReturn(account);
        ResponseEntity<Account> response = accountController.updateAccount(account);
        account.setName("Real User (allegedly)");

        // Invoke
        response = accountController.updateAccount(account);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testUpdateAccountFailed() throws IOException { // updateAccount may throw IOException
        // Setup
        Account account = new Account("Real user", null, null, false);
        when(mockAccountDAO.updateAccount(account)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = accountController.updateAccount(account);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateAccountHandleException() throws IOException { // updateAccount may throw IOException
        // Setup
        Account account = new Account("Real user", null, null, false);
        doThrow(new IOException()).when(mockAccountDAO).updateAccount(account);

        // Invoke
        ResponseEntity<Account> response = accountController.updateAccount(account);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAccount() throws IOException {  // getAccount may throw IOException
        // Setup
        Account account = new Account("Real user", null, null, false);
        when(mockAccountDAO.getAccount(account.getName())).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.getAccount(account.getName());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testGetAccountNotFound() throws Exception { // createAccount may throw IOException
        // Setup
        String accountName = "name";
        // Simulate no account found
        when(mockAccountDAO.getAccount(accountName)).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = accountController.getAccount(accountName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAccountHandleException() throws Exception { // createAccount may throw IOException
        // Setup
        String accountName = "account";
        doThrow(new IOException()).when(mockAccountDAO).getAccount(accountName); // throw an IOException

        // Invoke
        ResponseEntity<Account> response = accountController.getAccount(accountName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetAccountsHandleException() throws IOException { 
        // Setup
        doThrow(new IOException()).when(mockAccountDAO).getAccounts();

        // Invoke
        ResponseEntity<Account[]> response = accountController.getAccounts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteAccount() throws IOException { // deleteAccount may throw IOException
        // Setup
        String accountName = "account";
        // pretend: successful deletion
        when(mockAccountDAO.deleteAccount(accountName)).thenReturn(true);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(accountName);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteAccountNotFound() throws IOException { // deleteAccount may throw IOException
        // Setup
        String accountName = "account";
        // pretend: fail to delete
        when(mockAccountDAO.deleteAccount(accountName)).thenReturn(false);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(accountName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteAccountHandleException() throws IOException { // deleteAccount may throw IOException
        // Setup
        String accountName = "account";
        doThrow(new IOException()).when(mockAccountDAO).deleteAccount(accountName);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(accountName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


}
