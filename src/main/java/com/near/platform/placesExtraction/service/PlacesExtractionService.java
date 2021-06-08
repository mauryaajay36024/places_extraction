package com.near.platform.placesExtraction.service;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import mcm.NearServiceResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlacesExtractionService {
  ResponseEntity<NearServiceResponseDto> addMetricsDataToDatabase(LocationMetrics locationMetrics) throws Exception;
  ResponseEntity<NearServiceResponseDto> updateMetricsDetail(Long id, LocationMetrics locationMetrics) throws Exception;
  LocationMetrics getLocationMetricsData(Long id) throws Exception;
  List<LocationMetrics>  getAllLocationMetricsData() throws Exception;

}
