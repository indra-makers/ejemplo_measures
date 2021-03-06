package com.indramakers.example.measuresms.model.entities;

import java.io.Serializable;
import java.util.Date;

public class Measure implements Serializable {

    private Long id;
    private String deviceId;
    private Double value;
    private Date dateTime;

    public Measure() {
    }

    public Measure(String deviceId, Double value) {
        this.deviceId = deviceId;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
