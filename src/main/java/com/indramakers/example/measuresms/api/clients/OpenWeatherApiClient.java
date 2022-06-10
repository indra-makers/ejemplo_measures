package com.indramakers.example.measuresms.api.clients;

import com.indramakers.example.measuresms.api.models.ClimaAPI;
import com.indramakers.example.measuresms.api.models.openweather.OpenWeatherResponse;
import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenWeatherApiClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.openweather.apikey}")
    private String apiKey;

    @Value("${api.openweather.url}")
    private String apiUrl;

    @Cacheable(value = "clima", cacheManager = "expire30seg", key = "{#lat ,#lon}", unless = "#result == null")
    public ClimaAPI getClima(Double lat, Double lon) {

        UriComponentsBuilder uri = UriComponentsBuilder
                .fromUriString(apiUrl)
                        .queryParam("lat", lat.toString())
                        .queryParam("lon", lon.toString())
                        .queryParam("APPID",apiKey)
                        .queryParam("units", "metric");


        ResponseEntity<OpenWeatherResponse> response = restTemplate.getForEntity(uri.toUriString(),
                OpenWeatherResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
           throw new BusinessException(ErrorCodes.MEASURE_VALUES_OUT_OF_RANGE);
        }

        OpenWeatherResponse body = response.getBody();
        return new ClimaAPI(body.getTemperatureInfo().getTemperature(),
                body.getTemperatureInfo().getHumidity(),
                body.getTemperatureInfo().getPressure());

    }

}
