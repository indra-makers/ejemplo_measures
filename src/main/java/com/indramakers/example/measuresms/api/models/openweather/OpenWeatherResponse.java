package com.indramakers.example.measuresms.api.models.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherResponse {

    @JsonProperty("main")
    private OpenWeatherMain temperatureInfo;

    public OpenWeatherResponse() {
    }

    public OpenWeatherResponse(OpenWeatherMain temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }

    public OpenWeatherMain getTemperatureInfo() {
        return temperatureInfo;
    }

    public void setTemperatureInfo(OpenWeatherMain temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }
}
