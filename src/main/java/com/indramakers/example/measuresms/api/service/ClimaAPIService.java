package com.indramakers.example.measuresms.api.service;

import com.indramakers.example.measuresms.api.clients.OpenWeatherApiClient;
import com.indramakers.example.measuresms.api.models.ClimaAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClimaAPIService {

    @Autowired
    private OpenWeatherApiClient openWeatherApiClient;

    public ClimaAPI obtenerClima(Double lat, Double lng) {
        return openWeatherApiClient.getClima(lat,lng);
    }
}
