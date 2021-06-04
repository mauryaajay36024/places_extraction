package com.near.platform.placesExtraction.repository;

import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlacesExtractionRepository extends MongoRepository<LocationMetrics, String> {

}
