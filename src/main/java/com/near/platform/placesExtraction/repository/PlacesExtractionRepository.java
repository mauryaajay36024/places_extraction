package com.near.platform.placesExtraction.repository;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface PlacesExtractionRepository extends MongoRepository<LocationMetrics, String> {

  List<LocationMetrics> findByPoiListId(Long poiListId);
}
