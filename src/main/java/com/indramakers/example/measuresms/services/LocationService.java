package com.indramakers.example.measuresms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indramakers.example.measuresms.model.entities.Locations;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.repositories.LocationRepository;

@Service
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;

	public void registerLocation(Locations location) {

		locationRepository.create(location);

	}

	public List<Locations> getLocationsById(int id) {

		return locationRepository.findById(id);
	}

	public int delete(int id) {

		return locationRepository.delete(id);

	}
	
	public List<Measure> getMeasureLocation(int idLocation){
		
		return locationRepository.getMeasureByLocation(idLocation);
	}

}
