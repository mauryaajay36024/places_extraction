package com.near.platform.placesExtraction.service;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import mcm.NearServiceResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlacesExtractionService {

  ResponseEntity<NearServiceResponseDto> addPlacesDataToDatabase(LocationMetrics locationMetrics, String userId) throws Exception;

  ResponseEntity<NearServiceResponseDto> updateMetricsDetail(Long id, LocationMetrics locationMetrics,String userId) throws Exception;

  LocationMetrics getLocationMetricsData(Long id) throws Exception;

  List<LocationMetrics>  getAllLocationMetricsData() throws Exception;

  ResponseEntity<NearServiceResponseDto> livyExecutePysparkJob(String poiListId) throws Exception;

  ResponseEntity<NearServiceResponseDto> executeLivyJobFromQueue(boolean jobStatus) throws Exception;
}
