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

import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.persistence.AccountDAO;

/**
 * Handles HTTP communication.
 * 
 * Adapted from SWEN Faculty
 */
@RestController
@RequestMapping("account")
public class AccountController {
    
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDao;

    /**
     * Creates a REST API controller
     * @param accountDao Inject data access object
     */
    public AccountController(AccountDAO accountDao) {
        this.accountDao = accountDao;
    }    
    
    /**
     * Saves the {@linkplain Account account} to persistent storage
     * 
     * @param account Saves this
     * 
     * @return ResponseEntity with created {@link Account account} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Account account} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        LOG.info("POST /accounts " + account);
        try {
            boolean success = accountDao.createAccount(account);
            if(success) {
                return new ResponseEntity<Account>(account, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Gets a account given its name
     * @param name The name of the account to be returned
     * @return The Account object of the account with that name
     */
    @GetMapping("/{name}")
    public ResponseEntity<Account> getAccount(@PathVariable String name)
    {
        try {
            Account out = accountDao.getAccount(name);
            if (out != null)
            {
                return new ResponseEntity<Account>(out, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
    }

    /***
     * @param name used to identify searched for account
     * 
     * @return ResponseEntity with and HTTP status of Success<br>
     * ResponseEntity with HTTP satus if {@link Account account} object doesn't exist
     * ResponseEntity with HTTP satus status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException 
     */
    @GetMapping("/")
    public ResponseEntity<Account[]> searchAccounts(@RequestParam String name) throws IOException {
        try {
            Account[] accounts = this.accountDao.getAccounts();
            LOG.info("GET /accounts/?name="+name);
    
            // Check if any accounts are found
            if (accounts == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Account[] allAccounts = new Account[accounts.length];
                Account curaccount = null;
                int counter = -1;

                for(int i = 0; i < accounts.length; i++){
                    curaccount = accounts[i];
                    if(curaccount.getName().contains(name)){
                        counter+=1;
                        allAccounts[counter] = curaccount;
                    }
                }
                if (counter != -1) {
                    return new ResponseEntity<>(allAccounts, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (IOException e) {
            LOG.warning("Error occurred while searching through accounts: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Updates the {@linkplain Account account} with the provided {@linkplain Account account} object, if it exists
     * 
     * @param account The {@link Account account} to update
     * 
     * @return ResponseEntity with updated {@link Account account} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        LOG.info("PUT /accounts " + account);

        try {
            Account newAccount = accountDao.updateAccount(account);
            if (newAccount != null)
                return new ResponseEntity<Account>(newAccount,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    /**
     * Gets the accounts from the Account file DAO and returns them in a ResponseEntity
     * @return ResponseEntity the accounts from the Account file DAO
     */
    @GetMapping("")
    public ResponseEntity<Account[]> getAccounts()
    {
        LOG.info("GET /accounts");
        try {
            Account[] outAccounts = accountDao.getAccounts();
            return new ResponseEntity<Account[]>(outAccounts, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the Account with the name name
     * @param name The name of the Account to be deleted
     * @return True if the account was deleted and false otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Account> deleteAccount(@PathVariable String name)
    {
        try {
            if (accountDao.deleteAccount(name))
                return new ResponseEntity<Account>(HttpStatus.OK);
            else
                return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Other REST methods here
     */
}
