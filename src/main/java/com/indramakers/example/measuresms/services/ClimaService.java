package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.api.model.Weather;
import com.indramakers.example.measuresms.api.services.GetWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClimaService {

    @Autowired
    private GetWeatherService weatherService;

    public String isColdOrHot(Double lat, Double lon) {

        Weather clima = weatherService.getWeather(lat, lon);

        if (clima.getTemperature()>25) return "calido";
        else return "frio";
    }
}
