package com.indramakers.example.measuresms.api.model;

public class Weather {

    private Double lat;
    private Double lon;
    private Double temperature;

    public Weather() {
    }

    public Weather(Double lat, Double lon, Double temperature) {
        this.lat = lat;
        this.lon = lon;
        this.temperature = temperature;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
