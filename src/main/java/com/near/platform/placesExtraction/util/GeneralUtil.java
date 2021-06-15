package com.near.platform.placesExtraction.util;

import com.near.platform.placesExtraction.config.RedisConfig;
import com.near.platform.placesExtraction.constant.Constants;
import com.near.platform.placesExtraction.exception.MetricsFieldNotFoundException;
import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class GeneralUtil {

  private final Logger logger = LoggerFactory.getLogger(GeneralUtil.class);

  @Autowired
  RedisConfig redisConfig;

  public void dataValidator(LocationMetrics locationMetrics)  throws Exception{
    if (locationMetrics.getCountry() == null) {
      throw new MetricsFieldNotFoundException("Country value is missing");
    }
    else if(locationMetrics.getExtractedPOICount() == null){
      throw new MetricsFieldNotFoundException("ExtractedPoiCount value is missing");
    }
    else if(locationMetrics.getDeleteCount() == null){
      throw new MetricsFieldNotFoundException("DeletedCount value is missing");
    }
    else if(locationMetrics.getCurrentPOICount() == null){
      throw new MetricsFieldNotFoundException("CurrentPoiCount value is missing");
    }
    else if (locationMetrics.getPoiListId() == null){
      throw new MetricsFieldNotFoundException("Poi List Id is missing");
    }
    else if (locationMetrics.getIngestCount() == null){
      throw new MetricsFieldNotFoundException("Ingested count is missing");
    }
    else if (locationMetrics.getUpdateCount() == null){
      throw new MetricsFieldNotFoundException(" Update count is missing");
    }
    else if (locationMetrics.getDateOfExtraction() == null){
      throw new MetricsFieldNotFoundException("Date Of Extraction is missing");
    }
    else if (locationMetrics.getGroundTruth() == null){
      throw new MetricsFieldNotFoundException("Ground truth is missing");
    }
    else if (locationMetrics.getSource() == null){
      throw new MetricsFieldNotFoundException("Source of data is missing");
    }
    else if (locationMetrics.getPoiListName() == null){
      throw new MetricsFieldNotFoundException("Brand name  is missing");
    }
    else if (locationMetrics.getCat1() == null){
      throw new MetricsFieldNotFoundException("Category1 of metrics data is missing");
    }
    else if (locationMetrics.getCat2() == null){
      throw new MetricsFieldNotFoundException("Category2 of metrics data is missing");
    }
  }

  public Long getLivyJobQueueSize(){
    try {
      Jedis jedis=redisConfig.getJedis();
      return jedis.llen(Constants.REDIS_KEY);
    }
    catch (Exception e){
      logger.error("Exception in getLivyJobQueueSize() :: ",e);
    }
    return null;
  }

}
