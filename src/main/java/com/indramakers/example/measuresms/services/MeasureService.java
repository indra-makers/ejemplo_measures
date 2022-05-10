package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.excepciones.BussinessException;
import com.indramakers.example.measuresms.excepciones.NotFoundException;
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

    public void registerMeasure(String deviceId, Double value) {

        if(!devicesRepository.existsById(Long.valueOf(deviceId))){
            throw new NotFoundException("Device not found");
        }
        if(value<0 || value>100) {
            throw new BussinessException("Invalid measure", "003");
        }

        measureRepository.create(new Measure(deviceId, value));
    }

    public List<Measure> getMeasuresByDevice(String deviceId) {
        return measureRepository.findByDevice(deviceId);
    }
}
