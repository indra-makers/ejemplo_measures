package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.api.models.ClimaAPI;
import com.indramakers.example.measuresms.api.service.ClimaAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClimaService {

    @Autowired
    private ClimaAPIService climaAPIService;

    public String getClima(Double lat, Double lng) {
        ClimaAPI clima = climaAPIService.obtenerClima(lat, lng);

        if(clima.getTemperatura()< 5.0) {
            return "muy frio";
        } else if (clima.getTemperatura()>=5.0 && clima.getTemperatura()<10.0) {
            return "frio";
        } else if (clima.getTemperatura()>=10.0 && clima.getTemperatura()<20.0) {
            return "al clima";
        }else {
            return "caliente";
        }
    }

}
