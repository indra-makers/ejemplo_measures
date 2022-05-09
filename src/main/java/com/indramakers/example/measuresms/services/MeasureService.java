package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private IDevicesRepository devicesRepository;

    public void registerMeasure(Long deviceId, Double value) {
        if(!devicesRepository.existsById(deviceId)){
            throw new NotFoundException(ErrorCodes.DEVICE_NOT_FOUND);
        }

        if(value<0 || value>100) {
            throw new BusinessException(ErrorCodes.MEASURE_VALUES_OUT_OF_RANGE);
        }

        measureRepository.create(new Measure(deviceId, value));
    }

    public List<Measure> getMeasuresByDevice(Long deviceId) {
        return measureRepository.findByDevice(deviceId);
    }
}
