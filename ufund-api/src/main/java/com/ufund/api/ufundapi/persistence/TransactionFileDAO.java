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
                ArrayList<Transaction> transactionRecord = new ArrayList<>();
                transactionRecord.add(t);
                ledger.put(t.getNeedName(), transactionRecord);
            }
        }

        return true;
    }

    /**
     * Stores this transaction under this need name.
     * Does not check if transaction has the right need name.
     * Does not check if transaction is valid in any way.
     * @param transaction
     * @param need
     * @throws IOException
     */
    private void storeTransaction(Transaction transaction, String need) throws IOException {
        synchronized(ledger) {
            if(ledger.containsKey(need)) {
                ledger.get(need).add(transaction);
            } else {
                ArrayList<Transaction> transactionRecord = new ArrayList<>();
                transactionRecord.add(transaction);
                ledger.put(need, transactionRecord);
            }
            save();
        }
    }

    @Override
    public Transaction createTransaction(Transaction transaction) throws IOException {
        String needName = transaction.getNeedName();
        if(isValidNeedName(needName)) {
            Transaction storeThis = new Transaction(transaction.getAmount(), needName);
            storeTransaction(storeThis, needName);
            return storeThis;
        }
        return null;
    }

    /**
     * TODO: if possible, check that a Need with this name is in the cupboard
     * @param name Check if this name is valid for a need
     * @return if it is valid
     */
    private boolean isValidNeedName(String name) {
        return !name.equals("");
    } 

    @Override
    public boolean deleteTransactionHistory(String need) throws IOException {
        if(ledger.containsKey(need)) {
            ledger.remove(need);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Transaction[] getTransactions(String need) throws IOException {
        if(ledger.containsKey(need)) {
            Transaction[] transactions = new Transaction[ledger.get(need).size()];
            ledger.get(need).toArray(transactions);
            return transactions;
        } else {
            return new Transaction[0];
        }
    }

    @Override
    public Transaction[] getAllTransactions() throws IOException {
        return getTransactionArray();
    }
    
}
