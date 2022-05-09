package com.indramakers.example.measuresms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.config.Routes;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.model.responses.ErrorResponse;
import com.indramakers.example.measuresms.repositories.DeviceRepository;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
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
    private DeviceRepository deviceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createDeviceHappyPath() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"name\": \"LQB-331\",\n" +
                        "    \"units\": \"DEN\",\n" +
                        "    \"branch\": \"Diemens\",\n" +
                        "    \"locationId\": \"1\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Device>  devices = devicesRepository.findByName("LQB-331");
        Assertions.assertEquals(1, devices.size());

        Device deviceToAssert = devices.get(0);

        Assertions.assertEquals("DEN", deviceToAssert.getUnits());
        Assertions.assertEquals("Diemens", deviceToAssert.getBranch());
    }

    @Test
    public void createDeviceDeviceAlreadyExist() throws Exception {
        //----la preparacion de los datos de prueba-------
        devicesRepository.save(new Device(1,"LTB-331", "siemens", "MTS"));

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"locationId\": \"1\",\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(412, response.getStatus());

        String textREsponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textREsponse, ErrorResponse.class);

        Assertions.assertEquals("001", error.getCode());
        Assertions.assertEquals("Device with that name already exists", error.getMessage());

    }

    @Test
    public void createDeviceLocationNotExist() throws Exception {
        //----la preparacion de los datos de prueba-------

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"locationId\": \"8\",\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(412, response.getStatus());

        String textREsponse = response.getContentAsString();
        ErrorResponse error = objectMapper.readValue(textREsponse, ErrorResponse.class);

        Assertions.assertEquals("001", error.getCode());
        Assertions.assertEquals("Location with that id not exists", error.getMessage());

    }

    @Test
    public void createDeviceDeviceWithBadParams() throws Exception {
        //----la preparacion de los datos de prueba-------

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices")
                .content("{\n" +
                        "    \"location_id\": \"1\",\n" +
                        "    \"name\": \"abc-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    public void getDeviceByBranch() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/devices?branch=motorola")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Device[] device = objectMapper.readValue(response.getContentAsString(), Device[].class);
        Assertions.assertEquals(1, device.length);
    }

    @Test
    public void getDeviceByBranch2() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/devices/by-branch?branch=motorola")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Device[] device = objectMapper.readValue(response.getContentAsString(), Device[].class);
        Assertions.assertEquals(1, device.length);
    }

    @Test
    @Sql("/testdata/get_device_measures.sql")
    public void getDeviceMeasures() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/devices/2000/measures")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(4, measures.length);
    }

    @Test
    @Sql("/testdata/addMeasureToDevice.sql")
    public void addMeasureToDevice() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/devices/2000/measures")
                .content("{\n" +
                        "    \"value\": 50\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Object[]> resulst = jdbcTemplate.query("SELECT id, value FROM tb_measures where device_id = ?",
                (rs, rn) -> {
                    return new Object[] {rs.getObject("id"), rs.getObject("value")};
                },
                2000);

        Assertions.assertEquals(1, resulst.size());
        Assertions.assertEquals(50, Integer.valueOf(resulst.get(0)[1].toString()));
    }

    @Test
    public void addMeasureToDeviceWhenDeviceisNotFound() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, "1231")
                .content("{\n" +
                        "    \"value\": 50\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());

        ErrorResponse error = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        Assertions.assertEquals("Device not found", error.getMessage());
    }



}
