package com.near.platform.placesExtraction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ApplicationConfig {

  private Livy livy;
  private Consul consul;
  private Mongo mongo;
  private Redis redis;

  public static class Mongo {
    private String uriInConsul;
    private String database;

    public String getUriInConsul() {
      return uriInConsul;
    }

    public void setUriInConsul(String uriInConsul) {
      this.uriInConsul = uriInConsul;
    }

    public String getDatabase() {
      return database;
    }

    public void setDatabase(String database) {
      this.database = database;
    }
  }

  public static class Consul {
    private String host;

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }
  }

  public static class Livy {
    private String url;
    private String driverMemory;
    private String executorMemory;
    private Integer numExecutors;
    private String file;
    private Integer executorCores;

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

  public static class Redis{
    private String url;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }

  public Mongo getMongo() {
    return mongo;
  }

  public void setMongo(Mongo mongo) {
    this.mongo = mongo;
  }

  public Consul getConsul() {
    return consul;
  }

  public void setConsul(Consul consul) {
    this.consul = consul;
  }

  public Livy getLivy() {
    return livy;
  }

  public void setLivy(Livy livy) {
    this.livy = livy;
  }

  public Redis getRedis() {
    return redis;
  }

  public void setRedis(Redis redis) {
    this.redis = redis;
  }
}