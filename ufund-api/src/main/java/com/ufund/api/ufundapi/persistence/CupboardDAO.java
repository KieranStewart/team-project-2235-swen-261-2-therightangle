package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

/**
 * Interface for doing stuff to cupboard.
 * Add and remove methods as necessary.
 * @author Logan Nickerson
 */
public interface CupboardDAO {

    Need[] getNeeds() throws IOException;
    Need getNeed(String name) throws IOException;

    /**
     * Saves a {@linkplain Need Need} to the cupboard
     * @param Need
     * @return false if duplicate name
     * @throws IOException
     */
    boolean createNeed(Need Need) throws IOException;
    Need updateNeed(Need Need) throws IOException;
    boolean deleteNeed(String name) throws IOException;
}
