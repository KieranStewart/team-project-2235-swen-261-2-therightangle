package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{id}")
    /**
     * Gets a need given its name
     * @param name The name of the need to be returned
     * @return The Need object of the need with that name
     */
    public ResponseEntity<Need> getNeed(@PathVariable String name)
    {
        try {
            Need out = cupboardDao.getNeed(name);
            if (out != null)
            {
                return new ResponseEntity<Need>(out, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Need>(HttpStatus.NOT_FOUND);
    }

    /**
     * Other REST methods here
     */
}
