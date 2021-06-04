package com.near.platform.placesExtraction.service;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.repository.PlacesExtractionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlacesExtractionImp {

   @Autowired
    PlacesExtractionRepository placesExtractionRepository;

    public void addDataToDatabase(LocationMetrics locationMetrics) {

        placesExtractionRepository.save(locationMetrics);
    }


  public void updateDataToDatabase(String id, LocationMetrics locationMetricsDetails) {
    Optional<LocationMetrics> locationMetrics1 = placesExtractionRepository.findById(id);
    if(locationMetrics1.isPresent()){
      LocationMetrics locationMetrics=locationMetrics1.get();

      BeanUtils.copyProperties(locationMetricsDetails,locationMetrics);

//      locationMetrics.setCurrentPOICount(locationMetricsDetails.getCurrentPOICount());//set current poi count
//      locationMetrics.setExtractedPOICount(locationMetricsDetails.getExtractedPOICount());//set extracted poi count
//      locationMetrics.setIngestCount(locationMetricsDetails.getIngestCount()); //set ingest count
//      locationMetrics.setUpdateCount(locationMetricsDetails.getUpdateCount());// set updated count
//      locationMetrics.setDeleteCount(locationMetricsDetails.getDeleteCount()); //set delete count
//      locationMetrics.setDateOfExtraction(locationMetricsDetails.getDateOfExtraction()); //set date of extraction
//      locationMetrics.setPoiListId(locationMetricsDetails.getPoiListId()); //set poi list id
//      locationMetrics.setCountry(locationMetricsDetails.getCountry());//set country
//      locationMetrics.setGroundTruth(locationMetricsDetails.getGroundTruth());//set ground truth
//      locationMetrics.setSource(locationMetricsDetails.getSource());//set source

      //todo return result
      placesExtractionRepository.save(locationMetrics);


    }
  }

//  public void getLocationMetricsData(String id) {
//    Optional<LocationMetrics> locationMetrics1 = placesExtractionRepository.findById(id);
//    if(locationMetrics1.isPresent()){
//      LocationMetrics locationMetrics=locationMetrics1.get();
//
//
//      //todo return result
//      placesExtractionRepository.save(locationMetrics);
//
//
//    }
//  }
}
