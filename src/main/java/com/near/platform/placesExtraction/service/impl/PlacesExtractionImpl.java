package com.near.platform.placesExtraction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.near.platform.placesExtraction.config.RedisConfig;
import com.near.platform.placesExtraction.constant.Constants;
import com.near.platform.placesExtraction.dto.request.FileProcessRequest;
import com.near.platform.placesExtraction.exception.*;
import com.near.platform.placesExtraction.model.mongo.LocationMetrics;
import com.near.platform.placesExtraction.repository.PlacesExtractionRepository;
import com.near.platform.placesExtraction.service.MailerService;
import com.near.platform.placesExtraction.service.PlacesExtractionService;
import mailer.NearMailerService;
import mcm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@ConfigurationProperties(prefix="livy")
public class PlacesExtractionImpl implements PlacesExtractionService {

  private final Logger logger = LoggerFactory.getLogger(PlacesExtractionImpl.class);

  boolean status=false;

  @Autowired
  NearServiceResponseUtil nearServiceResponseUtil;

  @Autowired
  PlacesExtractionRepository placesExtractionRepository;

  @Autowired
  NearMailerService nearMailerService;

  @Autowired
  RedisConfig redisConfig;

  // livy configuration
  private String url;
  private String driverMemory;
  private String executorMemory;
  private Integer numExecutors;
  private String file;
  private Integer executorCores;

  private ExecutorService taskPool = Executors.newFixedThreadPool(100);

  //Add data to database
  @Override
  public ResponseEntity<NearServiceResponseDto> addMetricsDataToDatabase(LocationMetrics locationMetrics) throws Exception {
    MessageCodeInfo messageCodeInfo;
    NearServiceResponseDto nearServiceResponseDto;
    //To check data is valid
    dataValidator(locationMetrics);
    //To check if data is not already present into database
    if(placesExtractionRepository.findById(locationMetrics.getPoiListId()).isEmpty()) {
      try {
        placesExtractionRepository.save(locationMetrics);

        messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0008", null);
        nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.OK.value(), "PLT-0008", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Data saved to database successfully");
        return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);
      } catch (Exception ex) {
        logger.error(" Exception while saving data to database", ex);
      }
    }
    messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
    nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Duplicate data entry");
    return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
  }

  //Add data and send mail to user
  @Override
  public ResponseEntity<NearServiceResponseDto> addPlacesDataToDatabase(LocationMetrics locationMetrics, String userId) throws Exception{
    MessageCodeInfo messageCodeInfo;
    NearServiceResponseDto nearServiceResponseDto;

    //To check data is valid
    dataValidator(locationMetrics);
    //To check if data is not already present into database
    if(placesExtractionRepository.findById(locationMetrics.getPoiListId()).isEmpty()) {
      try {
        placesExtractionRepository.save(locationMetrics);

        String successBody = "Dear user,<br><br>Your request for data upload is complete.<br>"+
            "<br>" +locationMetrics.toString();
        taskPool.submit(new MailerService(nearMailerService, Constants.MAILER_FROM_ADDRESS, userId, Constants.SUCCESS_SUBJECT, successBody));
        logger.info("Mailer initiated Successfully");

        messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0008", null);
        nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.OK.value(), "PLT-0008", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Data saved to database successfully, Shortly you will receive confirmation mail");
        return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);
      } catch (Exception ex) {
        logger.error(" Exception while saving data to database", ex);
      }
    }
    messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLATFORM, "PLT-0001", null);
    nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0001", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Duplicate data entry");
    return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
  }

  @Override
  public ResponseEntity<NearServiceResponseDto> updateMetricsDetail(Long id, LocationMetrics locationMetricsDetails,String userId) throws Exception {
    MessageCodeInfo messageCodeInfo;
    NearServiceResponseDto nearServiceResponseDto;

    dataValidator(locationMetricsDetails);

    if (placesExtractionRepository.findById(id).isPresent() && placesExtractionRepository.findById(id).get().getPoiListId().equals(locationMetricsDetails.getPoiListId())) {
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
        locationMetrics.setPoiListName(locationMetricsDetails.getPoiListName()); //update brand name
        locationMetrics.setCat1(locationMetricsDetails.getCat1()); //update cat1
        locationMetrics.setCat2(locationMetricsDetails.getCat2()); //update cat2


        placesExtractionRepository.save(locationMetrics);

        String successBody = "Dear user,<br><br>Your request for data update is complete.<br>"+
            "<br>" +locationMetrics.toString();
        taskPool.submit(new MailerService(nearMailerService, Constants.MAILER_FROM_ADDRESS, userId, Constants.SUCCESS_SUBJECT, successBody));

        messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0056", null);
        nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.OK.value(), "NPL-0056", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Data updated to database successfully, Shortly you will receive confirmation mail");
        return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);

      } catch (Exception ex) {
        logger.error("Exception while updating data :", ex);
      }
    }
    messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0007", null);
    nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "NPL-0007", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Id not matched, please enter correct id");
    return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
  }

  @Override
  public LocationMetrics getLocationMetricsData(Long id) throws Exception {
    if(placesExtractionRepository.findById(id).isPresent()){
      return placesExtractionRepository.findById(id).get();
    }
    throw new IdNotFoundException("Entered id doesn't  matched, please provide correct id");
  }

  @Override
  public List<LocationMetrics> getAllLocationMetricsData() throws Exception{
    if(!placesExtractionRepository.findAll().isEmpty()){

      return placesExtractionRepository.findAll();
    }
    throw new MetricsDataNotFoundException("No metrics data found into database");
  }

  @Override
  public ResponseEntity<NearServiceResponseDto> livyStartSparkJob() throws Exception {

    MessageCodeInfo messageCodeInfo;
    NearServiceResponseDto nearServiceResponseDto;

    List<String> args = new ArrayList<>();
    args.add("6562");
    Map<String, Object> conf = new HashMap<>();
    conf.put("spark.yarn.maxAppAttempts", 3);
    conf.put("spark.dynamicAllocation.minExecutors", 1);
    conf.put("spark.dynamicAllocation.maxExecutors", 20);

    FileProcessRequest fileProcessRequest = new FileProcessRequest(conf,args, numExecutors, file, executorMemory, driverMemory, executorCores);
    ObjectMapper mapper = new ObjectMapper();
    String request = mapper.writeValueAsString(fileProcessRequest);


    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    if(getLivyJobQueueSize()==0 && !status) {
      try {
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        String result = restTemplate.postForObject(url, entity, String.class);

        status=true;

        logger.info("job initiated:: result {}", result);

        messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0007", null);
        nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0008", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Spark job start");
        return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);

      } catch (Exception e) {
        logger.error("Exception ", e);
      }
    }
    else if(getLivyJobQueueSize()>0 || status){

      Jedis jedis=redisConfig.getJedis();
      jedis.lpush(Constants.REDIS_KEY,request);

      logger.info("request is added into queue {}",request);

      messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0007", null);
      nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0008", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Spark job request is added to queue");
      return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);
    }
    messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0007", null);
    nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "NPL-0007", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Spark job failed");
    return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);

  }

  @Override
  public ResponseEntity<NearServiceResponseDto> executeLivyJobFromQueue() throws Exception{
    MessageCodeInfo messageCodeInfo;
    NearServiceResponseDto nearServiceResponseDto;

    if(getLivyJobQueueSize()>0){
      Jedis jedis=redisConfig.getJedis();
      String request = jedis.rpop(Constants.REDIS_KEY);

      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

      try {
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        String result = restTemplate.postForObject(url, entity, String.class);
        logger.info("job initiated from script::::result {}", result);

        messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0007", null);
        nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "PLT-0008", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Spark job start");
        return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.OK);

      } catch (Exception e) {
        logger.error("Exception ", e);
      }
    }
    messageCodeInfo = nearServiceResponseUtil.fetchMessageCodeInfo(MessageCodeCategory.PLACES, "NPL-0007", null);
    nearServiceResponseDto = nearServiceResponseUtil.buildNearServiceResponseDto(true, HttpStatus.PRECONDITION_FAILED.value(), "NPL-0007", messageCodeInfo.getLongDesc(), messageCodeInfo.getShortDesc(), messageCodeInfo.getCodeType(), "Spark job failed");
    return new ResponseEntity<>(nearServiceResponseDto, HttpStatus.PRECONDITION_FAILED);
  }

  public Long getLivyJobQueueSize(){
    Jedis jedis=redisConfig.getJedis();
    return jedis.llen(Constants.REDIS_KEY);

  }

  private void dataValidator(LocationMetrics locationMetrics)  throws Exception{
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


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDriverMemory() {
    return driverMemory;
  }

  public void setDriverMemory(String driverMemory) {
    this.driverMemory = driverMemory;
  }

  public String getExecutorMemory() {
    return executorMemory;
  }

  public void setExecutorMemory(String executorMemory) {
    this.executorMemory = executorMemory;
  }

  public Integer getNumExecutors() {
    return numExecutors;
  }

  public void setNumExecutors(Integer numExecutors) {
    this.numExecutors = numExecutors;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public Integer getExecutorCores() {
    return executorCores;
  }

  public void setExecutorCores(Integer executorCores) {
    this.executorCores = executorCores;
  }

}
