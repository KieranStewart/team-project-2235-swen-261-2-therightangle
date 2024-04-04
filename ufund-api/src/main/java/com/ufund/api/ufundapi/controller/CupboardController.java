package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    
    /**
     * Gets a need given its name
     * @param name The name of the need to be returned
     * @return The Need object of the need with that name
     */
    @GetMapping("/{name}")
    public ResponseEntity<Need> getNeed(@PathVariable String name)
    {
        try {
            Need out = cupboardDao.getNeed(name);
            if (out != null) {
                return new ResponseEntity<Need>(out, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Need>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param name used to identify searched for need
     * 
     * @return ResponseEntity with and HTTP status of Success<br>
     * ResponseEntity with HTTP satus if {@link Need need} object doesn't exist
     * ResponseEntity with HTTP satus status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException 
     */
    @GetMapping("/")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam String name) throws IOException {
        try {
            Need[] needs = this.cupboardDao.getNeeds();
            LOG.info("GET /needs/?name="+name);
    
            // Check if any needs are found
            if (needs == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Need[] allNeeds = new Need[needs.length];
                Need curneed = null;
                int counter = -1;

                for(int i = 0; i < needs.length; i++){
                    curneed = needs[i];
                    if(curneed.getName().contains(name)){
                        counter+=1;
                        allNeeds[counter] = curneed;
                    }
                }
                if (counter != -1) {
                    return new ResponseEntity<>(allNeeds, HttpStatus.OK);
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
     * Updates the {@linkplain Need need} with the provided {@linkplain Need need} object, if it exists
     * 
     * @param need The {@link Need need} to update
     * 
     * @return ResponseEntity with updated {@link Need need} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /needs " + need);

        try {
            Need newNeed = cupboardDao.updateNeed(need);
            if (newNeed != null)
                return new ResponseEntity<Need>(newNeed,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    /**
     * Gets the needs from the Cupboard file DAO and returns them in a ResponseEntity
     * @return ResponseEntity the needs from the Cupboard file DAO
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds()
    {
        LOG.info("GET /needs");
        try {
            Need[] outNeeds = cupboardDao.getNeeds();
            return new ResponseEntity<Need[]>(outNeeds, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the Need with the name name
     * @param name The name of the Need to be deleted
     * @return True if the need was deleted and false otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Need> deleteNeed(@PathVariable String name)
    {
        if(name != null) LOG.info("DELETE /" + name);
        try {
            if (cupboardDao.deleteNeed(name))
                return new ResponseEntity<Need>(HttpStatus.OK);
            else
                return new ResponseEntity<Need>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Other REST methods here
     */
}
