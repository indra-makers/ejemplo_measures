package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import com.indramakers.example.measuresms.repositories.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private IDevicesRepository devicesRepository;

    public void registerLocation(Long id, String name) {
        locationRepository.create(new Location(id, name));
    }

    public List<Location> getLocationById(Long id) {
        if(locationRepository.findLocation(id).size()!=0){
            return locationRepository.findLocation(id);
        }
        else{
            throw new NotFoundException(ErrorCodes.INVALID_ID_LOCATION.getMessage());
        }
    }

    public void deleteLocationById(Long id){
        if(devicesRepository.findByIdLocation(id).size()==0){
            locationRepository.delete(id);
        }
        else{
            throw new NotFoundException(ErrorCodes.INVALID_ID_LOCATION.getMessage());
        }
    }

}
