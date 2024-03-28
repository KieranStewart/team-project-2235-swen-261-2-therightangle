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
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.NeedType;
import com.ufund.api.ufundapi.model.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Cupboard File DAO class
 * 
 * Adapted from SWEN Faculty
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
        testTransactions[0] = new Transaction(1, null, "real need");
        testTransactions[1] = new Transaction(2, null, "real need");
        testTransactions[2] = new Transaction(3, null, "other need");

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
        Transaction transaction = new Transaction(10, null, newNeedName);

        // Invoke
        boolean createTransactionSuccess = assertDoesNotThrow(() -> transactionFileDAO.createTransaction(transaction),
                                "Unexpected exception thrown");
        
        // Analyze
        assertTrue(createTransactionSuccess);
        Transaction[] actual = transactionFileDAO.getTransactions(newNeedName);
        assertEquals(actual[0].getNeedName(), transaction.getNeedName());
        assertEquals(actual[0].getAmount(), transaction.getAmount());
    }

    @Test
    public void testCreateThirdTransaction() {
        // Setup
        String validNeedName = "real need";
        Transaction transaction = new Transaction(10, null, validNeedName);

        // Invoke
        boolean createTransactionSuccess = assertDoesNotThrow(() -> transactionFileDAO.createTransaction(transaction),
                                "Unexpected exception thrown");
        
        // Analyze
        assertTrue(createTransactionSuccess);
        Transaction[] actual = transactionFileDAO.getTransactions(validNeedName);
        assertEquals(actual[0].getNeedName(), transaction.getNeedName());
        assertEquals(actual[0].getAmount(), transaction.getAmount());
    }
    
    
    @Test 
    public void testCreateTransactionForInvalidNeed() {
        // Setup
        Transaction transaction = new Transaction(0, null, "");

        // Invoke
        boolean createTransactionSuccess = assertDoesNotThrow(() -> transactionFileDAO.createTransaction(transaction),
                                "Unexpected exception thrown");

        // Analyze
        assertFalse(createTransactionSuccess);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Transaction[].class));

        Transaction transaction = new Transaction(0, null, null);

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

    /*
     * This test will only pass if the returned array is in the same arbitrary order as the test array
     * If it is to be ordered, the order should not be arbitrary, but dependent on a new field of Need (on design principle)
     * If it is to not be ordered, refactor this test so it does not require insertion order to be maintained
     */
    @Test
    public void testGetNeeds()
    {
        // Setup
        Need[] actualNeeds;

        // Invoke
        actualNeeds = transactionFileDAO.getNeeds();

        // Analyze
        Comparator<Need> comparator = new Comparator<Need>() { 
            /** 
             * HashMap doesn't maintain insertion order, and we don't intend to implement a natural order of Needs from a design
             * perspective.  Use this to sort needs so we can test that the getNeeds method actually works.
             */
            @Override
            public int compare(Need o1, Need o2) {
                return o1.getName().compareTo(o2.getName());
            }};
        Arrays.sort(actualNeeds, comparator);
        Arrays.sort(testTransactions, comparator);
        for(int i = 0; i < actualNeeds.length; i++) {
            assertEquals(testTransactions[i].toString(), actualNeeds[i].toString());
        }
    }

    @Test
    public void testGetNeedNotFound() {
        // Invoke
        Need need = cupboardFileDAO.getNeed("fake need");

        // Analyze
        assertNull(need);
    }
    
    @Test
    public void testUpdateNeed() {
        // Setup
        Need need = new Need(0, 0, "update me", "updated", null, null, NeedType.DONATION);

        // Invoke
        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Need actual = cupboardFileDAO.getNeed(need.getName());
        assertEquals(actual,need);
    }

        @Test
    public void testUpdateNeedNotFound() {
        // Setup
        Need need = new Need(0, 0, "unavaliable",null , null, null, NeedType.DONATION);

        // Invoke
        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteNeed() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cupboardFileDAO.deleteNeed("Thing One"),
                            "Unexpected exception thrown");

        // Analzye
        assertTrue(result);
        // We check the internal hash map size against the length
        // of the test needs array - 1 (because of the delete)
        // Because the needs attribute of TransactionFileDAO is package private we can access it directly
        // I don't know why they are encouraging this inappropriate and violent behavior (this should be private, we should test it a different way)
        assertEquals(testTransactions.length - 1, cupboardFileDAO.needs.size());
    }

    @Test
    public void testDeleteNeedNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cupboardFileDAO.deleteNeed("fake need"),
                                                "Unexpected exception thrown");

        // Analyze
        assertFalse(result);
        assertEquals(testTransactions.length, cupboardFileDAO.needs.size());
    }
    
}
