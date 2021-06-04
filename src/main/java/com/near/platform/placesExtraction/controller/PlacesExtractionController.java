package com.near.platform.placesExtraction.controller;

import com.near.platform.placesExtraction.config.ApplicationConfig;
import com.near.platform.placesExtraction.constant.Constants;
import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.service.PlacesExtractionImp;
import exception.NearServiceException;
import io.swagger.annotations.ApiOperation;
import mcm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.CONTROLLER_API)
public class PlacesExtractionController {

    Logger logger = LoggerFactory.getLogger(PlacesExtractionController.class);

    @Autowired
    NearServiceResponseUtil nearServiceResponseUtil;
    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    PlacesExtractionImp placesExtractionImp;

//    @Value("${places_extraction}")
//    private String appName;

    //Adding location metrics data to database
    @ApiOperation(value = Constants.VALUE1)
    @PostMapping(value = Constants.POST_API)
    public ResponseEntity<NearServiceResponseDto> addMetricsDataToMongoDatabase(@RequestBody LocationMetrics locationMetrics) throws NearServiceException {
        MessageCodeInfo messageCodeInfo;
        NearServiceResponseDto nearServiceResponseDto;

        try {
            logger.info("Request received for add metrics data to mongodb database");
            if (locationMetrics == null) {
                messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
                nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Metrics info is empty or invalid");
                return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
            }
            placesExtractionImp.addDataToDatabase(locationMetrics);
            logger.info("Data added to database successfully");

            //TODO messageCode ??
            messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "NPL-0056", null);
            nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.OK.value(), "NPL-0056", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Data updated successfully");
            return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);

        }
        catch (Exception e) {
            logger.error("Exception occurred on data insertion ", e);
            messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
            nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Metrics info is empty or invalid");
            return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);

        }
    }

    //Update location metrics
    @ApiOperation(value = Constants.VALUE2)
    @PutMapping(value = Constants.UPDATE_API)

    public ResponseEntity<NearServiceResponseDto> updateMetricsDataToMongoDatabase(@PathVariable String id,@RequestBody LocationMetrics locationMetrics) throws NearServiceException {
        MessageCodeInfo messageCodeInfo;
        NearServiceResponseDto nearServiceResponseDto;

        try {
            logger.info("Request received for update metrics Data ");

            if (id.isEmpty() && locationMetrics == null) {
                messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
                nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Matrix info is empty or invalid");
                return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
            }
            placesExtractionImp.updateDataToDatabase(id, locationMetrics);

            logger.info("Data updated to database successfully");

            //TODO messageCode ??
            messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "NPL-0056", null);
            nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.OK.value(), "NPL-0056", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Data updated successfully");
            return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Exception occurred on data updating ", e);
            messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
            nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Matrix info is empty or invalid");
            return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
        }
    }

    //Fetch location metrics data
 /**   @ApiOperation(value = Constants.VALUE3)
    @GetMapping(value = Constants.GET_SINGLE_DATA_API)

    public ResponseEntity<NearServiceResponseDto> getMetricsData(@PathVariable String id) throws NearServiceException {
        MessageCodeInfo messageCodeInfo;
        NearServiceResponseDto nearServiceResponseDto;

        try {
            logger.info("Request received to fetch the metrics Data ");

            if (id.isEmpty()) {
                messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
                nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Matrix info is empty or invalid");
                return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
            }
            placesExtractionImp.getLocationMetricsData(id);

            logger.info("Data fetched from database successfully");

            //TODO messageCode ??
            messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "NPL-0056", null);
            nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.OK.value(), "NPL-0056", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Data updated successfully");
            return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Exception occurred on data fetch ", e);
            messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
            nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Matrix info is empty or invalid");
            return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
        }
    }
*/

}
