package com.ufund.api.ufundapi.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Uses JSON to store needs
 * 
 * Adapted from SWEN Faculty
 * @author Logan Nickerson
 * @author May Jiang
 */

@Component
public class CupboardFileDAO implements CupboardDAO {

    /**
     * Suppress unused because logger is for debug
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(CupboardFileDAO.class.getName());
    
    /**
     * Holds local cache of file
     */
    Map<String, Need> needs;

    /**
     * Provides serialization and deserialization
     */
    private ObjectMapper objectMapper; 
    private String filename;

    /**
     * Creates a Need File Data Access Object
     * 
     * @param filename read/write to
     * @param objectMapper Provides serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed
     */
    public CupboardFileDAO(@Value("${cupboard.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * @return All cupboard needs in an array
     */
    private Need[] getNeedArray() {
        ArrayList<Need> needArrayList = new ArrayList<>();
    
        for(Need need : needs.values()) {
            needArrayList.add(need);
        }

        Need[] needArray = new Need[needArrayList.size()];
        needArrayList.toArray(needArray);
        return needArray;
    }

    /**
     * Saves the {@linkplain Need needs} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Need needs} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Need[] needArray = getNeedArray();

        // Serializes cache
        objectMapper.writeValue(new File(filename), needArray);
        return true;
    }

    /**
     * Loads {@linkplain Need needs} from the JSON file into the map
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed
     */
    private boolean load() throws IOException {
        needs = new HashMap<>();

        Need[] needArray = objectMapper.readValue(new File(filename), Need[].class);

        for(Need need : needArray) {
            needs.put(need.getName(), need);
        }
        return true;
    }

    @Override
    public boolean createNeed(Need need) throws IOException {
        synchronized(needs) {
            if(needs.containsKey(need.getName())) {
                return false;
            }
            needs.put(need.getName(), need);
            save();
            return true;
        }
    }

    @Override
    public boolean deleteNeed(String name) {
        needs.remove(name);
        return true;
    }

    @Override
    public Need getNeed(String name) {
        return needs.get(name);
    }

    @Override
    /**
     * Gets an array of all the needs in the Cupboard
     * @return Need[] Needs The needs in the Cupboard
     */
    public Need[] getNeeds() {
        return this.getNeedArray();
    }

    @Override
    public Need updateNeed(Need need) throws IOException {
        synchronized(needs) {
            System.out.println(needs);
            if((needs.containsKey(need.getName()) == false)) {
                return null; // if doesn't exist return null
            }
            needs.put(need.getName(), need);
            save(); //can throw IOException
            return need;
        }
    }
    
    
}
