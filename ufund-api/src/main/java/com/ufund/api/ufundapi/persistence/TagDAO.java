package com.ufund.api.ufundapi.persistence;
import java.io.IOException;

import com.ufund.api.ufundapi.model.Tag;

/**
 * Interface for doing stuff to the accounts
 * 
 * Adapted from Logan Nickerson
 * @author May Jiang
 */
public interface TagDAO {
    Tag[] getTags() throws IOException;
    Tag getTag(String name) throws IOException;
    
    /**
     * Saves a {@linkplain Tag Tag} to the cupboard
     * @param account
     * @return false if duplicate name
     * @throws IOException
     */
    boolean createTag(Tag account) throws IOException;
    Tag updateTag(Tag account) throws IOException;
    boolean deleteTag(String name) throws IOException;
}
