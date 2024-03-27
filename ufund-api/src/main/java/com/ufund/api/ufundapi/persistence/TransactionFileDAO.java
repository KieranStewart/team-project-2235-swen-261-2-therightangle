package com.ufund.api.ufundapi.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Uses JSON to store Transactions
 * 
 * Adapted from SWEN Faculty
 * @author Logan Nickerson
 */

@Component
public class TransactionFileDAO implements TransactionDAO {

    /**
     * Suppress unused because logger is for debug
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(CupboardFileDAO.class.getName());
    
    /**
     * Holds local cache of file
     * Transactions are stored by the name of the need.
     * Whatever identifies Needs should be the first type.
     */
    Map<String, List<Transaction>> ledger;

    /**
     * Provides serialization and deserialization
     */
    private ObjectMapper objectMapper;  
    private String filename;

    /**
     * Creates a Transaction File Data Access Object
     * 
     * @param filename read/write to
     * @param objectMapper Provides serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed
     */
    public TransactionFileDAO(@Value("${ledger.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * @return All Transactions in an array
     */
    private Transaction[] getTransactionArray() {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    
        for(String need : ledger.keySet()) {
            for(Transaction t : ledger.get(need)) {
                transactionArrayList.add(t);
            }   
        }

        Transaction[] allTransactions = new Transaction[transactionArrayList.size()];
        transactionArrayList.toArray(allTransactions);
        return allTransactions;
    }

    /**
     * Saves the {@linkplain Need needs} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Need needs} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Transaction[] transactionArray = getAllTransactions();

        // Serializes cache
        objectMapper.writeValue(new File(filename), transactionArray);
        return true;
    }

    /**
     * Loads {@linkplain Transaction transactions} from the JSON file into the map
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed
     */
    private boolean load() throws IOException {
        ledger = new HashMap<>();

        Transaction[] transactions = objectMapper.readValue(new File(filename), Transaction[].class);

        // Populate map based on transaction's need name
        for(Transaction t : transactions) {
            if(ledger.containsKey(t.getNeedName())) {
                ledger.get(t.getNeedName()).add(t);
            } else {
                ArrayList<Transaction> record = new ArrayList<>();
                record.add(t);
                ledger.put(t.getNeedName(), record);
            }
        }

        return true;
    }

    @Override
    public boolean createTransaction(Transaction transaction, Need need) throws IOException {
        synchronized(ledger) {
            // TODO: If the cupboard can be accessed here, make sure Need is in the cupboard
            

            transaction.setNeedName(need.getName());
            if(ledger.containsKey(need.getName())) {
                ledger.get(need.getName()).add(transaction);
            } else {
                ArrayList<Transaction> record = new ArrayList<>();
                record.add(transaction);
                ledger.put(need.getName(), record);
            }
            save();
            return true;
        }
    }

    @Override
    public boolean deleteTransactionHistory(Need need) throws IOException {
        if(ledger.containsKey(need.getName())) {
            ledger.remove(need.getName());
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Transaction[] getTransactions(Need need) throws IOException {
        Transaction[] transactions = new Transaction[ledger.get(need.getName()).size()];
        ledger.get(need.getName()).toArray(transactions);
        return transactions;
    }

    @Override
    public Transaction[] getAllTransactions() throws IOException {
        return getTransactionArray();
    }
    
}
