package com.indramakers.example.measuresms.api.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class StormGlass {

    @Autowired
    private RestTemplate restTemplate;

    public StormGlassWeather getWeather(Double lat, Double lon) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "ad86e998-e23c-11ec-ab6b-0242ac130002-ad86ea10-e23c-11ec-ab6b-0242ac130002");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("https://api.stormglass.io/v2/weather/point")
                .queryParam("lat", lat.toString())
                .queryParam("lng", lon.toString())
                .queryParam("params", "airTemperature");

        System.out.println(uri.toUriString());
        ResponseEntity<StormGlassWeather> result = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                entity,
                StormGlassWeather.class);

        return result.getBody();
    }
}
