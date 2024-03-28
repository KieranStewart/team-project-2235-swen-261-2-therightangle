package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.TransactionDAO;
import com.ufund.api.ufundapi.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Transaction Controller class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Controller-tier")
public class TransactionControllerTest {

    private TransactionController transactionController;
    private TransactionDAO mockTransactionDAO;

    /**
     * Before each test, create a new TransactionController object and inject
     * a mock Transaction DAO
     */
    @BeforeEach
    public void setupTransactionController() {
        mockTransactionDAO = mock(TransactionDAO.class);
        transactionController = new TransactionController(mockTransactionDAO);
    }

    @Test
    public void testCreateTransaction() throws IOException {
        // Setup
        Transaction transaction = new Transaction(0, null, "valid need");
        // simulate success
        when(mockTransactionDAO.createTransaction(transaction)).thenReturn(true);

        // Invoke
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transaction, response.getBody());
    }

    @Test
    public void testCreateTransactionFailed() throws IOException {
        // Setup
        Transaction transaction = new Transaction(0, null, "");
        // when createTransaction is called, return false simulating failure
        when(mockTransactionDAO.createTransaction(transaction)).thenReturn(false);

        // Invoke
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateTransactionHandleException() throws IOException {
        // Setup
        Transaction transaction = new Transaction(0, null, null);

        doThrow(new IOException()).when(mockTransactionDAO).createTransaction(transaction); // Stimulate an exception

        // Invoke
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllTransactions() throws IOException
    {
        // Setup
        Transaction[] expectedTransactions = mockTransactionDAO.getAllTransactions();
        ResponseEntity<Transaction[]> expected = new ResponseEntity<Transaction[]>(expectedTransactions, HttpStatus.OK);

        // Invoke
        ResponseEntity<Transaction[]> actual = transactionController.getAllTransactions();

        // Check
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTransactions() throws IOException {  // getTransactions may throw IOException
        // Setup
        String testNeedName = "test";
        Transaction[] transactionsForTestNeed = new Transaction[] {
            new Transaction(0, null, testNeedName),
            new Transaction(0, null, testNeedName)
        };
        when(mockTransactionDAO.getTransactions(testNeedName)).thenReturn(transactionsForTestNeed);

        // Invoke
        ResponseEntity<Transaction[]> response = transactionController.getTransactions(testNeedName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionsForTestNeed, response.getBody());
    }

    @Test
    public void testGetTransactionNotFound() throws Exception { // createTransaction may throw IOException
        // Setup
        String needName = "fake need";
        // Simulate no transaction found
        when(mockTransactionDAO.getTransactions(needName)).thenReturn(new Transaction[0]);

        // Invoke
        ResponseEntity<Transaction[]> response = transactionController.getTransactions(needName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetTransactionsHandleException() throws Exception { // createTransaction may throw IOException
        // Setup
        String needName = "doesn't matter";
        doThrow(new IOException()).when(mockTransactionDAO).getTransactions(needName); // throw an IOException

        // Invoke
        ResponseEntity<Transaction[]> response = transactionController.getTransactions(needName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllTransactionsHandleException() throws IOException { 
        // Setup
        doThrow(new IOException()).when(mockTransactionDAO).getAllTransactions();

        // Invoke
        ResponseEntity<Transaction[]> response = transactionController.getAllTransactions();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteTransactions() throws IOException { // deleteTransaction may throw IOException
        // Setup
        String needName = "test";
        // pretend: successful deletion
        when(mockTransactionDAO.deleteTransactionHistory(needName)).thenReturn(true);

        // Invoke
        ResponseEntity<Transaction> response = transactionController.deleteTransactionHistory(needName);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteTransactionsNeedNotFound() throws IOException { // deleteTransaction may throw IOException
        // Setup
        String needName = "fake need";
        // pretend: fail to delete
        when(mockTransactionDAO.deleteTransactionHistory(needName)).thenReturn(false);

        // Invoke
        ResponseEntity<Transaction> response = transactionController.deleteTransactionHistory(needName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteTransactionHistoryHandleException() throws IOException { // deleteTransaction may throw IOException
        // Setup
        String needName = "transaction";
        doThrow(new IOException()).when(mockTransactionDAO).deleteTransactionHistory(needName);

        // Invoke
        ResponseEntity<Transaction> response = transactionController.deleteTransactionHistory(needName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
