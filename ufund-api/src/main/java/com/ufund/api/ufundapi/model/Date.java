package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a date in time.
 * This allows the back-end and front-end to change the string format
 * of a date without messing each other up.
 * @author Logan Nickerson
 */
public class Date implements Comparable<Date> {

    private final static String TO_STRING_FORMAT = "%d/%d/%d";

    @JsonProperty("day") private final int day;
    @JsonProperty("month") private final int month;
    @JsonProperty("year") private final int year;
    
    public Date(@JsonProperty("day") int day, @JsonProperty("month") int month, @JsonProperty("year") int year) {
        this.day = day;
        this.month = month;
        this.year = year;
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

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, month, day, year);
    }

    @Override
    public int compareTo(Date timestamp) {
        if(this.year == timestamp.year) {
            if(this.month == timestamp.month) {
                return this.day - timestamp.day;
            }
            return this.month - timestamp.month;
        }
        return this.year - timestamp.year;
    }
    
}
