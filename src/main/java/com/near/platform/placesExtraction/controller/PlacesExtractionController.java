package com.near.platform.placesExtraction.controller;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.service.PlacesExtractionService;
import io.swagger.annotations.ApiOperation;
import mcm.NearServiceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/v1/places")
public class PlacesExtractionController {

    @Autowired
    PlacesExtractionService placesExtractionService;

    @ApiOperation(value = "Add metrics data and send mail to user")
    @PostMapping(value = "/metrics/insert")
    public ResponseEntity<NearServiceResponseDto> insertMetricsData(@RequestBody LocationMetrics locationMetrics, @RequestParam(value = "emailId") String userId) throws Exception {
        return placesExtractionService.addPlacesDataToDatabase(locationMetrics,userId);

    }

    @ApiOperation(value = "Fetch metrics data for given id")
    @GetMapping(value = "/metrics/fetchDataForId")
    public List<LocationMetrics> getMetricsDataForId(@RequestBody LocationMetrics locationMetrics) throws Exception {
        return placesExtractionService.getLocationMetricsDataForId(locationMetrics);
    }

    @ApiOperation(value = "Fetch all metrics data with LatestOnly param")
    @GetMapping(value = "/metrics/fetchAllData")
    public List<LocationMetrics> getAllMetricsData(@RequestParam(value = "LatestOnly") boolean latestOnly) throws Exception{
        return placesExtractionService.getAllLocationMetricsData(latestOnly);
    }


    @ApiOperation(value = "livy call to start a spark job")
    @GetMapping(value = "/livy/runScript")
    public ResponseEntity<NearServiceResponseDto> livyExecutePyspark(@RequestParam(value = "POIListId") String poiListId) throws Exception{
        return placesExtractionService.livyExecutePysparkJob(poiListId);
    }

    @ApiOperation(value = "Redis call to pop request and start livy job via script")
    @GetMapping(value = "/redis/runScript")
    public ResponseEntity<NearServiceResponseDto> popDataFromRedis(@RequestParam(value = "jobStatus")boolean jobStatus) throws Exception{
        return placesExtractionService.executeLivyJobFromRedis(jobStatus);
    }

}
