package com.ufund.api.ufundapi.model;

public enum NeedType {
    VOLUNTEER,
    DONATION;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
