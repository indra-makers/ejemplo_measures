package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private IDevicesRepository iDevicesRepository;

    public void createLocation(Location location){
        if(locationRepository.findByLocationName(location.getName()).isEmpty()){
            locationRepository.createLocation(location);
        }else{
            throw new BusinessException(ErrorCodes.LOCATION_NOT_FOUND);
        }

    }

    public List<Location> getById(Long id){
        return locationRepository.findByLocationId(id);
    }

    public void deleteLocation(Long id_location){
        if (iDevicesRepository.findByIdLocation(id_location).isEmpty()){
            locationRepository.deleteLocation(id_location);
        }else{
            throw new BusinessException(ErrorCodes.LOCATION_NOT_FOUND);
        }

    }

    public List<Double> getValueMeasure(int id_location){
        return locationRepository.getValueMeasure(id_location);
    }

    public List<Measure> getMeasures(int id_location){
        return locationRepository.getMeasures(id_location);
    }



}
