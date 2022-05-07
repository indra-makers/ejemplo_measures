package com.indramakers.example.measuresms.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.config.Routes;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.LocationRepository;
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


@SpringBootTest
@AutoConfigureMockMvc
@Transactional  //por cada test hace un rollback
public class LocationControllerTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createLocationHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.LOCATIONS_PATH)
                .content("{\n" +
                        "    \"name\": \"aquerrte\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Location> locations = locationRepository.findByLocationName("aquerrte");
        Assertions.assertEquals(1, locations.size());

        Location deviceToAssert = locations.get(0);

        Assertions.assertEquals("aquerrte", deviceToAssert.getName());
    }

    @Test
    @Sql("/testdata/create_location_already_created.sql")
    public void createLocationAlreadyCreated() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.LOCATIONS_PATH)
                .content("{\n" +
                        "    \"name\": \"aquerrte\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_location_already_created.sql")
    public void createLocationBadParams() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Routes.LOCATIONS_PATH)
                .content("{\n" +
                        "    \"namee\": \"aquerrte\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }


    @Test
    @Sql("/testdata/create_location_already_created.sql")
    public void getLocationByIdHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.LOCATIONS_BY_ID+"?id_location=999").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        Location[] locations = objectMapper.readValue(response.getContentAsString(), Location[].class);
        Assertions.assertEquals(1, locations.length);
    }

    @Test
    public void getNoLocationById() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.LOCATIONS_BY_ID+"?id_location=999").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_location_already_created.sql")
    public void getLocationByIdBadParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.LOCATIONS_BY_ID+"?id_location=99gt9").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_location_already_created.sql")
    public void deleteLocationHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.LOCATIONS_PATH+Routes.DELETE_LOCATION_BY_ID, 999).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void deleteNoLocation() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.LOCATIONS_PATH+Routes.DELETE_LOCATION_BY_ID, 999).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/delete_location_with_device_referenced.sql")
    public void deleteLocationDeviceReferenced() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.LOCATIONS_PATH+Routes.DELETE_LOCATION_BY_ID, 999).contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(412, response.getStatus());
    }

    @Test
    @Sql("/testdata/create_location_already_created.sql")
    public void deleteLocationBadParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(Routes.LOCATIONS_PATH+Routes.DELETE_LOCATION_BY_ID, "99ad9").contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_measures_by_locations.sql")
    public void getMeasuresByLocationsHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.MEASURES_BY_LOCATION, 990).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        Measure[] measures = objectMapper.readValue(response.getContentAsString(), Measure[].class);
        Assertions.assertEquals(4, measures.length);
    }

    @Test
    public void getMeasuresByNoLocations() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.MEASURES_BY_LOCATION, 990).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_measures_by_locations_no_devices.sql")
    public void getMeasuresByLocationsNoDevices() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.MEASURES_BY_LOCATION, 990).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_measures_by_locations.sql")
    public void getMeasuresByLocationsBadParam() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.MEASURES_BY_LOCATION, "99das0").contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(500, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_no_measures_by_locations.sql")
    public void getNoMeasuresByLocations() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.MEASURES_BY_LOCATION, 990).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @Sql("/testdata/get_measures_by_locations.sql")
    public void getMeasuresValuesByLocationsHappy() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(Routes.LOCATIONS_PATH+Routes.MEASURES_VALUE_BY_LOCATION, 990).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());

        List<Measure> measures = locationRepository.getMeasures(990);

        Assertions.assertEquals(4, measures.size());
        Assertions.assertEquals(66, measures.get(0).getValue());
        Assertions.assertEquals(56, measures.get(1).getValue());
        Assertions.assertEquals(76, measures.get(2).getValue());
        Assertions.assertEquals(46, measures.get(3).getValue());
    }



}
