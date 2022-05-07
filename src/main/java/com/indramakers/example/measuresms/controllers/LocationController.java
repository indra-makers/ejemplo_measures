package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.config.Routes;
import com.indramakers.example.measuresms.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routes.LOCATIONS_PATH)
public class LocationController {

    @Autowired
    private LocationService locationService;


    @PostMapping
    public void createLocation(@RequestBody Location location){
        locationService.createLocation(location);
    }

    @GetMapping(Routes.LOCATIONS_BY_ID)
    public List<Location> getLocationById(@RequestParam(name="id_location") int id){
        return locationService.getById(id);
    }

    @DeleteMapping(Routes.DELETE_LOCATION_BY_ID)
    public void deleteLocation(@PathVariable("id_location") int id_location){
        locationService.deleteLocation(id_location);
    }

    @GetMapping(Routes.MEASURES_VALUE_BY_LOCATION)
    public List<Double> getMeasureValuesByLocation(@PathVariable("id_location") int id_location){
        return locationService.getValueMeasure(id_location);
    }

    @GetMapping(Routes.MEASURES_BY_LOCATION)
    public List<Measure> getMeasuresByLocation(@PathVariable("id_location") int id_location){
        return locationService.getMeasures(id_location);
    }


}
