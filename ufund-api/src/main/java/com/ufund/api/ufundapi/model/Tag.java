package com.ufund.api.ufundapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.logging.Logger;

/**
 * Represent a single tag for the Hope Center
 * @author May Jiang
 */


public class Tag {

    /**
     * suppressing unused because tests
     * we will burn OO to the ground if it means we can use a worse testing architecture
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package privates
    static final String STRING_FORMAT = "Account [name = %s, tagDetail = %s, tagInstruction = %s, applyable = %b]";

    @JsonProperty("name") private String name;
    @JsonProperty("tagDetail") private String tagDetail;
    @JsonProperty("tagInstruction") private String tagInstruction;
    @JsonProperty("applyable") private boolean applyable;

    public Tag(@JsonProperty("name") String name,
    @JsonProperty("tagDetail") String tagDetail,
    @JsonProperty("tagInstruction") String tagInstruction,
    @JsonProperty("applyable") boolean applyable){
        this.name = name;
        this.tagDetail = tagDetail;
        this.tagInstruction = tagInstruction;
        this.applyable = applyable;
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

    public String getTagDetail() {
        return this.tagDetail;
    }

    public void setTagDetail(String tagDetail) {
        this.tagDetail = tagDetail;
    }

    public Boolean getApplyable() {
        return this.applyable;
    }

    public void setApplyable(Boolean applyable) {
        this.applyable = applyable;
    }

    public String getTagInstruction() {
        return this.tagInstruction;
    }

    public void setTagInstruction(String tagInstruction) {
        this.tagInstruction = tagInstruction;
    }

        @Override
    public String toString() {
        return String.format(STRING_FORMAT, name, tagDetail, tagInstruction,applyable);
    }
}
