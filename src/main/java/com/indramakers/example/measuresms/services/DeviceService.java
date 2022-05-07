package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessExceptions;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private IDevicesRepository devicesRepository;
    @Autowired
    private LocationRepository locationRepository;

    /**
     * Create device
     * na devices with repeated name.
     * @param device
     */

    public void createDevice(Device device) {
        Date date = new Date();
        List<Device> devicesByName = devicesRepository.findByName(device.getName());

        if (!devicesByName.isEmpty()) {
            throw new BusinessExceptions(ErrorCodes.DEVICE_WITH_NAME_EXISTS);
        }
        List<Location> locations = locationRepository.getLocation();
        if(locations.isEmpty()){
            throw new NotFoundException(ErrorCodes.NO_LOCATIONS_FOUND.getMessage());
        }
        List<Location> locations1 = locationRepository.findByLocationId(device.getId_location());
        if(locations1.isEmpty()){
            throw new BusinessExceptions(ErrorCodes.LOCATION_INVALID);
        }
        device.setCreatedAt(new Timestamp(date.getTime()));
        devicesRepository.save(device);
    }

    /**
     * get devices by branch
     * @param branch
     * @return list of devices
     */
    public List<Device> getBytBranch(String branch) {
        return devicesRepository.findByBranch(branch);
    }

    public List<Device> getById_location(int id_location){
        if(locationRepository.findByLocationId(id_location).isEmpty()){
            throw new NotFoundException(ErrorCodes.NO_LOCATIONS_FOUND.getMessage());
        }
        return devicesRepository.findByIdLocation(id_location);
    }
}
