package com.indramakers.example.measuresms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import com.indramakers.example.measuresms.repositories.MeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional  //por cada test hace un rollback
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IDevicesRepository devicesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Sql("/testdata/create_location.sql")
    public void createDeviceHappyPath() throws Exception {
        List<Location> location = locationRepository.getLocation();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": "+location.get(0).getId_location()+"\n"+
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Device>  devices = devicesRepository.findByName("LTB-331");
        Assertions.assertEquals(1, devices.size());

        Device deviceToAssert = devices.get(0);

        Assertions.assertEquals("CEN", deviceToAssert.getUnits());
        Assertions.assertEquals("siemens", deviceToAssert.getBranch());
    }

    @Test
    @Sql("/testdata/create_location.sql")
    public void createDeviceDeviceAlreadyExist() throws Exception {
        //----la preparacion de los datos de prueba-------
        List<Location> location = locationRepository.getLocation();
        devicesRepository.save(new Device("LTB-331", "siemens", "MTS", location.get(0).getId_location()));
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": "+location.get(0).getId_location()+"\n"+
                        "}").contentType(MediaType.APPLICATION_JSON);

        //------------ las verificaciones--------------------
        Exception exception = assertThrows(NestedServletException.class, () -> {
            MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "Request processing failed; nested exception is java.lang.RuntimeException: Device with that name already exists";

        Assertions.assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void createDeviceDeviceWithBadParams() throws Exception {
        //----la preparacion de los datos de prueba-------

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"name\": \"abc-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_location.sql")
    public void getDeviceMeasures() throws Exception {
        List<Location> location = locationRepository.getLocation();
        devicesRepository.save(new Device("LTB-331", "siemens", "MTS", location.get(0).getId_location()));
        Iterable <Device> devices = devicesRepository.findAll();

        measureRepository.create(new Measure(((devices.iterator().next().getId()).intValue()), 50d));
        measureRepository.create(new Measure(((devices.iterator().next().getId()).intValue()), 35d));
        measureRepository.create(new Measure(((devices.iterator().next().getId()).intValue()), 40d));
        measureRepository.create(new Measure(((devices.iterator().next().getId()).intValue()), 43d));
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/devices/"+((devices.iterator().next().getId()).toString())+"/measures")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(4, measures.length);
    }




}
