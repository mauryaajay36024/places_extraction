package com.near.platform.placesExtraction.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileProcessRequest {

  private Map<String,Object> conf;
  private Integer numExecutors;
  private String file;
  private String executorMemory;
  private String driverMemory;
  private Integer executorCores;
  private List<String> args;



  public FileProcessRequest(Map<String, Object> conf,List<String> args, Integer numExecutors, String file, String executorMemory, String driverMemory, Integer executorCores) {
    this.conf = conf;
    this.args=args;
    this.numExecutors = numExecutors;
    this.file = file;
    this.executorMemory = executorMemory;
    this.driverMemory = driverMemory;
    this.executorCores = executorCores;
  }

  public Map<String, Object> getConf() {
    return conf;
  }

  public void setConf(Map<String, Object> conf) {
    this.conf = conf;
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

  public String getExecutorMemory() {
    return executorMemory;
  }

  public void setExecutorMemory(String executorMemory) {
    this.executorMemory = executorMemory;
  }

  public String getDriverMemory() {
    return driverMemory;
  }

  public void setDriverMemory(String driverMemory) {
    this.driverMemory = driverMemory;
  }

  public Integer getExecutorCores() {
    return executorCores;
  }

  public void setExecutorCores(Integer executorCores) {
    this.executorCores = executorCores;
  }

  public List<String> getArgs() {
    return args;
  }

  public void setArgs(List<String> args) {
    this.args = args;
  }
}