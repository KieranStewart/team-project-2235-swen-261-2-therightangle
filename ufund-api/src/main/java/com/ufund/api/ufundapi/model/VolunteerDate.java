package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VolunteerDate {

    @JsonProperty("day") int day;
    @JsonProperty("month") int month;
    @JsonProperty("year") int year;
    @JsonProperty("filled") boolean filled; // True if the volunteer date has a volunteer in this slot.

    private final static String TO_STRING_FORMAT = "%d/%d/%d";

    public VolunteerDate(@JsonProperty("day") int day,
    @JsonProperty("month") int month,
    @JsonProperty("year") int year,
    @JsonProperty("filled") boolean filled) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.filled = filled;
    }
 
    public boolean getFilled() {
        return this.filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        String out = String.format(TO_STRING_FORMAT, month, day, year);
        if (this.filled)
            return out + ":filled";
        return out + ":empty";
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
