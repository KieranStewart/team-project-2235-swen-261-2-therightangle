package com.ufund.api.ufundapi.model;

public class VolunteerDate extends Date {

    boolean filled; // True if the volunteer date has a volunteer in this slot.
    public VolunteerDate(int day, int month, int year, boolean filled) {
        super(day, month, year);
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
        String out = super.toString();
        if (this.filled)
            return out + ":filled";
        return out + ":empty";
    }
}
