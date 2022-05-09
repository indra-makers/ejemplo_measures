package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Location;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.DeviceRepository;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private IDevicesRepository devicesRepository;

    @Autowired
    private DeviceRepository deviceRepository;


    /**
     * Create device
     * na devices with repeated name.
     * @param device
     */
    public void createDevice(Device device) {

        List<Device> devicesByName = devicesRepository.findByName(device.getName());
        Boolean locationById = deviceRepository.findByLocation(device.getLocationId()).size()==0;

        if(locationById == true){
            throw new BusinessException(ErrorCodes.LOCATION_WITH_ID_NOT_EXISTS);
        }else if(!devicesByName.isEmpty()){
            throw new BusinessException(ErrorCodes.DEVICE_WITH_NAME_EXISTS);
        }else{
            devicesRepository.save(device);

        }
    }

    /**
     * get devices by branch
     * @param branch
     * @return list of devices
     */
    public List<Device> getBytBranch(String branch) {
        return devicesRepository.findByBranch(branch);
    }

    public List<Device> getDeviceByLocation(int id) {

        if(deviceRepository.findByLocation(id).isEmpty()){
            throw new NotFoundException(ErrorCodes.LOCATION_WITH_ID_NOT_EXISTS.getMessage());
        }else {
            return deviceRepository.findByLocation(id);
        }
    }
}
