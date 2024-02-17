package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

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
            cupboardDao.createNeed(need);
            return new ResponseEntity<Need>(need, HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Other REST methods here
     */
}
