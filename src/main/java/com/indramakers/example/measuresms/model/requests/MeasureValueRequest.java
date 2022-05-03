package com.indramakers.example.measuresms.model.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class MeasureValueRequest {

    @Min(0)
    @Max(100)
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
