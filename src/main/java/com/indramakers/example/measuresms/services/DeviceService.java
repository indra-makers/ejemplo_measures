package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
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

    /**
     * Create device
     * na devices with repeated name.
     * @param device
     */

    public void createDevice(Device device) {
        Date date = new Date();
        List<Device> devicesByName = devicesRepository.findByName(device.getName());

        if (!devicesByName.isEmpty()) {
            throw new RuntimeException("Device with that name already exists");
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
        return devicesRepository.findByIdLocation(id_location);
    }
}
