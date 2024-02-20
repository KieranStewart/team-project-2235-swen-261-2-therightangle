package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

/**
 * Handles HTTP communication.
 * 
 * Adapted from SWEN Faculty
 */
@RestController
@RequestMapping("cupboard")
public class CupboardController {

    private static final Logger LOG = Logger.getLogger(CupboardController.class.getName());
    private CupboardDAO cupboardDao;

    /**
     * Creates a REST API controller to repond to requests
     * @param cupboardDao Injected data access object
     */
    public CupboardController(CupboardDAO cupboardDao) {
        this.cupboardDao = cupboardDao;
    }

    /**
     * Saves the {@linkplain Need need} to persistent storage
     * 
     * @param need Saves this
     * 
     * @return ResponseEntity with created {@link Need need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Need need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        LOG.info("POST /needs " + need);
        try {
            boolean success = cupboardDao.createNeed(need);
            if(success) {
                return new ResponseEntity<Need>(need, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***
     * @param name used to identify searched for need
     * 
     * @return ResponseEntity with and HTTP status of Success<br>
     * ResponseEntity with HTTP satus if {@link Need need} object doesn't exist
     * ResponseEntity with HTTP satus status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException 
     */
    @GetMapping("/")
    public ResponseEntity<Need> searchNeeds(@RequestParam String name) throws IOException {
        try {
            Need[] needs = this.cupboardDao.getNeeds();
            LOG.info("GET /needs/?name="+name);
    
            // Check if any needs are found
            if (needs == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Need need = null;
                for(int i = 0; i < needs.length; i++){
                    need = needs[i];
                    if(need.getName().equals(name)){
                        break;
                    }
                }
                if (need != null) {
                    return new ResponseEntity<>(need, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (IOException e) {
            LOG.warning("Error occurred while searching through needs: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Other REST methods here
     */
}
