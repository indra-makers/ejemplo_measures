package com.indramakers.example.measuresms.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.api.models.openweather.OpenWeatherMain;
import com.indramakers.example.measuresms.api.models.openweather.OpenWeatherResponse;
import com.indramakers.example.measuresms.model.responses.ErrorResponse;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClimaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testMuyFrio() throws Exception {
        //definendo los moks...
        OpenWeatherResponse mockedBody = new OpenWeatherResponse(
                new OpenWeatherMain(3.0,
                        100.0,
                        5.0, 40.0)
        );
        ResponseEntity<OpenWeatherResponse> entity = new ResponseEntity(mockedBody, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(
               Mockito.anyString(),
               Mockito.<Class<OpenWeatherResponse>>any()
        )).thenReturn(entity);

        //-----------------------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/clima?lat=10.39241169743138&lon=-75.49341817874787")
                .contentType(MediaType.APPLICATION_JSON);


        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        String strResponse = response.getContentAsString();

        Assertions.assertEquals("muy frio", strResponse);
    }

    @Test
    public void testNotFOundAPI() throws Exception {
        ResponseEntity<OpenWeatherResponse> entity = new ResponseEntity(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForEntity(
                Mockito.anyString(),
                Mockito.<Class<OpenWeatherResponse>>any()
        )).thenReturn(entity);

        //-----------------------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/clima?lat=10.39241169743138&lon=-75.49341817874787")
                .contentType(MediaType.APPLICATION_JSON);


        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(412, response.getStatus());

        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Measures must be in range", error.getMessage());
    }
}
