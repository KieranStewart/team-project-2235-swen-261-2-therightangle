package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.model.Tag;
import com.ufund.api.ufundapi.persistence.TagDAO;

/**
 * Handles HTTP communication for tags
 * 
 * Adapted from SWEN Faculty
 */
@RestController
@RequestMapping("tag")
public class TagController {
    
    private static final Logger LOG = Logger.getLogger(TagController.class.getName());
    private TagDAO tagDao;

    /**
     * Creates a REST API controller
     * @param tagDao Inject data access object
     */
    public TagController(TagDAO tagDao) {
        this.tagDao = tagDao;
    }    
    
    /**
     * Saves the {@linkplain Tag tag} to persistent storage
     * 
     * @param tag Saves this
     * 
     * @return ResponseEntity with created {@link Tag tag} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Tag tag} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        LOG.info("POST /tags " + tag);
        try {
            boolean success = tagDao.createTag(tag);
            if(success) {
                return new ResponseEntity<Tag>(tag, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Gets a tag given its name
     * @param name The name of the tag to be returned
     * @return The Tag object of the tag with that name
     */
    @GetMapping("/{name}")
    public ResponseEntity<Tag> getTag(@PathVariable String name)
    {
        try {
            Tag out = tagDao.getTag(name);
            if (out != null)
            {
                return new ResponseEntity<Tag>(out, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);
    }

    /***
     * @param name used to identify searched for tag
     * 
     * @return ResponseEntity with and HTTP status of Success<br>
     * ResponseEntity with HTTP satus if {@link Tag tag} object doesn't exist
     * ResponseEntity with HTTP satus status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException 
     */
    @GetMapping("/")
    public ResponseEntity<Tag[]> searchTags(@RequestParam String name) throws IOException {
        try {
            Tag[] tags = this.tagDao.getTags();
            LOG.info("GET /tags/?name="+name);
    
            // Check if any tags are found
            if (tags == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Tag[] allTags = new Tag[tags.length];
                Tag curtag = null;
                int counter = -1;

                for(int i = 0; i < tags.length; i++){
                    curtag = tags[i];
                    if(curtag.getName().contains(name)){
                        counter+=1;
                        allTags[counter] = curtag;
                    }
                }
                if (counter != -1) {
                    return new ResponseEntity<>(allTags, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (IOException e) {
            LOG.warning("Error occurred while searching through tags: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Updates the {@linkplain Tag tag} with the provided {@linkplain Tag tag} object, if it exists
     * 
     * @param tag The {@link Tag tag} to update
     * 
     * @return ResponseEntity with updated {@link Tag tag} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag) {
        LOG.info("PUT /tags " + tag);

        try {
            Tag newTag = tagDao.updateTag(tag);
            if (newTag != null)
                return new ResponseEntity<Tag>(newTag,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    /**
     * Gets the tags from the Tag file DAO and returns them in a ResponseEntity
     * @return ResponseEntity the tags from the Tag file DAO
     */
    @GetMapping("")
    public ResponseEntity<Tag[]> getTags()
    {
        LOG.info("GET /tags");
        try {
            Tag[] outTags = tagDao.getTags();
            return new ResponseEntity<Tag[]>(outTags, HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the Tag with the name name
     * @param name The name of the Tag to be deleted
     * @return True if the tag was deleted and false otherwise
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Tag> deleteTag(@PathVariable String name)
    {
        try {
            if (tagDao.deleteTag(name))
                return new ResponseEntity<Tag>(HttpStatus.OK);
            else
                return new ResponseEntity<Tag>(HttpStatus.NOT_FOUND);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
