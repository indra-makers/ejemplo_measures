package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.services.DeviceService;
import com.indramakers.example.measuresms.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private LocationService locationService;


    /**
     * URL /locations
     * @param location
     */
    @PostMapping
    public void create(@Valid @RequestBody Location location) {
        locationService.registerLocation(location.getName());
    }

    /**
     *   GET /locations?id={{id}},
     * @param id
     * @return
     */
    @GetMapping
    public List<Location> getLocationById(@RequestParam(name = "id") int id) {
        return locationService.getLocationById(id);
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
     * URL /locations
     *
     * @param id
     */
    @DeleteMapping ("/{id_location}")
    public void delete(@PathVariable("id_location") int id) {
        locationService.deleteLocation(id);
    }

}
