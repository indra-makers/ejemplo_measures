package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    /**
     *POST /locations
     * @param location
     */
    @PostMapping
    public void create(@RequestBody Location location) {
        locationService.registerLocation(location);
    }

    /**
     * GET  /locations?name_location={{name}}
     * @param name
     */
    @GetMapping
    public List<Location> getByName(@RequestParam(name="name_location") String name){
        return locationService.getLocationByName(name);
    }

    /**
     * DELETE  /locations/delete/{id_location}
     * @param id_location
     */
    @DeleteMapping("/delete/{id_location}")
    public void deleteLocationById(@PathVariable(name="id_location") int id_location){
        locationService.deleteLocation(id_location);
    }

    /**
     * GET  /locations/
     * @param id_location
     */
    @GetMapping ("/{id_location}/measures")
    public List<Measure> getByLocation(@PathVariable(name="id_location") int id_location){
        return locationService.getMeasures(id_location);
    }
}
