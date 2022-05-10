package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.Config;
import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exeptions.BusinessException;
import com.indramakers.example.measuresms.exeptions.NotFoundException;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.LocationRepository;
import com.indramakers.example.measuresms.repositories.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {

	@Autowired
	private MeasureRepository measureRepository;
	private LocationRepository locationRepository;
	private IDevicesRepository devicesRepository;

	public void registerMeasure(int deviceId, Double value) {

		if (!devicesRepository.existsById(Integer.valueOf(deviceId))) {
			throw new NotFoundException(ErrorCodes.DEVICE_NOT_FOUND.getMessage());
		}

		if (value < Config.MIN_MEASURE_VALUE || value > Config.MAX_MEASURE_VALUE) {
			throw new BusinessException(ErrorCodes.MEASURE_VALUES_OUT_OF_RANGE);
		}

		measureRepository.create(new Measure(deviceId, value));
	}

	public List<Measure> getMeasuresByDevice(int deviceId) {
		return measureRepository.findByDevice(deviceId);
	}

	public List<Measure> getMeasuresByLocation(int locationId) {
		return locationRepository.getMeasureByLocation(locationId);
	}

}
