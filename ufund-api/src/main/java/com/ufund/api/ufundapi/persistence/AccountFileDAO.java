package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;

/**
 * Uses account JSON to store accounts
 * 
 * Adapted from Logan Nickerson
 * @author May Jiang
 */
public class AccountFileDAO implements AccountDAO{
    
    /**
     * Suppress unused because logger is for debug
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    
    /**
     * Holds local cache of file
     */
    Map<String, Account> accounts;

    /**
     * Provides serialization and deserialization
     */
    private ObjectMapper objectMapper; 
    private String filename;

    /**
     * Creates a Account File Data Access Object
     * 
     * @param filename read/write to
     * @param objectMapper Provides serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed
     */
    public AccountFileDAO(@Value("${account.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * @return All cupboard accounts in an array
     */
    private Account[] getaccountArray() {
        ArrayList<Account> accountArrayList = new ArrayList<>();
    
        for(Account account : accounts.values()) {
            accountArrayList.add(account);
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }

    /**
     * Saves the {@linkplain Account accounts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Account accounts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Account[] accountArray = getaccountArray();

        // Serializes cache
        objectMapper.writeValue(new File(filename), accountArray);
        return true;
    }

    /**
     * Loads {@linkplain Account accounts} from the JSON file into the map
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed
     */
    private boolean load() throws IOException {
        accounts = new HashMap<>();

        Account[] accountArray = objectMapper.readValue(new File(filename), Account[].class);

        for(Account account : accountArray) {
            accounts.put(account.getName(), account);
        }
        return true;
    }

    @Override
    public boolean createAccount(Account account) throws IOException{
        synchronized(accounts) {
            if(accounts.containsKey(account.getName())) {
                return false;
            }
            accounts.put(account.getName(), account);
            save();
            return true;
        }
    }

    @Override
    public boolean deleteAccount(String name) throws IOException {
        synchronized(accounts)
        {
            Account out = accounts.remove(name);
            save();
            return !(out == null);
        }
    }

    /**
     * Returns a Account from the Account database with the given name, and null if there is no such Account
     * @param name the name of the Account to be returned
     * @return A Account with the given name from the database or null if no such name exists
     */
    @Override
    public Account getAccount(String name) {
        synchronized(accounts)
        {
            if (accounts.containsKey(name))
                return accounts.get(name);
            return null;
        }
    }

    @Override
    /**
     * Gets an array of all the Accounts in the Cupboard
     * @return Account[] Accounts The Accounts in the Cupboard
     */
    public Account[] getAccounts() {
        return this.getaccountArray();
    }

    @Override
    public Account updateAccount(Account account) throws IOException {
        synchronized(accounts) {
            System.out.println(accounts);
            if((accounts.containsKey(account.getName()) == false)) {
                return null; // if doesn't exist return null
            }
            accounts.put(account.getName(), account);
            save(); //can throw IOException
            return account;
        }
    }
}

