package com.near.platform.placesExtraction.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisConfig {

  private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

  @Autowired
  private ApplicationConfig applicationConfig;

  public  Jedis getJedis(){

    return new Jedis(applicationConfig.getRedis().getUrl());
  }
}

