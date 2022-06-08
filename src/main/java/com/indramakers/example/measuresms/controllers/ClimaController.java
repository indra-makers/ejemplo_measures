package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.services.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clima")
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    @GetMapping
    public String obtenerEstadoClima(@RequestParam Double lat,
                                     @RequestParam Double lon) {

        return climaService.getClima(lat, lon);
    }
}
