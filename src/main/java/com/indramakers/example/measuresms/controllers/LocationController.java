package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class LocationController {

    @Autowired
    private LocationService locationService;


    @PostMapping
    public void createLocation(@RequestBody Location location){
        locationService.createLocation(location);
    }

    @GetMapping("/by_id")
    public List<Location> getLocation(@RequestParam(name="id_location") Long id){
        return locationService.getById(id);
    }

    @DeleteMapping("/delete/{id_location}")
    public void deleteLocation(@PathVariable("id_location") Long id_location){
        locationService.deleteLocation(id_location);
    }

    @GetMapping("/{id_location}/measures_value")
    public List<Double> getMeasureValuesByLocation(@PathVariable("id_location") int id_location){
        return locationService.getValueMeasure(id_location);
    }

    @GetMapping("/{id_location}/measures")
    public List<Measure> getMeasuresByLocation(@PathVariable("id_location") int id_location){
        return locationService.getMeasures(id_location);
    }

}
