package com.indramakers.example.measuresms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LocationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IDevicesRepository devicesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createLocationHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/location")
                .content("{\n" +
                        "    \"id\": \"15\",\n" +
                        "    \"name\": \"casa\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void getLocation() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/location/1");

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Location[] location = objectMapper.readValue(response.getContentAsString(), Location[].class);
        Assertions.assertEquals(1, location.length);
    }

    @Test
    public void deleteLocationHappy() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/location/12");

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void deleteLocationBad() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/location/1");

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());
    }
}
