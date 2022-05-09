package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Device;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.model.requests.MeasureValueRequest;
import com.indramakers.example.measuresms.services.DeviceService;
import com.indramakers.example.measuresms.services.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MeasureService measureService;

    /**
     * URL /devices
     *
     * @param device
     */
    @PostMapping
    public void create(@Valid @RequestBody Device device) {
        deviceService.createDevice(device);
    }

    /**
     * GET /devices?marca={{marca}},
     *
     * @param branch
     * @return
     */
    @GetMapping
    public List<Device> getDevicesByBranch(@RequestParam(name = "branch") String branch) {
        return deviceService.getBytBranch(branch);
    }

    /**
     * GET /devices/by-branch?branch={{valor}}
     *
     * @param branch
     * @return
     */
    @GetMapping("/by-branch")
    public List<Device> getDevicesByBranch2(@RequestParam(name = "branch") String branch) {
        return deviceService.getBytBranch(branch);
    }

    /**
     * PATH /devices/{deviceId}/measures
     * POST
     * PARAMS: body -> { value: 12312 }
     */
    @PostMapping("/{deviceId}/measures")
    public void addMeasureToDevice(
            @Valid @RequestBody MeasureValueRequest request,
            @PathVariable("deviceId") Long deviceId) {

        measureService.registerMeasure(deviceId, request.getValue());
    }

    /**
     * GET /devices/{id}/measures
     */

    @GetMapping("/{deviceId}/measures")
    public List<Measure> getDeviceMeasures(
            @PathVariable("deviceId") Long deviceId) {

        return measureService.getMeasuresByDevice(deviceId);
    }

    @GetMapping("/getbyLocation")
    public List<Device> getLocationsDevices(@RequestParam(name="id_location") Long id_location) {
        return deviceService.getById_location(id_location);
    }
}
