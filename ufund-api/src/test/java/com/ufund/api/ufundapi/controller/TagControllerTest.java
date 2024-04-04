package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.ufund.api.ufundapi.persistence.TagDAO;

import org.junit.jupiter.api.BeforeEach;
import com.ufund.api.ufundapi.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Tag Controller class
 * 
 * Adapted from SWEN Faculty
 */
@org.junit.jupiter.api.Tag("Controller-tier")
public class TagControllerTest {
    private TagController tagController;
    private TagDAO mockTagDAO;

    /**
     * Before each test, create a new TagController object and inject
     * a mock Cupboard DAO
     */
    @BeforeEach
    public void setupTagController() {
        mockTagDAO = mock(TagDAO.class);
        tagController = new TagController(mockTagDAO);
    }

    @Test
    public void testCreateTag() throws IOException {
        // Setup
        Tag tag = new Tag("tag", "dt", "ins", false);
        // simulate success
        when(mockTagDAO.createTag(tag)).thenReturn(true);

        // Invoke
        ResponseEntity<Tag> response = tagController.createTag(tag);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tag, response.getBody());
    }

    @Test
    public void testCreateTagFailed() throws IOException {
        // Setup
        Tag tag = new Tag(null, null, null, false);
        // when createTag is called, return false simulating failure
        when(mockTagDAO.createTag(tag)).thenReturn(false);

        // Invoke
        ResponseEntity<Tag> response = tagController.createTag(tag);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateTagHandleException() throws IOException {
        // Setup
        Tag tag = new Tag(null, null, null, false);

        doThrow(new IOException()).when(mockTagDAO).createTag(tag); // Stimulate an exception

        // Invoke
        ResponseEntity<Tag> response = tagController.createTag(tag);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchTags() throws IOException{
        // Setup
        String searchTag = "event";
        Tag[] tags = new Tag[3];
        tags[0] = new Tag(searchTag, searchTag, searchTag, false);
        tags[1] = new Tag("hi", searchTag, searchTag, false);
        tags[2] = new Tag("bye", searchTag, searchTag, false);
    
        when(mockTagDAO.getTags()).thenReturn(tags);

        //Invoke
        ResponseEntity<Tag[]> response = tagController.searchTags(searchTag);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        //assertArrayEquals(tags,response.getBody());
    }

    @Test
    public void testSearchTagsFailed() throws IOException {
        // Setup
        String searchTag = "Volunteers";
        Tag[] tags = null;

        when(mockTagDAO.getTags()).thenReturn(tags);

        // Invoke
        ResponseEntity<Tag[]> response = tagController.searchTags(searchTag);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchTagsNotFound() throws IOException {
        // Setup
        String searchTag = "Fund giant panda propaganda to undermine the rat party";
        Tag[] tags = new Tag[] {
            new Tag("noo not found", searchTag, searchTag, false)
        };

        when(mockTagDAO.getTags()).thenReturn(tags);

        // Invoke
        ResponseEntity<Tag[]> response = tagController.searchTags(searchTag);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchTagsHandleException() throws IOException {
        // Setup
        String searchTag = "Volunteers";

        doThrow(new IOException()).when(mockTagDAO).getTags();

        // Invoke
        ResponseEntity<Tag[]> response = tagController.searchTags(searchTag);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetTags() throws IOException
    {
        // Setup
        Tag[] expectedTags = mockTagDAO.getTags();
        ResponseEntity<Tag[]> expected = new ResponseEntity<Tag[]>(expectedTags, HttpStatus.OK);

        // Invoke
        ResponseEntity<Tag[]> actual = tagController.getTags();

        // Check
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateTag() throws IOException { // updateTag may throw IOException
        // Setup
        Tag tag = new Tag(null, null, null, false);
        when(mockTagDAO.updateTag(tag)).thenReturn(tag);
        ResponseEntity<Tag> response = tagController.updateTag(tag);
        tag.setName("Test");

        // Invoke
        response = tagController.updateTag(tag);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tag, response.getBody());
    }

    @Test
    public void testUpdateTagFailed() throws IOException { // updateTag may throw IOException
        // Setup
        Tag tag = new Tag(null, null, null, false);
        when(mockTagDAO.updateTag(tag)).thenReturn(null);

        // Invoke
        ResponseEntity<Tag> response = tagController.updateTag(tag);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateTagHandleException() throws IOException { // updateTag may throw IOException
        // Setup
        Tag tag = new Tag(null, null, null, false);
        doThrow(new IOException()).when(mockTagDAO).updateTag(tag);

        // Invoke
        ResponseEntity<Tag> response = tagController.updateTag(tag);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetTag() throws IOException {  // getTag may throw IOException
        // Setup
        Tag tag = new Tag("name", null, null, false);
        when(mockTagDAO.getTag(tag.getName())).thenReturn(tag);

        // Invoke
        ResponseEntity<Tag> response = tagController.getTag(tag.getName());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tag, response.getBody());
    }

    @Test
    public void testGetTagNotFound() throws Exception { // createTag may throw IOException
        // Setup
        String tagName = "name";
        // Simulate no tag found
        when(mockTagDAO.getTag(tagName)).thenReturn(null);

        // Invoke
        ResponseEntity<Tag> response = tagController.getTag(tagName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetTagsHandleException() throws IOException { 
        // Setup
        doThrow(new IOException()).when(mockTagDAO).getTags();

        // Invoke
        ResponseEntity<Tag[]> response = tagController.getTags();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteTag() throws IOException { // deleteTag may throw IOException
        // Setup
        String tagName = "tag";
        // pretend: successful deletion
        when(mockTagDAO.deleteTag(tagName)).thenReturn(true);

        // Invoke
        ResponseEntity<Tag> response = tagController.deleteTag(tagName);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteTagNotFound() throws IOException { // deleteTag may throw IOException
        // Setup
        String tagName = "tag";
        // pretend: fail to delete
        when(mockTagDAO.deleteTag(tagName)).thenReturn(false);

        // Invoke
        ResponseEntity<Tag> response = tagController.deleteTag(tagName);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteTagHandleException() throws IOException { // deleteTag may throw IOException
        // Setup
        String tagName = "tag";
        doThrow(new IOException()).when(mockTagDAO).deleteTag(tagName);

        // Invoke
        ResponseEntity<Tag> response = tagController.deleteTag(tagName);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}
