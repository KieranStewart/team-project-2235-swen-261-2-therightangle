package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NeedType {

    @JsonProperty("volunteer") VOLUNTEER,
    @JsonProperty("donation") DONATION;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
