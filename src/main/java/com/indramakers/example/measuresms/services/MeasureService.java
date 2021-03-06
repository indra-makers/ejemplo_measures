package com.indramakers.example.measuresms.services;

import com.indramakers.example.measuresms.config.Config;
import com.indramakers.example.measuresms.config.ErrorCodes;
import com.indramakers.example.measuresms.exceptions.BusinessException;
import com.indramakers.example.measuresms.exceptions.NotFoundException;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.model.responses.ListMEasuresResponses;
import com.indramakers.example.measuresms.model.responses.MeasureSummary;
import com.indramakers.example.measuresms.model.responses.MeasureSummaryResponse;
import com.indramakers.example.measuresms.repositories.IDevicesRepository;
import com.indramakers.example.measuresms.repositories.MeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureService {

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private IDevicesRepository devicesRepository;

    public void registerMeasure(String deviceId, Double value) {

        if (!devicesRepository.existsById(Long.valueOf(deviceId))) {
            throw new NotFoundException(ErrorCodes.DEVICE_NOT_FOUND.getMessage());
        }

        if (value < Config.MIN_MEAUSURE_VALUE || value > Config.MAX_MEAUSURE_VALUE) {
            throw new BusinessException(ErrorCodes.MEASURE_VALUES_OUT_OF_RANGE);
        }

        measureRepository.create(new Measure(deviceId, value));
    }

    public Page<Measure> getMeasuresByDevice(String deviceId, Pageable page) {
        if (!devicesRepository.existsById(Long.valueOf(deviceId))) {
            throw new NotFoundException(ErrorCodes.DEVICE_NOT_FOUND.getMessage());
        }
        return measureRepository.findByDevice(deviceId, page);
    }

    public ListMEasuresResponses getSummary(String deviceId) {
        Page<Measure> measures  = measureRepository.findByDevice(deviceId, null);

        double sum = 0;

        for (Measure measure: measures) {
            sum += measure.getValue();
        }

        return new ListMEasuresResponses(measures.getContent(), sum);
    }

    public MeasureSummaryResponse getAllSummary() {
        List<MeasureSummary> list = measureRepository.getSummary();

        return new MeasureSummaryResponse(list);
    }


}
