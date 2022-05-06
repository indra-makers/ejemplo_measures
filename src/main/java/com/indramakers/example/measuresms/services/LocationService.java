package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private IDevicesRepository devicesRepository;

    public void registerLocation(String name){
        locationRepository.create(new Location(name));
    }

    public List<Location> getLocationById(int id) {
        return locationRepository.findByLocation_id(id);
    }

    public void deleteLocation(int id){
        if (devicesRepository.findByLocation(id).size() == 0){
            locationRepository.deleteLocation(id);
        }else{
            throw  new RuntimeException("Eliminacion invalida, device asociado a localizacion");
        }
    }
}
