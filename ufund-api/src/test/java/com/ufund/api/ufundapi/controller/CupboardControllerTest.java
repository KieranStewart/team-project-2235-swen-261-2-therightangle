package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.model.Date;
import com.ufund.api.ufundapi.model.Need;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

/**
 * Test the Cupboard Controller class
 * 
 * Adapted from SWEN Faculty
 */
@Tag("Controller-tier")
public class CupboardControllerTest {
    private CupboardController cupboardController;
    private CupboardDAO mockCupboardDAO;

    /**
     * Before each test, create a new CupboardController object and inject
     * a mock Cupboard DAO
     */
    @BeforeEach
    public void setupCupboardController() {
        mockCupboardDAO = mock(CupboardDAO.class);
        cupboardController = new CupboardController(mockCupboardDAO);
    }

    @Test
    public void testCreateNeed() throws IOException {
        // Setup
        Need need = new Need(0, 0, null, null, null, null);
        // simulate success
        when(mockCupboardDAO.createNeed(need)).thenReturn(true);

        // Invoke
        ResponseEntity<Need> response = cupboardController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testCreateNeedFailed() throws IOException {
        // Setup
        Need need = new Need(100, 0, "buy dog", "pretend this is a duplicate", null, new Date(17, 2, 2023));
        // when createNeed is called, return false simulating failure
        when(mockCupboardDAO.createNeed(need)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = cupboardController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateNeedHandleException() throws IOException {
        // Setup
        Need need = new Need(0, 0, null, null, null, null);

        doThrow(new IOException()).when(mockCupboardDAO).createNeed(need); // Stimulate an exception

        // Invoke
        ResponseEntity<Need> response = cupboardController.createNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void testSearchNeeds() throws IOException{
        // Setup
        String searchNeed = "event";
        Need[] needs = new Need[3];
        needs[0] = new Need(0, 0, "eventone", null, null, null);
        needs[1] = new Need(0, 0, "eventtwo", null, null, null);
        needs[2] = new Need(0, 0, "volunteers", null, null, null);
    
        when(mockCupboardDAO.getNeeds()).thenReturn(needs);

        //Invoke
        ResponseEntity<Need[]> response = cupboardController.searchNeeds(searchNeed);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        //assertArrayEquals(needs,response.getBody());
    }

    @Test
    public void testSearchNeedsFailed() throws IOException {
        // Setup
        String searchNeed = "Volunteers";
        Need[] needs = null;

        when(mockCupboardDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.searchNeeds(searchNeed);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testSearchNeedsHandleException() throws IOException {
        // Setup
        String searchNeed = "Volunteers";

        doThrow(new IOException()).when(mockCupboardDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.searchNeeds(searchNeed);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    /**
     * Add other controller tests here
     */

}
