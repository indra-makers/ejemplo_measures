package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<Device> devicesByName = devicesRepository.findByName(device.getName());

        if (!devicesByName.isEmpty()) {
            throw new RuntimeException("Device with that name already exists");
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


    public List<Device> getDeviceByLocation(int id) {
        return devicesRepository.findByLocation(id);
    }

}
