package com.ufund.api.ufundapi.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single need for the Hope Center.
 * Please note that the excessive getters and setters
 * are for JSON
 * 
 * Adapted from SWEN Faculty
 * @author Logan Nickerson
 */
public class Need {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [goal = %.2f, progress = %.2f, name = %s, description = %s, volunteerDates = %s, deadline = %s, type = %s, tags = %s]";

    @JsonProperty("goal") private double goal;
    @JsonProperty("progress") private double progress;
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("volunteerDates") private Date[] volunteerDates;
    @JsonProperty("deadline") private Date deadline;
    @JsonProperty("type") private NeedType type;
    @JsonProperty("tags") ArrayList<String> tags;

    public Need(@JsonProperty("goal") double goal, 
    @JsonProperty("progress") double progress, 
    @JsonProperty("name") String name, 
    @JsonProperty("description") String description, 
    @JsonProperty("volunteerDates") Date[] volunteerDates, 
    @JsonProperty("deadline") Date deadline, 
    @JsonProperty("type") NeedType type,
    @JsonProperty("tags") ArrayList<String> tags) {
        this.goal = goal;
        this.progress = progress;
        this.name = name;
        this.description = description;
        this.volunteerDates = volunteerDates;
        this.deadline = deadline;
        this.type = type;
        this.tags = tags;
    }

    public Need(double goal, double progress, String name, String description, Date[] volunteerDates, Date deadline, NeedType type) {
        this(goal, progress, name, description, volunteerDates, deadline, type, new ArrayList<String>());
    }

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    protected static String getStringFormat() {
        return STRING_FORMAT;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date[] getVolunteerDates() {
        return volunteerDates;
    }

    public void setVolunteerDates(Date[] volunteerDates) {
        this.volunteerDates = volunteerDates;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public NeedType getType() {
        return type;
    }
    
    public void setType(NeedType type) {
        this.type = type;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, goal, progress, name, description, Arrays.toString(volunteerDates), deadline, type, tags);
    }

}
