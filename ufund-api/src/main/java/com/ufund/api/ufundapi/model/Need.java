package com.ufund.api.ufundapi.model;
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
    static final String STRING_FORMAT = "Need [goal = %d, progress = %d, name = %s, description = %s, volunteer dates = %s, deadline = %s, type = %s]";

    @JsonProperty("goal") private int goal;
    @JsonProperty("progress") private int progress;
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("volunteerDates") private VolunteerDate[] volunteerDates;
    @JsonProperty("deadline") private Date deadline;
    @JsonProperty("type") private NeedType type;

    public Need(@JsonProperty("goal") int goal, 
    @JsonProperty("progress") int progress, 
    @JsonProperty("name") String name, 
    @JsonProperty("description") String description, 
    @JsonProperty("volunteerDates") VolunteerDate[] volunteerDates, 
    @JsonProperty("deadline") Date deadline, 
    @JsonProperty("type") NeedType type) {
        this.goal = goal;
        this.progress = progress;
        this.name = name;
        this.description = description;
        this.volunteerDates = volunteerDates;
        this.deadline = deadline;
        this.type = type;
    }

    // public Need(@JsonProperty("goal") int goal, 
    // @JsonProperty("progress") int progress, 
    // @JsonProperty("name") String name, 
    // @JsonProperty("description") String description, 
    // @JsonProperty("volunteer dates") Date[] volunteerDates, 
    // @JsonProperty("deadline") Date deadline) {
    //     this.goal = goal;
    //     this.progress = progress;
    //     this.name = name;
    //     this.description = description;
    //     this.volunteerDates = volunteerDates;
    //     this.deadline = deadline;
    //     this.type = "donation";
    // }

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    protected static String getStringFormat() {
        return STRING_FORMAT;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VolunteerDate[] getVolunteerDates() {
        return volunteerDates;
    }

    public void setVolunteerDates(VolunteerDate[] volunteerDates) {
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

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, goal, progress, name, description, Arrays.toString(volunteerDates), deadline, type);
    }

    public void setType(NeedType type) {
        this.type = type;
    }
}
