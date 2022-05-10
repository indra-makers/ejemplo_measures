package com.indramakers.example.measuresms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.model.responses.ErrorResponse;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional  //por cada test hace un rollback
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IDevicesRepository devicesRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createDeviceHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"location\": \"2\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Device>  devices = devicesRepository.findByName("LTB-331");
        Assertions.assertEquals(1, devices.size());

        Device deviceToAssert = devices.get(0);

        Assertions.assertEquals("CEN", deviceToAssert.getUnits());
        Assertions.assertEquals("siemens", deviceToAssert.getBranch());
        Assertions.assertEquals(2, deviceToAssert.getLocation());
    }

    @Test
    public void createDeviceDeviceAlreadyExist() throws Exception {
        //----la preparacion de los datos de prueba-------
        devicesRepository.save(new Device("LTB-331", "siemens", "MTS", 2));

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\"\n" +
                        "    \"location\": \"2\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(412, response.getStatus());

        String textResponse = response.getContentAsString();
        System.out.println(textResponse);

        String textREsponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textREsponse, ErrorResponse.class);

        Assertions.assertEquals("001", error.getCode());
        Assertions.assertEquals("Device with that name already exists", error.getMessage());
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
                        "    \"branch\": \"siemens\",\n" +
                        "    \"location\": \"1\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_device_measures.sql")
    public void getDeviceMeasures() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/devices/1000/measures")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(4, measures.length);
    }


    @Test
    @Sql("/testdata/addMeasureToDevice.sql")
    public void createMeasure() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices/1000/measures")
                .content("{\n" +
                        "   \"value\" :50\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Object[]> resultst= jdbcTemplate.query("SELECT id, value FROM tb_measures WHERE device_id=?",
                (rs, rn) ->{
                  return new Object[] {rs.getObject("id"), rs.getDate("value")};
                },
                 "1000");

        Assertions.assertEquals(1, resultst.size());
        Assertions.assertEquals(50, Integer.valueOf(resultst.get(0)[1].toString()));

    }

    @Test
    @Sql("/testdata/addMeasureToDevice.sql")
    public void addMeasuretoDeviceWhenDeviceNotFound() throws Exception{
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices/92332/measures")
                .content("{\n" +
                        "   \"value\" :50\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());

        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Device not found",errorResponse.getMessage());

    }


//    @Test
//    public void createLocationHappyPath() throws Exception {
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .post("/devices")
//                .content("{\n" +
//                        "    \"name\": \"Francia\",\n" +
//                        "}").contentType(MediaType.APPLICATION_JSON);
//
//        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
//        Assertions.assertEquals(200, response.getStatus());
//
//        List<Location> locations = locationRepository.findByLocation_id();
//        Assertions.assertEquals(1, locations.size());
//
//        Location locationToAssert = location.get(0);
//
//        Assertions.assertEquals("CEN", deviceToAssert);
//    }




}
