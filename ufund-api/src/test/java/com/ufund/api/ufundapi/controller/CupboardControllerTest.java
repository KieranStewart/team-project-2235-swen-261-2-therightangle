package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.model.Date;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.NeedType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
        Need need = new Need(0, 0, null, null, null, null, NeedType.DONATION);
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
        Need need = new Need(100, 0, "buy dog", "pretend this is a duplicate", null, new Date(17, 2, 2023), NeedType.DONATION);
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
        Need need = new Need(0, 0, null, null, null, null, NeedType.DONATION);

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
        needs[0] = new Need(0, 0, "eventone", null, null, null, NeedType.DONATION);
        needs[1] = new Need(0, 0, "eventtwo", null, null, null, NeedType.DONATION);
        needs[2] = new Need(0, 0, "volunteers", null, null, null, NeedType.DONATION);
    
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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchNeedsNotFound() throws IOException {
        // Setup
        String searchNeed = "Fund giant panda propaganda to undermine the rat party";
        Need[] needs = new Need[] {
            new Need(0, 0, "Help starving children", " ", null, null, null)
        };

        when(mockCupboardDAO.getNeeds()).thenReturn(needs);

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.searchNeeds(searchNeed);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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

    @Test
    public void testGetNeeds() throws IOException
    {
        // Setup
        Need[] expectedNeeds = mockCupboardDAO.getNeeds();
        ResponseEntity<Need[]> expected = new ResponseEntity<Need[]>(expectedNeeds, HttpStatus.OK);

        // Invoke
        ResponseEntity<Need[]> actual = cupboardController.getNeeds();

        // Check
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateNeed() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(0, 0, null, null, null, null, null);
        when(mockCupboardDAO.updateNeed(need)).thenReturn(need);
        ResponseEntity<Need> response = cupboardController.updateNeed(need);
        need.setName("Test");

        // Invoke
        response = cupboardController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testUpdateNeedFailed() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(0, 0, null, null, null, null, null);
        when(mockCupboardDAO.updateNeed(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = cupboardController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateNeedHandleException() throws IOException { // updateNeed may throw IOException
        // Setup
        Need need = new Need(0, 0, null, null, null, null, null);
        doThrow(new IOException()).when(mockCupboardDAO).updateNeed(need);

        // Invoke
        ResponseEntity<Need> response = cupboardController.updateNeed(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetNeed() throws IOException {  // getNeed may throw IOException
        // Setup
        Need need = new Need(0, 0, "name", null, null, null, null);
        when(mockCupboardDAO.getNeed(need.getName())).thenReturn(need);

        // Invoke
        ResponseEntity<Need> response = cupboardController.getNeed(need.getName());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(need, response.getBody());
    }

    @Test
    public void testGetNeedNotFound() throws Exception { // createNeed may throw IOException
        // Setup
        String needName = "name";
        // Simulate no need found
        when(mockCupboardDAO.getNeed(needName)).thenReturn(null);

        // Invoke
        ResponseEntity<Need> response = cupboardController.getNeed(needName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // @Test
    // public void testGetNeedHandleException() throws Exception { // createNeed does NOT throw IOException ever. idk why this was here. probably safe to delete.
    //     // Setup
    //     String needName = "need";
    //     doThrow(new IOException()).when(mockCupboardDAO).getNeed(needName); // throw an IOException

    //     // Invoke
    //     ResponseEntity<Need> response = cupboardController.getNeed(needName);

    //     // Analyze
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    // }

    @Test
    public void testGetNeedsHandleException() throws IOException { 
        // Setup
        doThrow(new IOException()).when(mockCupboardDAO).getNeeds();

        // Invoke
        ResponseEntity<Need[]> response = cupboardController.getNeeds();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteNeed() throws IOException { // deleteNeed may throw IOException
        // Setup
        String needName = "need";
        // pretend: successful deletion
        when(mockCupboardDAO.deleteNeed(needName)).thenReturn(true);

        // Invoke
        ResponseEntity<Need> response = cupboardController.deleteNeed(needName);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException { // deleteNeed may throw IOException
        // Setup
        String needName = "need";
        // pretend: fail to delete
        when(mockCupboardDAO.deleteNeed(needName)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = cupboardController.deleteNeed(needName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteNeedHandleException() throws IOException { // deleteNeed may throw IOException
        // Setup
        String needName = "need";
        doThrow(new IOException()).when(mockCupboardDAO).deleteNeed(needName);

        // Invoke
        ResponseEntity<Need> response = cupboardController.deleteNeed(needName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}
