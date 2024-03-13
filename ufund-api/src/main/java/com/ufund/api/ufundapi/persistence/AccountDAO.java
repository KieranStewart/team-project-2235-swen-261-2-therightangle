package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Account;

/**
 * Interface for doing stuff to the accounts
 * 
 * Adapted from Logan Nickerson
 * @author May Jiang
 */
public interface AccountDAO {

    Account[] getAccounts() throws IOException;
    Account getAccount(String name) throws IOException;
    
    /**
     * Saves a {@linkplain Account Account} to the cupboard
     * @param Account
     * @return false if duplicate name
     * @throws IOException
     */
    boolean createAccount(Account Account) throws IOException;
    Account updateAccount(Account Account) throws IOException;
    boolean deleteAccount(String name) throws IOException;
}
