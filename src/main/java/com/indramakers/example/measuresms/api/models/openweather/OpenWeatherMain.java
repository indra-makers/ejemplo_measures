package com.indramakers.example.measuresms.api.models.openweather;


import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherMain {

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double thermicSensation;

    private Double pressure;

    private Double humidity;

    public OpenWeatherMain() {
    }

    public OpenWeatherMain(Double temperature, Double thermicSensation, Double pressure, Double humidity) {
        this.temperature = temperature;
        this.thermicSensation = thermicSensation;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getThermicSensation() {
        return thermicSensation;
    }

    public void setThermicSensation(Double thermicSensation) {
        this.thermicSensation = thermicSensation;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
