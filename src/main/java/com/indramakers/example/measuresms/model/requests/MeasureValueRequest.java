package com.indramakers.example.measuresms.model.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MeasureValueRequest {

    @Min(0)
    @Max(1000)
    private Double value;

    public MeasureValueRequest() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
