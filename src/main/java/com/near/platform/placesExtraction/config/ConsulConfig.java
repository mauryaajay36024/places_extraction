//package com.near.platform.placesExtraction.config;
//
//import com.ecwid.consul.v1.ConsulClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import utility.service.NearConsulService;
//
///**
// * Created by Prathap on 13 Aug, 2019
// */
//@Configuration
//public class ConsulConfig {
//
//    @Autowired
//    private NearConsulService nearConsulService;
//    @Autowired
//    private ApplicationConfig applicationConfig;
//
//    @Bean
//    public ConsulClient getConsulClient() {
//        return nearConsulService.getConsulClient(applicationConfig.getConsul().getHost());
//    }
//
//}
