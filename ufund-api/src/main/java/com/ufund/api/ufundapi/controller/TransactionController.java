package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.Transaction;
import com.ufund.api.ufundapi.persistence.TransactionDAO;

/**
 * Handles HTTP communication of transactions
 * 
 * @author Logan Nickerson
 */
@RestController
@RequestMapping("transactions")
public class TransactionController {

    private static final Logger LOG = Logger.getLogger(CupboardController.class.getName());
    private TransactionDAO transactionDAO;

    /**
     * Creates a REST API controller to repond to requests
     * @param transactionDAO Injected data access object
     */
    public TransactionController(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     * Puts the {@linkplain Transaction transaction} on the list for its need
     * 
     * @param need Adds this
     * 
     * @return ResponseEntity with created {@link Transaction transaction} object and HTTP status of CREATED.<br>
     * ResponseEntity with HTTP status of NOT_FOUND if {@link Transaction transaction} is for a non-existant need (not implemented yet).<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        LOG.info(() -> "POST /transactions " + transaction);
        try {
            if(transaction.isInitialized()) {
                LOG.log(Level.WARNING, "The client is trying to send a Transaction with an ID set.  Don't do this.");
            }
            Transaction storedTransaction = transactionDAO.createTransaction(transaction);
            if(storedTransaction == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Transaction>(storedTransaction, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Gets an array of transactions from the need
     * @param name The name of the need to get transactions for
     * @return The Transaction objects for the need with that name
     */
    @GetMapping("/{name}")
    public ResponseEntity<Transaction[]> getTransactions(@PathVariable String name)
    {
        try {
            Transaction[] transactions = transactionDAO.getTransactions(name);
            if (transactions.length == 0) {
                return new ResponseEntity<Transaction[]>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Transaction[]>(transactions, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Transaction[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets all transactions
     * @return ResponseEntity with all transactions
     */
    @GetMapping("")
    public ResponseEntity<Transaction[]> getAllTransactions()
    {
        LOG.info("GET /transactions");
        try {
            Transaction[] outTransactions = transactionDAO.getAllTransactions();
            return new ResponseEntity<Transaction[]>(outTransactions, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes all the transactions made for this need
     * @param name The name of the Need
     * @return True if the history was deleted and false otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Transaction> deleteTransactionHistory(@PathVariable String name)
    {
        try {
            if (transactionDAO.deleteTransactionHistory(name))
                return new ResponseEntity<Transaction>(HttpStatus.OK);
            else
                return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
