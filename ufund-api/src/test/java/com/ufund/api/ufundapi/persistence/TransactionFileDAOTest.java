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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Date;
import com.ufund.api.ufundapi.model.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Transaction File DAO class
 * 
 * Adapted from SWEN Faculty
 * @author Logan Nickerson
 */
@Tag("Persistence-tier")
public class TransactionFileDAOTest {

    TransactionFileDAO transactionFileDAO;
    Transaction[] testTransactions;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupTransactionFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testTransactions = new Transaction[3];
        testTransactions[0] = new Transaction(1, new Date(1, 1, 2000), "real need", 1);
        testTransactions[1] = new Transaction(2, new Date(1, 1, 2001), "real need", 2);
        testTransactions[2] = new Transaction(3, new Date(1, 2, 2001), "other need", 3);;

        // Array is usually sorted on load
        Arrays.sort(testTransactions);

        // Pretend that the file gives this array when read
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Transaction[].class))
                .thenReturn(testTransactions);
        transactionFileDAO = new TransactionFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testCreateFirstTransaction() {
        // Setup
        String newNeedName = "new need";
        Transaction transactionFromClient = new Transaction(10, null, newNeedName, 0);

        // Invoke
        Transaction createdTransaction = assertDoesNotThrow(() -> transactionFileDAO.createTransaction(transactionFromClient),
                                "Unexpected exception thrown");
        
        // Analyze
        assertNotNull(createdTransaction);
        Transaction[] actual = transactionFileDAO.getTransactions(newNeedName);
        assertEquals(actual[0].getNeedName(), transactionFromClient.getNeedName());
        assertEquals(actual[0].getAmount(), transactionFromClient.getAmount());
    }

    @Test
    public void testCreateThirdTransaction() {
        // Setup
        String validNeedName = "real need";
        Transaction transactionFromClient = new Transaction(10, null, validNeedName, 0);

        // Invoke
        Transaction createdTransaction = assertDoesNotThrow(() -> transactionFileDAO.createTransaction(transactionFromClient),
                                "Unexpected exception thrown");
        
        // Analyze
        assertNotNull(createdTransaction);
        Transaction[] actual = transactionFileDAO.getTransactions(validNeedName);
        assertEquals(actual[0].getNeedName(), transactionFromClient.getNeedName());
        assertEquals(actual[0].getAmount(), transactionFromClient.getAmount());
    }
    
    
    @Test 
    public void testCreateTransactionForInvalidNeed() {
        // Setup
        Transaction transaction = new Transaction(0, null, "", 0);

        // Invoke
        Transaction createdTransaction = assertDoesNotThrow(() -> transactionFileDAO.createTransaction(transaction),
                                "Unexpected exception thrown");

        // Analyze
        assertNull(createdTransaction);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Transaction[].class));

        Transaction transaction = new Transaction(0, null, "doesn't matter", 0);

        assertThrows(IOException.class,
                        () -> transactionFileDAO.createTransaction(transaction),
                        "IOException not thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // Pretend an error happens in readValue (after calling load)
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"), Transaction[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new TransactionFileDAO("doesnt_matter.txt", mockObjectMapper),
                        "IOException not thrown");
    }

    @Test
    public void testGetAllTransactions()
    {
        // Setup
        Transaction[] actualTransactions;

        // Invoke
        actualTransactions = transactionFileDAO.getAllTransactions();

        // Analyze
        for(int i = 0; i < actualTransactions.length; i++) {
            assertEquals(testTransactions[i].toString(), actualTransactions[i].toString());
        }
    }

    @Test
    public void testGetTransactionsNeedNotFound() {
        // Invoke
        Transaction[] transactions = transactionFileDAO.getTransactions("fake need name");

        // Analyze
        assertEquals(0, transactions.length);
    }

    @Test
    public void testGetTransactionsNeedIsFound() {
        // Invoke
        Transaction[] actualTransactions = transactionFileDAO.getTransactions("real need");

        // Analyze
        assertEquals(2, actualTransactions.length);
        assertEquals(testTransactions[1], actualTransactions[0]);
    }

    @Test
    public void testDeleteTransactions() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> transactionFileDAO.deleteTransactionHistory("other need"),
                            "Unexpected exception thrown");
        Transaction[] emptyArray = transactionFileDAO.getTransactions("other need");

        // Analzye
        assertTrue(result);
        assertEquals(0, emptyArray.length);
    }

    @Test
    public void testDeleteNeedNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> transactionFileDAO.deleteTransactionHistory("fake need"),
                                                "Unexpected exception thrown");
        Transaction[] actualTransactions = transactionFileDAO.getAllTransactions();

        // Analyze
        assertFalse(result);
        assertEquals(testTransactions.length, actualTransactions.length);
    }
    
}
