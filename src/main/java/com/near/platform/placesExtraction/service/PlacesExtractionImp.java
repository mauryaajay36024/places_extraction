package com.near.platform.placesExtraction.service;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.repository.PlacesExtractionRepository;
import mcm.NearServiceResponseUtil;
import org.apache.commons.lang.NullArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.MissingResourceException;
import java.util.Optional;

@Service
public class PlacesExtractionImp {

  private Logger logger = LoggerFactory.getLogger(PlacesExtractionImp.class);


  @Autowired
  NearServiceResponseUtil nearServiceResponseUtil;

  @Autowired
  PlacesExtractionRepository placesExtractionRepository;

  public String addDataToDatabase(LocationMetrics locationMetrics) {

    logger.info("Inside addDataToDatabase method");

    //To check data is valid
    if(dataValidator(locationMetrics)){
      try {
        logger.info("Data is valid");
        placesExtractionRepository.save(locationMetrics);
        logger.info("Inside addDataToDatabase method ::Data saved to database");
        return "data added to database successfully";
      }
      catch (Exception ex){
        logger.error("Exception while saving data to database",ex);
      }
    }
   //todo response entity
    return "data saving failed";
  }

  public String updateDataToDatabase(String id, LocationMetrics locationMetricsDetails) {
      if (dataValidator(locationMetricsDetails)) {
        logger.info("Data is valid");
        Optional<LocationMetrics> locationMetrics1 = placesExtractionRepository.findById(id);
        if (locationMetrics1.isPresent()) {
          try {
            LocationMetrics locationMetrics = locationMetrics1.get();

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
            logger.info("Data updated into database successfully");
            return "Data updated successfully";
          } catch (Exception ex) {
            logger.error("Exception while updating data :", ex);
          }
        }
      }
      return "Data not updated";
  }


  public LocationMetrics getLocationMetricsData(String id) {
    Optional<LocationMetrics> locationMetrics1 = placesExtractionRepository.findById(id);
    if(locationMetrics1.isPresent()){
      LocationMetrics locationMetrics=locationMetrics1.get();
      return locationMetrics;
    }
    throw new MissingResourceException("No data available for ::",id,"into database");
  }

  private boolean dataValidator(LocationMetrics locationMetrics) {
    if(locationMetrics !=null) {
      String country = locationMetrics.getCountry();
      Long extractedPoiCount = locationMetrics.getExtractedPOICount();
      Long deletedCount = locationMetrics.getDeleteCount();
      if (country != null && extractedPoiCount != null && deletedCount != null) {
        return true;
      }
      throw new IllegalArgumentException("Critical values are missing, Provide all required values");
    }
    throw new NullArgumentException("Location metrics is null, Please provide value");
  }
}
