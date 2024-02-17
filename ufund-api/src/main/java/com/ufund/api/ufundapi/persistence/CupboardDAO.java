package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

/**
 * Interface for doing stuff to cupboard.
 * Add and remove methods as necessary.
 * @author Logan Nickerson
 */
public interface CupboardDAO {

        /**
         * Retrieves all {@linkplain Need Needs}
         * 
         * @return An array of {@link Need Need} objects, may be empty
         * 
         * @throws IOException if an issue with underlying storage
         */
        Need[] getNeeds() throws IOException;
    
        /**
         * Retrieves a {@linkplain Need Need} with the given name
         * 
         * @param name The name of the {@link Need Need} to get
         * 
         * @return a {@link Need Need} object with the matching name
         * <br>
         * null if no {@link Need Need} with a matching name is found
         * 
         * @throws IOException if an issue with underlying storage
         */
        Need getNeed(String name) throws IOException;
    
        /**
         * Creates and saves a {@linkplain Need Need}
         * 
         * @param Need {@linkplain Need Need} object to be created and saved
         *
         * @return new {@link Need Need} if successful, false otherwise (duplicate name)
         * 
         * @throws IOException if an issue with underlying storage
         */
        Need createNeed(Need Need) throws IOException;
    
        /**
         * Updates and saves a {@linkplain Need Need}
         * 
         * @param {@link Need Need} object to be updated and saved
         * 
         * @return updated {@link Need Need} if successful, null if
         * {@link Need Need} could not be found
         * 
         * @throws IOException if underlying storage cannot be accessed
         */
        Need updateNeed(Need Need) throws IOException;
    
        /**
         * Deletes a {@linkplain Need Need} with the given name
         * 
         * @param name The name of the {@link Need Need}
         * 
         * @return true if the {@link Need Need} was deleted
         * <br>
         * false if the Need with the given name does not exist
         * 
         * @throws IOException if underlying storage cannot be accessed
         */
        boolean deleteNeed(String name) throws IOException;
}
