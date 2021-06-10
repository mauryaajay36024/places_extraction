package com.near.platform.placesExtraction.controller;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.service.PlacesExtractionService;

import io.swagger.annotations.ApiOperation;
import mailer.NearMailerService;
import mcm.NearServiceResponseDto;
import mcm.NearServiceResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/v1/places")
public class PlacesExtractionController {

    @Autowired
    PlacesExtractionService placesExtractionService;

    Logger logger = LoggerFactory.getLogger(PlacesExtractionController.class);


    @ApiOperation(value = "Add metrics fields into Mongodb database")
    @PostMapping(value = "/metrics")
    public ResponseEntity<NearServiceResponseDto> addMetricsData(@RequestBody LocationMetrics locationMetrics) throws Exception {
        return placesExtractionService.addMetricsDataToDatabase(locationMetrics);
    }

    @ApiOperation(value = "Update metrics field data ")
    @PutMapping(value ="/metrics/")
    public ResponseEntity<NearServiceResponseDto> updateMetricsData( @RequestParam(required = true, value = "id") Long id,@RequestBody LocationMetrics locationMetrics) throws Exception {
        return placesExtractionService.updateMetricsDetail(id, locationMetrics);
    }

    //Fetch location metrics data

    @ApiOperation(value = "Fetch metrics data for given id")
    @GetMapping(value = "/metrics/")
    public LocationMetrics getMetricsData(@RequestParam(required = true, value = "id") Long id) throws Exception {
        return placesExtractionService.getLocationMetricsData(id);
    }

    //Fetch location metrics data
    @ApiOperation(value = "Fetch all metrics data")
    @GetMapping(value = "/metrics/all")
    public List<LocationMetrics> getAllMetricsData() throws Exception{
        return placesExtractionService.getAllLocationMetricsData();
    }


    //todo mailer testing
    @ApiOperation(value = "Add metrics data and send mail to client")
    @RequestMapping(value = "/metrics/add/", method = RequestMethod.POST)
    public ResponseEntity<NearServiceResponseDto> uploadDataToDatabase(@RequestBody LocationMetrics locationMetrics, @RequestParam(value = "userId") String userId) throws Exception {

        return placesExtractionService.addPlacesDataToDatabase(locationMetrics,userId);

    }
}
