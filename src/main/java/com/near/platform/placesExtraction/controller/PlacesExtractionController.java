package com.near.platform.placesExtraction.controller;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.service.PlacesExtractionImp;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/places")
public class PlacesExtractionController {

    Logger logger = LoggerFactory.getLogger(PlacesExtractionController.class);

    @Autowired
    PlacesExtractionImp placesExtractionImp;

    @ApiOperation(value = "Add metrics fields into Mongodb database")
    @PostMapping(value = "/metrics")
    public ResponseEntity<HttpStatus> addMetricsData(@RequestBody LocationMetrics locationMetrics) throws Exception {
        return placesExtractionImp.addDataToDatabase(locationMetrics);
    }

    @ApiOperation(value = "Update metrics field data ")
    @PutMapping(value ="/metrics/{id}")
    public ResponseEntity<HttpStatus> updateMetricsData(@PathVariable String id,@RequestBody LocationMetrics locationMetrics) throws Exception {

        return placesExtractionImp.updateMetricsDetail(id, locationMetrics);

    }

    //Fetch location metrics data
    @ApiOperation(value = "Fetch metrics data for given id")
    @GetMapping(value = "/metrics/{id}")
    public LocationMetrics getMetricsData(@PathVariable String id) throws Exception {
        return placesExtractionImp.getLocationMetricsData(id);
    }

}
