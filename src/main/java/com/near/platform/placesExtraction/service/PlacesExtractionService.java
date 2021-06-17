package com.near.platform.placesExtraction.service;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import mcm.NearServiceResponseDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PlacesExtractionService {

  ResponseEntity<NearServiceResponseDto> addPlacesDataToDatabase(LocationMetrics locationMetrics, String userId) throws Exception;

  List<LocationMetrics> getLocationMetricsDataForId(LocationMetrics locationMetrics) throws Exception;

  List<LocationMetrics>  getAllLocationMetricsData(boolean latestOnly) throws Exception;

  ResponseEntity<NearServiceResponseDto> livyExecutePysparkJob(String poiListId) throws Exception;

  ResponseEntity<NearServiceResponseDto> executeLivyJobFromRedis(boolean jobStatus) throws Exception;
}
