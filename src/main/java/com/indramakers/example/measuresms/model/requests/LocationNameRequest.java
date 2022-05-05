package com.indramakers.example.measuresms.model.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class LocationNameRequest {

    private String name;

    public LocationNameRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
