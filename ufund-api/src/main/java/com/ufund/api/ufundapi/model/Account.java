package com.ufund.api.ufundapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.logging.Logger;

/**
 * Represent a single user account for the Hope Center
 * @author May Jiang
 */


public class Account {

    /**
     * suppressing unused because tests
     * we will burn OO to the ground if it means we can use a worse testing architecture
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package privates
    static final String STRING_FORMAT = "Account [name = %s, password = %s, email = %s, tags = %s, isAdmin = %b]";

    @JsonProperty("name") private String name;
    @JsonProperty("password") private String password;
    @JsonProperty("email") private String email;
    @JsonProperty("tags") private String[] tags;
    @JsonProperty("isAdmin") private boolean isAdmin;

    public Account(@JsonProperty("name") String name,
    @JsonProperty("password") String password,
    @JsonProperty("email") String email,
    @JsonProperty("tags") String[] tags,
    @JsonProperty("isAdmin") boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.tags = tags;
        this.isAdmin = isAdmin;
    }

    public static String getStringFormat() {
        return STRING_FORMAT;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean getIsAdmin(){
        return this.isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin){
        this.isAdmin = isAdmin;
    }

        @Override
    public String toString() {
        return String.format(STRING_FORMAT, name, password, email, tags, isAdmin);
    }
}
