package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.repositories.DeviceRepository;
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
    private DeviceRepository devicesRepository;

    public void registerLocation(Location location){
        Boolean locationById = locationRepository.findById(location.getId()).size()==0;

        if(locationById == true){
            locationRepository.create(location);
        }else {
            throw new BusinessException(ErrorCodes.LOCATION_WITH_ID_EXISTS);

        }
    }

    public List<Location> getLocationById(int id) {
        return locationRepository.findById(id);
    }

    /**
     * Create location
     * na location with repeated id.
     * @param id
     */
    public void deleteLocation(int id) {

        if(devicesRepository.findByLocation(id).size()==0){
            locationRepository.deleteLocation(id);
        }else{
            throw new RuntimeException("Invalid delete");
        }
    }


}
