package com.indramakers.example.measuresms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indramakers.example.measuresms.config.Routes;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.MeasureService;

@RestController
@RequestMapping(Routes.MEASURE_PATH)
public class MeasureController {
	@Autowired
	private MeasureService measureService;
	
	
	@PostMapping
	public void create(@RequestBody Measure measure) {
		measureService.registerMeasure(measure.getDeviceId(), measure.getValue());
		
	}
	
	@GetMapping(Routes.MEASURE_LOCATIONS)
	public void getMeasureLocation(@RequestParam(name = "id") int id) {
		measureService.getMeasuresByDevice(id);
	}
	
	
}
