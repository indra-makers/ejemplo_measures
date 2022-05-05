package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping
    public void create(@RequestBody Location location) {
        locationService.registerLocation(location.getId(), location.getName());
    }

    @GetMapping("/{id}")
    public List<Location> getLocation(@PathVariable("id") Long id) {
        return locationService.getLocationById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        locationService.deleteLocationById(id);
    }
}
