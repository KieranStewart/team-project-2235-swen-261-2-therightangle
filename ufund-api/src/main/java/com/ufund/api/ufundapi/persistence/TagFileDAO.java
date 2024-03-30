package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Tag;

/**
 * Uses account JSON to store tag
 * 
 * Adapted from Logan Nickerson
 * @author May Jiang
 */
@Component
public class TagFileDAO implements TagDAO {
    
    /**
     * Suppress unused because logger is for debug
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TagFileDAO.class.getName());
    
    /**
     * Holds local cache of file
     */
    Map<String, Tag> tag;

    /**
     * Provides serialization and deserialization
     */
    private ObjectMapper objectMapper; 
    private String filename;

    /**
     * Creates a Tag File Data Access Object
     * 
     * @param filename read/write to
     * @param objectMapper Provides serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed
     */
    public TagFileDAO(@Value("${tag.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * @return All cupboard tag in an array
     */
    private Tag[] getTagArray() {
        ArrayList<Tag> accountArrayList = new ArrayList<>();
    
        for(Tag account : tag.values()) {
            accountArrayList.add(account);
        }

        Tag[] accountArray = new Tag[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }

    /**
     * Saves the {@linkplain Tag tag} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Tag tag} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Tag[] accountArray = getTagArray();

        // Serializes cache
        objectMapper.writeValue(new File(filename), accountArray);
        return true;
    }

    /**
     * Loads {@linkplain Tag tag} from the JSON file into the map
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed
     */
    private boolean load() throws IOException {
        tag = new HashMap<>();

        Tag[] accountArray = objectMapper.readValue(new File(filename), Tag[].class);

        for(Tag account : accountArray) {
            tag.put(account.getName(), account);
        }
        return true;
    }

    @Override
    public boolean createTag(Tag account) throws IOException{
        synchronized(tag) {
            if(tag.containsKey(account.getName())) {
                return false;
            }
            tag.put(account.getName(), account);
            save();
            return true;
        }
    }

    @Override
    public boolean deleteTag(String name) throws IOException {
        synchronized(tag)
        {
            Tag out = tag.remove(name);
            save();
            return !(out == null);
        }
    }

    /**
     * Returns a Tag from the Tag database with the given name, and null if there is no such Tag
     * @param name the name of the Tag to be returned
     * @return A Tag with the given name from the database or null if no such name exists
     */
    @Override
    public Tag getTag(String name) {
        synchronized(tag)
        {
            if (tag.containsKey(name))
                return tag.get(name);
            return null;
        }
    }

    @Override
    /**
     * Gets an array of all the Tags in the Cupboard
     * @return Tag[] Tags The Tags in the Cupboard
     */
    public Tag[] getTags() {
        return this.getTagArray();
    }

    @Override
    public Tag updateTag(Tag account) throws IOException {
        synchronized(tag) {
            System.out.println(tag);
            if((tag.containsKey(account.getName()) == false)) {
                return null; // if doesn't exist return null
            }
            tag.put(account.getName(), account);
            save(); //can throw IOException
            return account;
        }
    }
}

