package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Cupboard File DAO class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Persistence-tier")
public class CupboardFileDAOTest {
    CupboardFileDAO cupboardFileDAO;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupNeedFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testNeeds = new Need[3];
        testNeeds[0] = new Need(0, 0, "update me", "not update", null, null, "donation");
        testNeeds[1] = new Need(0, 0, "Thing One", null, null, null, "donation");
        testNeeds[2] = new Need(0, 0, "Thing Two", null, null, null, "donation");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the need array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Need[].class))
                .thenReturn(testNeeds);
        cupboardFileDAO = new CupboardFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testCreateNeed() {
        // Setup
        Need need = new Need(0, 0, "new name", null, null, null, "donation");

        // Invoke
        boolean result = assertDoesNotThrow(() -> cupboardFileDAO.createNeed(need),
                                "Unexpected exception thrown");

        // Analyze
        assertTrue(result);
        Need actual = cupboardFileDAO.getNeed(need.getName());
        assertEquals(actual.getName(), need.getName());
        assertEquals(actual.getDeadline(), need.getDeadline());
        assertEquals(actual.getGoal(), need.getGoal());
        assertEquals(actual.getDescription(), need.getDescription());
        assertEquals(actual.getProgress(), need.getProgress());
        assertEquals(actual.getVolunteerDates(), need.getVolunteerDates());
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Need[].class));

        Need need = new Need(0, 0, "New Need", null, null, null, "donation");

        assertThrows(IOException.class,
                        () -> cupboardFileDAO.createNeed(need),
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
                .readValue(new File("doesnt_matter.txt"),Need[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new CupboardFileDAO("doesnt_matter.txt",mockObjectMapper),
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
        actualNeeds = cupboardFileDAO.getNeeds();

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
        Arrays.sort(testNeeds, comparator);
        for(int i = 0; i < actualNeeds.length; i++) {
            assertEquals(testNeeds[i].toString(), actualNeeds[i].toString());
        }
    }
    
    @Test
    public void testUpdateNeed() {
        // Setup
        Need need = new Need(0, 0, "update me", "updated", null, null, "donation");

        // Invoke
        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Need actual = cupboardFileDAO.getNeed(need.getName());
        assertEquals(actual,need);
    }

        @Test
    public void testUpdateHeroNotFound() {
        // Setup
        Need need = new Need(0, 0, "unavaliable",null , null, null, "donation");

        // Invoke
        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }
}
