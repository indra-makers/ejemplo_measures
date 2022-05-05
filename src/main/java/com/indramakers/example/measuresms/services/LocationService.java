package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public void registerLocation(Location location){
        locationRepository.createLocation(location);
    }

    public List<Location> getLocationByName(String name){
        return locationRepository.findByName(name);
    }

    public void deleteLocation(int id_location){
        locationRepository.deleteById(id_location);
    }

    public List<Measure> getMeasures(int id_location){
        return locationRepository.findMeasuresByLocation(id_location);
    }
}
