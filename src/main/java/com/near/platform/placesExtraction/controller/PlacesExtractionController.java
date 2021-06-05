package com.near.platform.placesExtraction.controller;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.service.PlacesExtractionImp;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/places")
public class PlacesExtractionController {

    Logger logger = LoggerFactory.getLogger(PlacesExtractionController.class);


    @Autowired
    PlacesExtractionImp placesExtractionImp;

    //Adding location metrics data to database
    @ApiOperation(value = "Add metrics fields into Mongodb database")
    @PostMapping(value = "/addMetricsData")
    public String addMetricsDataToMongoDatabase(@RequestBody LocationMetrics locationMetrics) {

        logger.info("Request received for add metrics data to mongodb database");
        placesExtractionImp.addDataToDatabase(locationMetrics);

        String response = placesExtractionImp.addDataToDatabase(locationMetrics);
        logger.info("Data added to database successfully");
        //todo change response entity
        return response;

    }

    //Update location metrics
    @ApiOperation(value = "Update metrics field data ")
    @PutMapping(value ="/updateMetrics/{id}")

    public String updateMetricsDataToMongoDatabase(@PathVariable String id,@RequestBody LocationMetrics locationMetrics) {
        logger.info("Request received for update metrics Data ");

        String response=placesExtractionImp.updateDataToDatabase(id, locationMetrics);
        logger.info("Data updated to database successfully");

        //todo change response
        return response;

    }

    //Fetch location metrics data
    @ApiOperation(value = "Fetch metrics data for given id")
    @GetMapping(value = "/getMetrics/{id}")

    public LocationMetrics getMetricsData(@PathVariable String id)  {
        logger.info("Request received to fetch the metrics Data ");
        LocationMetrics response=placesExtractionImp.getLocationMetricsData(id);

        logger.info("Data fetched from database successfully");
        return response;
    }
}
