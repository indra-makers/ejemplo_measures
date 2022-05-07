package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.Config;
import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessExceptions;
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
    private IDevicesRepository iDevicesRepository;

    public void registerMeasure(int deviceId, Double value) {
        if(value< Config.MIN_MEAUSURE_VALUE || value>Config.MAX_MEAUSURE_VALUE) {
            throw new BusinessExceptions(ErrorCodes.MEASURE_VALUES_OUT_OF_RANGE);
        }
        if(!iDevicesRepository.existsById(Long.valueOf(deviceId))){
            throw new NotFoundException(ErrorCodes.DEVICE_DOES_NOT_EXISTS.getMessage());
        }

        measureRepository.create(new Measure(deviceId, value));
    }



    public List<Measure> getMeasuresByDevice(int deviceId) {
        if(!iDevicesRepository.existsById(Long.valueOf(deviceId))){
            throw new NotFoundException(ErrorCodes.DEVICE_DOES_NOT_EXISTS.getMessage());
        }
        return measureRepository.findByDevice(deviceId);
    }
}
