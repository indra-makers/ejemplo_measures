package com.indramakers.example.measuresms.controllers;

import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/measures")
public class MeasuresController {

    @Autowired
    private MeasureService measureService;

    @GetMapping("/{idLocation}/locations")
    public List<Measure> getMeasuresByLocation (@PathVariable("idLocation") Long idLocation) {
        return measureService.getMeasuresByLocation(idLocation);
    }
}
