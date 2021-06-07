package com.near.platform.placesExtraction.service;

import com.near.platform.placesExtraction.exception.*;
import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.repository.PlacesExtractionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class PlacesExtractionImp {

  private Logger logger = LoggerFactory.getLogger(PlacesExtractionImp.class);

  @Autowired
  PlacesExtractionRepository placesExtractionRepository;

  public ResponseEntity<HttpStatus> addDataToDatabase(LocationMetrics locationMetrics) throws Exception {
    //To check data is valid
    dataValidator(locationMetrics);
    try {
      placesExtractionRepository.save(locationMetrics);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    catch (Exception ex){
      logger.error("Exception while saving data to database",ex);
    }
    return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
  }

  public ResponseEntity<HttpStatus> updateMetricsDetail(String id, LocationMetrics locationMetricsDetails) throws Exception {
    dataValidator(locationMetricsDetails);
    if (placesExtractionRepository.findById(id).isPresent()) {
      try {
        LocationMetrics locationMetrics = placesExtractionRepository.findById(id).get();

        locationMetrics.setCurrentPOICount(locationMetricsDetails.getCurrentPOICount());//set current poi count
        locationMetrics.setExtractedPOICount(locationMetricsDetails.getExtractedPOICount());//set extracted poi count
        locationMetrics.setIngestCount(locationMetricsDetails.getIngestCount()); //set ingest count
        locationMetrics.setUpdateCount(locationMetricsDetails.getUpdateCount());// set updated count
        locationMetrics.setDeleteCount(locationMetricsDetails.getDeleteCount()); //set delete count
        locationMetrics.setDateOfExtraction(locationMetricsDetails.getDateOfExtraction()); //set date of extraction
        locationMetrics.setPoiListId(locationMetricsDetails.getPoiListId()); //set poi list id
        locationMetrics.setCountry(locationMetricsDetails.getCountry());//set country
        locationMetrics.setGroundTruth(locationMetricsDetails.getGroundTruth());//set ground truth
        locationMetrics.setSource(locationMetricsDetails.getSource());//set source

        placesExtractionRepository.save(locationMetrics);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception ex) {
        logger.error("Exception while updating data :", ex);
      }
    }
      throw new IdNotFoundException("Entered id doesn't matched, please provide correct id");
  }


  public LocationMetrics getLocationMetricsData(String id) throws Exception {
    if(placesExtractionRepository.findById(id).isPresent()){
      return placesExtractionRepository.findById(id).get();
    }
    throw new IdNotFoundException("Entered id doesn't  matched, please provide correct id");
  }

  private void dataValidator(LocationMetrics locationMetrics)  throws Exception{
    if (locationMetrics.getCountry() == null) {
      throw new MetricsFieldNotFoundException("Country value is missing");
    }
    if(locationMetrics.getExtractedPOICount() == null){
      throw new MetricsFieldNotFoundException("ExtractedPoiCount value is missing");
    }
    if(locationMetrics.getDeleteCount() == null){
      throw new MetricsFieldNotFoundException("DeletedCount value is missing");
    }
    if(locationMetrics.getCurrentPOICount() == null){
      throw new MetricsFieldNotFoundException("CurrentPoiCount value is missing");
    }
  }
}
