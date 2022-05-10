package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exeptions.BusinessException;
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
	// private IDevicesRepository deviceRepository2;//

	/**
	 * Create device na devices with repeated name.
	 * 
	 * @param device
	 */
	public void createDevice(Device device) {

		boolean locationId = devicesRepository.getDeviceByLocation(device.getIdLocation()).size() == 0;

		if (locationId == true) {
			throw new BusinessException(ErrorCodes.LOCATION_DOESNT_EXIST);
		} else {
			devicesRepository.create(device);
		}

	}

	/**
	 * get devices by branch
	 * 
	 * @param branch
	 * @return list of devices
	 */
	// public List<Device> getBytBranch(String branch) {
	// return deviceRepository2.findByBranch(branch);
	// }



	public List<Device> getDeviceLocation(int idLocation) {

		return devicesRepository.getDeviceByLocation(idLocation);
	}
}
