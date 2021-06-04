package com.near.platform.placesExtraction.config;

import mongodb.service.NearMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Prathap on 13 Aug, 2019
 */
@Configuration
@EnableMongoRepositories(value = "com.near.platform.placesExtraction.repository", mongoTemplateRef = "mongoTemplate")
public class MongoConfig {

    @Autowired
    private NearMongoDbService nearMongoDbService;
//    @Autowired
//    private ConsulConfig consulConfig;
    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public MongoTemplate mongoTemplate() {
        return nearMongoDbService.getMongoTemplate("mongodb://localhost:27017/","near_db");

//        return nearMongoDbService.getMongoTemplate(
//                consulConfig.getConsulClient().getKVValue(
//                        applicationConfig.getMongo().getUriInConsul()).getValue().getDecodedValue(),
//                        applicationConfig.getMongo().getDatabase());
    }
}
