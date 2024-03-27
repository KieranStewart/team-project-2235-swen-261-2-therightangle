package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("cupboard")
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
     * Saves the {@linkplain Transaction need} to persistent storage
     * 
     * @param need Saves this
     * 
     * @return ResponseEntity with created {@link Transaction need} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Transaction need} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        LOG.info("POST /transactions " + transaction);
        try {
            boolean success = transactionDAO.createTransaction(transaction, transaction.getNeedName());
            if(success) {
                return new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
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
            if (transactions == null) {
                return new ResponseEntity<Transaction[]>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Transaction[]>(transactions, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Transaction[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // /**
    //  * @param name used to identify searched for need
    //  * 
    //  * @return ResponseEntity with and HTTP status of Success<br>
    //  * ResponseEntity with HTTP satus if {@link Transaction need} object doesn't exist
    //  * ResponseEntity with HTTP satus status of INTERNAL_SERVER_ERROR otherwise
    //  * @throws IOException 
    //  */
    // @GetMapping("/")
    // public ResponseEntity<Transaction[]> searchTransactions(@RequestParam String name) throws IOException {
    //     try {
    //         Transaction[] needs = this.transactionDAO.getTransactions();
    //         LOG.info("GET /needs/?name="+name);
    
    //         // Check if any needs are found
    //         if (needs == null) {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         } else {
    //             Transaction[] allTransactions = new Transaction[needs.length];
    //             Transaction curneed = null;
    //             int counter = -1;

    //             for(int i = 0; i < needs.length; i++){
    //                 curneed = needs[i];
    //                 if(curneed.getName().contains(name)){
    //                     counter+=1;
    //                     allTransactions[counter] = curneed;
    //                 }
    //             }
    //             if (counter != -1) {
    //                 return new ResponseEntity<>(allTransactions, HttpStatus.OK);
    //             } else {
    //                 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //             }
    //         }
    //     } catch (IOException e) {
    //         LOG.warning("Error occurred while searching through needs: " + e.getMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    // }


    // /**
    //  * Updates the {@linkplain Transaction need} with the provided {@linkplain Transaction need} object, if it exists
    //  * 
    //  * @param need The {@link Transaction need} to update
    //  * 
    //  * @return ResponseEntity with updated {@link Transaction need} object and HTTP status of OK if updated<br>
    //  * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @PutMapping("")
    // public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction need) {
    //     LOG.info("PUT /needs " + need);

    //     try {
    //         Transaction newTransaction = transactionDAO.updateTransaction(need);
    //         if (newTransaction != null)
    //             return new ResponseEntity<Transaction>(newTransaction,HttpStatus.OK);
    //         else
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    //     catch(IOException e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }    
    // }

    /**
     * Gets all transactions
     * @return ResponseEntity with all transactions
     */
    @GetMapping("")
    public ResponseEntity<Transaction[]> getTransactions()
    {
        LOG.info("GET /needs");
        try {
            Transaction[] outTransactions = transactionDAO.getAllTransactions();
            return new ResponseEntity<Transaction[]>(outTransactions, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the records for this need
     * @param name The name of the Need
     * @return True if the history was deleted and false otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable String name)
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
