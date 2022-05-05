package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;

    public void registerMeasure(String deviceId, Double value) {
        if(value<0 || value>100) {
            throw new RuntimeException("Invalid measure");
        }

        measureRepository.create(new Measure(deviceId, value));
    }

    public List<Measure> getMeasuresByDevice(String deviceId) {
        return measureRepository.findByDevice(deviceId);
    }

    public List<Measure> getMeasuresByLocation(Long idLocation) {
        return measureRepository.findByLocation(idLocation);
    }
}
