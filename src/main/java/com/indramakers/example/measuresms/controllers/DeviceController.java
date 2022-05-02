package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.Device;
import com.indramakers.example.measuresms.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * URL /devices
     * @param device
     */
    @PostMapping
    public void create(@RequestBody Device device) {
        deviceService.createDevice(device);
    }

    /**
     *   GET /devices?marca={{marca}},
     * @param branch
     * @return
     */
    @GetMapping
    public List<Device> getDevicesByBranch(@RequestParam(name = "branch") String branch) {
        return deviceService.getBytBranch(branch);
    }

    /**
     *   GET /devices/by-branch?branch={{valor}}
     * @param branch
     * @return
     */
    @GetMapping("/by-branch")
    public List<Device> getDevicesByBranch2(@RequestParam(name = "branch") String branch) {
        return deviceService.getBytBranch(branch);
    }

}
