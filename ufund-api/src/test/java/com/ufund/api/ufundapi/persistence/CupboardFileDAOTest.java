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
@SuppressWarnings("unused")
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
        testNeeds[0] = new Need(0, 0, "update me", "not update", null, null);
        testNeeds[1] = new Need(0, 0, null, null, null, null);
        testNeeds[2] = new Need(0, 0, null, null, null, null);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the need array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Need[].class))
                .thenReturn(testNeeds);
        cupboardFileDAO = new CupboardFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    // // Uncomment this when Get Need is implemented!
    // @Test
    // public void testCreateNeed() {
    //     // Setup
    //     Need need = new Need(0, 0, "new name", null, null, null);

    //     // Invoke
    //     boolean result = assertDoesNotThrow(() -> cupboardFileDAO.createNeed(need),
    //                             "Unexpected exception thrown");

    //     // Analyze
    //     assertTrue(result);
    //     Need actual = cupboardFileDAO.getNeed(need.getName());
    //     assertEquals(actual.getName(), need.getName());
    //     // TODO: all fields tested
    // }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Need[].class));

        Need need = new Need(0, 0, "New Need", null, null, null);

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
    // @Test
    // public void testGetNeeds()
    // {
    //     // Setup
    //     Need[] actualNeeds = cupboardFileDAO.getNeeds();

    //     // Analyze
    //     for (int i = 0; i < actualNeeds.length; i++)
    //     {
    //         assertEquals(testNeeds[i].toString(), actualNeeds[i].toString());
    //     }
    //   }
    
    @Test
    public void testUpdateHero() {
        // Setup
        Need need = new Need(0, 0, "update me", "updated", null, null);

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
        Need need = new Need(0, 0, "unavaliable",null , null, null);

        // Invoke
        Need result = assertDoesNotThrow(() -> cupboardFileDAO.updateNeed(need),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }
}
