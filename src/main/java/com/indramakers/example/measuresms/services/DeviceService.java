package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<Device> devicesByName = devicesRepository.findByName(device.getName());

        if (!devicesByName.isEmpty()) {
            throw new BusinessException(ErrorCodes.DEVICE_WITH_NAME_EXISTS);
        }

        List<Location> locationById = locationRepository.findByLocationId(device.getIdLocation());
        if(locationById.isEmpty()){
            throw new BusinessException(ErrorCodes.DEVICE_LOCATION_NOT_FOUND);
        }


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

    public List<Device> getById_location(Long id_location){
        return devicesRepository.findByIdLocation(id_location);
    }

}
