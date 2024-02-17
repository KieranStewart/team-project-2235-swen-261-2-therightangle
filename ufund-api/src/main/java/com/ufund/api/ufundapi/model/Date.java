package com.ufund.api.ufundapi.model;

/**
 * Represents a date in time.
 * This allows the back-end and front-end to change the string format
 * of a date without messing each other up.
 * @author Logan Nickerson
 */
public class Date {

    private final int day;
    private final int month;
    private final int year;
    
    public Date(int day, int month, int year) {
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
        return String.format("%d/%d/%d", month, day, year);
    }
    
}
