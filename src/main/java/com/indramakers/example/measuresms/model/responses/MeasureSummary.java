package com.indramakers.example.measuresms.model.responses;

public class MeasureSummary {
    String deviceId;
    Double average;

    public MeasureSummary(String deviceId, Double average) {
        this.deviceId = deviceId;
        this.average = average;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
