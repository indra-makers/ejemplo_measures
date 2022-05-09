package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
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
        List<Location> locationByIdLocation = locationRepository.findLocation(device.getIdLocation());
        System.out.println(locationByIdLocation.size());
        List<Device> devicesByName = devicesRepository.findByName(device.getName());

        if (!devicesByName.isEmpty()) {
            throw new BusinessException(ErrorCodes.DEVICE_WITH_NAME_EXISTS);
        }
        if (locationByIdLocation.isEmpty()){
            throw new NotFoundException(ErrorCodes.ID_LOCATION_NOT_EXIST.getMessage());
        }

        devicesRepository.save(device);
    }

    /**
     * get devices by branch
     * @param branch
     * @return list of devices
     */
    public List<Device> getBytBranch(String branch) {
        if(devicesRepository.findByBranch(branch).size()!=0){
            return devicesRepository.findByBranch(branch);
        }
        else{
            throw new BusinessException(ErrorCodes.INVALID_ID_BRANCH);
        }
    }

    public List<Device> getByIdLocation(Long idLocation) {
        if(devicesRepository.findByIdLocation(idLocation).size()!=0){
            return devicesRepository.findByIdLocation(idLocation);
        }
        else{
            throw new NotFoundException(ErrorCodes.INVALID_ID_LOCATION.getMessage());
        }
    }

}
