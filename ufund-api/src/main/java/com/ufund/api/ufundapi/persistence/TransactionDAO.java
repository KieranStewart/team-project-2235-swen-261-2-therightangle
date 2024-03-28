package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Transaction;

/**
 * Interface for getting and logging Transactions.
 * 
 * Adapted from SWEN Faculty
 * @author Logan Nickerson
 */
public interface TransactionDAO {

    Transaction[] getAllTransactions() throws IOException;
    /**
     * null if this need has no transactions
     * @param need
     * @return records for this need
     * @throws IOException
     */
    Transaction[] getTransactions(String need) throws IOException;

    /**
     * Saves a {@linkplain Transaction transaction} to storage
     * @param transaction
     * @return false if the transaction specifies an invalid need
     * @throws IOException
     */
    boolean createTransaction(Transaction transaction) throws IOException;
    boolean deleteTransactionHistory(String need) throws IOException;
}
