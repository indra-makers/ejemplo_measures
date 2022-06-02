package com.indramakers.example.measuresms.api.services;

import com.indramakers.example.measuresms.api.clients.StormGlass;
import com.indramakers.example.measuresms.api.clients.StormGlassWeather;
import com.indramakers.example.measuresms.api.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetWeatherService {

    @Autowired
    private StormGlass weatherClient;

    public Weather getWeather(Double lat, Double lon) {
        StormGlassWeather weather = weatherClient.getWeather(lat, lon);
        StormGlassWeather.AirTemperature airTemperature = weather.getData().get(0).getAirTemperature();

        return new Weather(lat, lon, Double.valueOf(airTemperature.getDwd()));
    }
}
