package com.indramakers.example.measuresms.model.responses;

import com.indramakers.example.measuresms.model.entities.Measure;

import java.util.List;

public class ListMEasuresResponses {

    private List<Measure> measures;

    private Double total;

    public ListMEasuresResponses(List<Measure> measures, Double total) {
        this.measures = measures;
        this.total = total;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
