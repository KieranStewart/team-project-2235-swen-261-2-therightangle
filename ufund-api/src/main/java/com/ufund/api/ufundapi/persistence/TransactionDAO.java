package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Transaction;

/**
 * Interface for getting and logging Transactions.
 * 
 * Adapted from SWEN Faculty
 * @author Logan Nickerson
 */
public interface TransactionDAO {

    Transaction[] getAllTransactions() throws IOException;
    Transaction[] getTransactions(Need need) throws IOException;

    /**
     * Saves a {@linkplain Transaction transaction} to storage
     * @param transaction
     * @return false if no Need is found to put this transaction under
     * @throws IOException
     */
    boolean createTransaction(Transaction transaction, Need need) throws IOException;
    boolean deleteTransactionHistory(Need need) throws IOException;
}
