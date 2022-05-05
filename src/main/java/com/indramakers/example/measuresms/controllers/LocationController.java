package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.DeviceService;
import com.indramakers.example.measuresms.services.LocationService;
import com.indramakers.example.measuresms.services.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MeasureService measureService;

    /**
     * GET /locations/{id_location}
     */
    @GetMapping("/{id_location}")
    public List<Location> getLocation(
            @PathVariable(name="id_location") int id) {

        return locationService.getLocationById(id);
    }

    /**
     * PATH /locations
     * POST
     */
    @PostMapping
    public void addLocation(@RequestBody Location location) {

        locationService.registerLocation(location);
    }

    /**
     * GET /locations/{id_location}/device
     */
    @GetMapping("/{id_location}/device")
    public List<Device> getLocationDevice(
            @PathVariable("id_location") int id) {

        return deviceService.getDeviceByLocation(id);
    }

    /**
     * GET /locations/{id_location}/measure
     */
    @GetMapping("/{id_location}/measure")
    public List<Measure> getLocationMeasure(
            @PathVariable("id_location") int id) {

        return measureService.getMeasureByLocation(id);
    }

    /**
     * URL /location
     *
     * @param id
     */
    @DeleteMapping ("/{id_location}")
    public void delete(@PathVariable("id_location") int id) {
        locationService.deleteLocation(id);
    }


}
