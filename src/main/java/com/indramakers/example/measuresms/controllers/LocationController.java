package com.indramakers.example.measuresms.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.indramakers.example.measuresms.config.Routes;
import com.indramakers.example.measuresms.model.entities.Locations;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.LocationService;

@RestController
@RequestMapping(Routes.LOCATION_PATH)
public class LocationController {

	@Autowired
	private LocationService locationService;

	@PostMapping
	public void create(@RequestBody Locations location) {
		locationService.registerLocation(location);
	}

	@GetMapping(Routes.LOCATIONS)
	public List<Locations> getLocationsById(@RequestParam(name = "id") int id) {

		return locationService.getLocationsById(id);
	}

	@DeleteMapping(Routes.ELIMINAR_LOCATION)
	public void delete(@RequestParam(name = "id") int id) {

		locationService.delete(id);

	}
	
	@GetMapping(Routes.MEASURE_LOCATIONS)
	public List<Measure> getMeasureLocation(int id) {
		return locationService.getMeasureLocation(id);
	}

}
