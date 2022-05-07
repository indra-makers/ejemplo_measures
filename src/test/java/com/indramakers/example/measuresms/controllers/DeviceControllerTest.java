package com.indramakers.example.measuresms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.config.Routes;
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
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH)
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": 999\n"+
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
        devicesRepository.save(new Device("LTB-331", "siemens", "MTS", 999l));
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH)
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": "+location.get(0).getId_location()+"\n"+
                        "}").contentType(MediaType.APPLICATION_JSON);

        //------------ las verificaciones--------------------

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();


        Assertions.assertEquals(412, response.getStatus());

    }

    @Test
    @Sql("/testdata/create_location.sql")
    public void createDeviceWithoutAtribute() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH)
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": 999\n"+
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }



    @Test
    @Sql("/testdata/create_location.sql")
    public void createDeviceWithInvalidLocation() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH)
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": 6000\n"+
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
    }

    @Test
    public void createDeviceNoLocations() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH)
                .content("{\n" +
                        "    \"name\": \"LTB-331\",\n" +
                        "    \"units\": \"CEN\",\n" +
                        "    \"branch\": \"siemens\",\n" +
                        "    \"id_location\": 6000\n"+
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    public void createDeviceDeviceWithBadParams() throws Exception {
        //----la preparacion de los datos de prueba-------

        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH)
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
    @Sql("/testdata/get_device_measures.sql")
    public void getMeasuresByDeviceHappy() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, 999)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(4, measures.length);
    }

    @Test
    public void getMeasuresByNoDevice() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, 999)
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    public void getMeasuresByDeviceBadParam() throws Exception {
        //----la ejecucion de la prueba misma--------------
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, "csaz")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        //------------ las verificaciones--------------------
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_measure_with_deviceId_happy.sql")
    public void createMeasureWithDeviceIdHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, 999)
                .content("{\n" +
                        "    \"value\": 50\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void createMeasureWithNoDeviceId() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, 999)
                .content("{\n" +
                        "    \"value\": 50\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_measure_with_deviceId_happy.sql")
    public void createMeasureInvalidParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, "2da")
                .content("{\n" +
                        "    \"value\": 50\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_measure_with_deviceId_happy.sql")
    public void createMeasureOutOfRange() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.DEVICES_PATH+Routes.MEASURES_BY_DEVICE_PATH, 999)
                .content("{\n" +
                        "    \"value\": 350\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
    }


    @Test
    @Sql("/testdata/get_devices_by_branch.sql")
    public void getDevicesByBranchHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICE_BY_BRANCH_PATH+"?branch=z.z")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(3, measures.length);
    }


    @Test
    @Sql("/testdata/get_devices_by_branch_invalid_name_param.sql")
    public void getDevicesByBranchNoBranch() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICE_BY_BRANCH_PATH+"?branch=z.z")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(0, measures.length);
    }

    @Test
    @Sql("/testdata/get_devices_by_branch_invalid_name_param.sql")
    public void getDevicesByBranchInvalidNameParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICE_BY_BRANCH_PATH+"?brantch=")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());

    }

    @Test
    @Sql("/testdata/get_devices_by_location_happy.sql")
    public void getDevicesByLocationsHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICES_BY_LOCATION_ID_PATH+"?id_location=999")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        Device[] devices = objectMapper.readValue(response.getContentAsString(), Device[].class);
        Assertions.assertEquals(4, devices.length);
    }

    @Test
    @Sql("/testdata/get_no_devices_by_location_happy.sql")
    public void getNoDevicesByLocations() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICES_BY_LOCATION_ID_PATH+"?id_location=999")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        Device[] devices = objectMapper.readValue(response.getContentAsString(), Device[].class);
        Assertions.assertEquals(0, devices.length);
    }

    @Test
    public void getDevicesByNoLocations() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICES_BY_LOCATION_ID_PATH+"?id_location=999")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_devices_by_location_happy.sql")
    public void getDevicesByLocationsBadParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.DEVICES_PATH+Routes.DEVICES_BY_LOCATION_ID_PATH+"?id_location=99da9")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }




}
