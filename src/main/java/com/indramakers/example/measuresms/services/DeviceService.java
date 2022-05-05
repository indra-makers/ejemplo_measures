package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.repositories.DeviceRepository;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository devicesRepository;
	private IDevicesRepository deviceRepository2;

	/**
	 * Create device na devices with repeated name.
	 * 
	 * @param device
	 */
	public void createDevice(Device device) {

		List<Device> devicesByName = deviceRepository2.findByName(device.getName());

		if (!devicesByName.isEmpty()) {
			throw new RuntimeException("Device with that name already exists");
		}

		deviceRepository2.save(device);
	}

	/**
	 * get devices by branch
	 * 
	 * @param branch
	 * @return list of devices
	 */
	public List<Device> getBytBranch(String branch) {
		return deviceRepository2.findByBranch(branch);
	}

	public void getMeasureLocations(int idLocation) {

		devicesRepository.getMeasureLocation(idLocation);
	}

	public void getDeviceLocation(int idLocation) {

		 devicesRepository.getDeviceByLocation(idLocation);
	}
}
