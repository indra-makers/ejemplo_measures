package com.indramakers.example.measuresms.model.responses;

import java.util.List;

public class MeasureSummaryResponse {
    private List<MeasureSummary> measures;

    public MeasureSummaryResponse(List<MeasureSummary> measures) {
        this.measures = measures;
    }

    public List<MeasureSummary> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureSummary> measures) {
        this.measures = measures;
    }
}
