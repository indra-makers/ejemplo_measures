package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessExceptions;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
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
    private IDevicesRepository iDevicesRepository;

    public void createLocation(Location location){
        if(locationRepository.findByLocationName(location.getName()).isEmpty()){
            locationRepository.createLocation(location);
        }else{
            throw new BusinessExceptions(ErrorCodes.LOCATION_WITH_NAME_EXISTS);
        }

    }

    public List<Location> getById(int id){
        if(locationRepository.findByLocationId(id).isEmpty()){
            throw new NotFoundException(ErrorCodes.NO_LOCATIONS_FOUND.getMessage());
        }
        return locationRepository.findByLocationId(id);
    }

    public void deleteLocation(int id_location){
        if(locationRepository.findByLocationId(id_location).isEmpty()){
            throw new NotFoundException(ErrorCodes.NO_LOCATIONS_FOUND.getMessage());
        }

        if (iDevicesRepository.findByIdLocation(id_location).isEmpty()){
            locationRepository.deleteLocation(id_location);
        }else{
            throw new BusinessExceptions(ErrorCodes.DEVICE_REFERENCED_TO_LOCATION);
        }

    }

    public List<Double> getValueMeasure(int id_location){
        return locationRepository.getValueMeasure(id_location);
    }

    public List<Measure> getMeasures(int id_location){
        if(locationRepository.findByLocationId(id_location).isEmpty()){
            throw new NotFoundException(ErrorCodes.NO_LOCATIONS_FOUND.getMessage());
        }

        if(locationRepository.getMeasures(id_location).isEmpty()){
            throw new NotFoundException(ErrorCodes.NO_MEASURES_FOUND.getMessage());
        }
        return locationRepository.getMeasures(id_location);
    }


}
